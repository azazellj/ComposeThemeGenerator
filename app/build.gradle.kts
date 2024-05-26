plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    id("DSMGeneratorPlugin")
}

android {
    namespace = "com.azazellj.compose.theme.generator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.azazellj.compose.theme.generator"

        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    flavorDimensions.add("client")

    productFlavors {
        create("international") {
            dimension = "client"

            isDefault = true
        }
        create("local") {
            dimension = "client"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
}
