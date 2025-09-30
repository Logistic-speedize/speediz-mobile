plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.hilt)
    alias (libs.plugins.secrets.gradle)
}

android {
    namespace = "com.example.speediz"
    compileSdk = 36
    ndkVersion = "26.1.10909125"

    defaultConfig {
        applicationId = "com.example.speediz"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "MAPBOX_ACCESS_TOKEN", "\"${project.findProperty("MAPBOX_ACCESS_TOKEN") ?: ""}\"")
    }

    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.default.properties"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:database"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:analytic"))
    implementation(project(":core:worker"))
    //api(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.serialization.json)
    ksp(libs.bundles.dagger.ksp)
    //hilt
    implementation(libs.hilt.dagger)
//implementation(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    //coil
    implementation(libs.coil)
    //room
    implementation(libs.bundles.room.impl)
    ksp(libs.room.compiler)
    //retrofit
    implementation(libs.bundles.retrofit)
    //coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    //work manager
    implementation(libs.work.runtime.ktx)
    //mapbox
    implementation(libs.mapbox)
    implementation(libs.compose.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}