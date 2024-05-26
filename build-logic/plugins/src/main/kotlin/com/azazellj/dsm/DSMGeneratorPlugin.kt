package com.azazellj.dsm

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import com.azazellj.dsm.task.AbstractGeneratorTask
import com.azazellj.dsm.task.ColorGeneratorTask
import com.azazellj.dsm.task.TextStyleGeneratorTask
import com.azazellj.dsm.task.TypographyGeneratorTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register

open class DSMGeneratorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // registers a callback on the application of the Android Application plugin
        project.plugins.withType(AppPlugin::class.java) { _ ->
            // look up the generic android component
            val androidComponents = project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)

            // run through all variants and create generator tasks
            androidComponents.onVariants(
                selector = androidComponents.selector().withBuildType("release"),
            ) { variant ->
                val flavor = variant.flavorName!!

                val generateDSMColorsTask = project.tasks.register<ColorGeneratorTask>(
                    name = "generate${flavor.capitalized()}DSMColors",
                    configuration = { configure(flavor) },
                )
                val generateDSMTextStylesTask = project.tasks.register<TextStyleGeneratorTask>(
                    name = "generate${flavor.capitalized()}DSMTextStyles",
                    configuration = { configure(flavor) },
                )
                val generateDSMTypographyTask = project.tasks.register<TypographyGeneratorTask>(
                    name = "generate${flavor.capitalized()}DSMTypography",
                    configuration = { configure(flavor) },
                )

                // create single task that runs all generator tasks
                project.tasks.register<DefaultTask>(
                    name = "generate${flavor.capitalized()}DSM",
                ) {
                    this.group = "dsm"

                    dependsOn(generateDSMColorsTask, generateDSMTextStylesTask, generateDSMTypographyTask)
                }
            }
        }
    }

    private fun AbstractGeneratorTask<*>.configure(flavor: String) {
        val android = project.extensions.getByType(CommonExtension::class.java)

        // app/src/international/assets/design-tokens.json
        val dsmTokensFile = project.layout.projectDirectory
            .dir("src")
            .dir(flavor)
            .dir("assets")
            .file("design-tokens.json")

        // app/src/international/java
        val flavorDirectory = project.layout.projectDirectory
            .dir("src")
            .dir(flavor)
            .dir("java")

        this.group = "dsm"
        this.dsmFile.set(dsmTokensFile)
        this.`package`.set("${android.namespace}.ui.theme")
        this.flavorDirectory.set(flavorDirectory.asFile)
    }
}
