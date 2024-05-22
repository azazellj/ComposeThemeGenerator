plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.android.gradlePlugin.api)
    compileOnly(gradleKotlinDsl())

    implementation(libs.kotlinpoet)
    implementation(libs.json)
}

gradlePlugin {
    plugins {
        create("DSMGeneratorPlugin") {
            id = "DSMGeneratorPlugin"
            implementationClass = "com.azazellj.dsm.DSMGeneratorPlugin"
        }
    }
}
