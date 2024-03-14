plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "dev.kobzar.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.kobzar.app"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {

    // Permission flow
    implementation(libs.permission.flow.general)

    // WorkManager
    implementation(libs.work.manager)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.common)
    kapt(libs.hilt.compiler)

    // Voyager Navigation
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel)
    implementation(libs.voyager.hilt)
    implementation(libs.voyager.transitions)

    // Splash Screen
    implementation(libs.splashscreen)

    implementation(libs.material.view)
    implementation(libs.androidx.ui.compose.runtime)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Testing
    kaptTest(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":core:platform"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    implementation(project(":data:repository"))
    implementation(project(":data:repository:impl"))
    
    implementation(project(":data:preferences"))
    implementation(project(":data:preferences:impl"))

    implementation(project(":data:network"))
    implementation(project(":data:network:impl"))

    implementation(project(":data:database"))
    implementation(project(":data:database:impl"))

    implementation(project(":domain"))
    implementation(project(":domain:impl"))

    implementation(project(":features:onboarding"))
    implementation(project(":features:asteroids"))
    implementation(project(":features:details"))
    implementation(project(":features:favorites"))
    implementation(project(":features:settings"))
    implementation(project(":features:compare"))
    implementation(project(":features:dangerNotify"))
}

kapt {
    correctErrorTypes = true
}