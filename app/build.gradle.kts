plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.newproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newproject"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        mlModelBinding = true
    }
}

dependencies {

    // TensorFlow Lite Support Library (for general support classes)
    implementation("org.tensorflow:tensorflow-lite-support:0.4.3")

    // TensorFlow Lite Metadata Library (for model metadata extraction)
    implementation("org.tensorflow:tensorflow-lite-metadata:0.3.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    //implementation(libs.tensorflow.lite.support)
    //implementation(libs.tensorflow.lite.metadata)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //implementation(libs.litert.support.api)
    //implementation(libs.litert)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.KwabenBerko:News-API-Java:1.0.2")
    implementation ("com.squareup.picasso:picasso:2.8")

}