package com.azazellj.dsm.task

import com.squareup.kotlinpoet.ClassName

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized
import org.json.JSONObject

internal abstract class AbstractGeneratorTask<T : GeneratedType> : DefaultTask() {

    @get:InputFile
    abstract val dsmFile: RegularFileProperty

    @get:OutputDirectory
    abstract val flavorDirectory: DirectoryProperty

    @get:Input
    abstract val `package`: Property<String>

    abstract val componentClassName: ClassName
    abstract val generatedFileName: String
    abstract val rootDSMKey: String

    @TaskAction
    fun taskAction() {
        val typeMap = mutableMapOf<String, T>()

        // read file
        val jsonObject = JSONObject(dsmFile.get().asFile.readText())

        // find all type tokens
        searchTypeTokens(jsonObject.getJSONObject(rootDSMKey), rootDSMKey, typeMap)

        val fileBuilder = FileSpec.builder(
            packageName = `package`.get(),
            fileName = generatedFileName,
        )

        val objectBuilder = TypeSpec
            .objectBuilder(
                name = generatedFileName,
            )
            .addModifiers(KModifier.INTERNAL)

        // write all properties
        for ((key, type) in typeMap) {
            objectBuilder.addProperty(
                PropertySpec.builder(
                    // some keys have dots in name as opacity
                    name = key.replace(".", "_"),
                    type = componentClassName,
                    modifiers = arrayOf(KModifier.INTERNAL),
                )
                    .initializer(writeTypeToken(key, type))
                    .build(),
            )
        }

        fileBuilder.addType(objectBuilder.build())

        fileBuilder.build().writeTo(flavorDirectory.get().asFile)
    }

    private fun searchTypeTokens(
        jsonObject: JSONObject,
        parentKey: String,
        result: MutableMap<String, T>,
    ) {
        for (key in jsonObject.keys()) {
            // 'medium - prominent' to 'mediumProminent'
            val keyFormatted = key.replace(" ", "").split("-").joinToString(
                separator = "",
                transform = { it.capitalized().trim() },
            )

            val fullKey = if (parentKey.isEmpty()) key else "$parentKey$keyFormatted"

            when (val value = jsonObject.get(key)) {
                is JSONObject -> {
                    if (isTypeToken(value)) {
                        result[fullKey] = convert(field = value)
                        continue
                    }
                    searchTypeTokens(value, fullKey, result)
                }
            }
        }
    }

    abstract fun isTypeToken(field: Any): Boolean
    abstract fun convert(field: Any): T
    abstract fun writeTypeToken(key: String, type: T): CodeBlock
}
