import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application") version "8.7.2"
    id("org.jetbrains.kotlin.android") version "2.0.0"
    id("org.jetbrains.kotlin.compose") version "1.5.9"
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}
// ================================================================
//  local.properties에서 OPENAI_API_KEY 읽어와서 openAiKey 변수에 저장
// ================================================================
val localProps = Properties()
val localFile = rootProject.file("local.properties")
if (localFile.exists()) {
    localProps.load(FileInputStream(localFile))
}
val openAiKey = localProps.getProperty("OPENAI_API_KEY", "")
android {
    namespace = "com.sdc.aicookmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sdc.aicookmate"
        minSdk = 32
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug {
            // BuildConfig.OPENAI_API_KEY 에 local.properties 키 값 주입
            buildConfigField("String", "OPENAI_API_KEY", "\"$openAiKey\"")
        }
        release {
            buildConfigField("String", "OPENAI_API_KEY", "\"$openAiKey\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // BuildConfig 및 Compose 설정
    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material3:material3:1.1.1")

    val nav_version = "2.8.4"


    // Jetpack Compose integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Retrofit, Gson, OkHttp for OpenAI GPT integration
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("androidx.compose.foundation:foundation-layout:1.3.1")

    //coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    //room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation ("androidx.compose.runtime:runtime:1.5.0")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation ("androidx.compose.material:material:1.5.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")


    implementation ("com.google.code.gson:gson:2.10.1")
    implementation (platform ("com.google.firebase:firebase-bom:32.7.1"))
    implementation ("com.google.firebase:firebase-firestore-ktx")

    implementation ("com.google.mlkit:text-recognition-korean:16.0.1")


    implementation ("org.tensorflow:tensorflow-lite:2.11.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.0")

    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation ("com.google.firebase:firebase-firestore-ktx") // Firestore
    implementation ("androidx.activity:activity-compose:1.7.2") // Compose Activity
    implementation ("androidx.compose.ui:ui:1.5.1") // Compose UI
    implementation ("androidx.compose.material3:material3:1.2.0")

}