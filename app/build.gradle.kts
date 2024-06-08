plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

  
}

android {
    namespace = "com.example.andropeinn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.andropeinn"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding=true
    }




}

dependencies {


    implementation("androidx.activity:activity:1.8.0")
    val lottieVersion="6.3.0"
    var firebase_bom="32.7.2"
    val itexPdf="7.1.15"
    val play_services="21.0.0"
    val firebase_Auth="22.3.1"

    val rounded_image="2.3.0"
    val viewPager="1.0.0"
    val circleImage="3.1.0"
    val core_ktx="1.12.0"
    val app_compact="1.6.1"
    val material="1.11.0"
    val constraint_layout="2.1.4"
    val lifecycle="2.7.0"
    val viewModel="2.7.0"
    val navigation_fragment="2.7.6"
    val navigation_ui="2.7.6"
    val junit="4.13.2"
    val JUnitTest="1.1.5"
    val espresso="3.5.1"


    implementation("com.google.firebase:firebase-auth-ktx:$firebase_Auth")



    implementation ("com.airbnb.android:lottie:$lottieVersion")

    implementation(platform("com.google.firebase:firebase-bom:$firebase_bom"))

    implementation ("com.itextpdf:itext7-core:$itexPdf")

    implementation ("com.google.android.gms:play-services-auth:$play_services")

    implementation ("com.makeramen:roundedimageview:$rounded_image")
    implementation("androidx.viewpager2:viewpager2:$viewPager")
    implementation("de.hdodenhof:circleimageview:$circleImage")
    implementation("androidx.core:core-ktx:$core_ktx")
    implementation("androidx.appcompat:appcompat:$app_compact")
    implementation("com.google.android.material:material:$material")
    implementation("androidx.constraintlayout:constraintlayout:$constraint_layout")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModel")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_fragment")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_ui")
    testImplementation("junit:junit:$junit")
    androidTestImplementation("androidx.test.ext:junit:$JUnitTest")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")
}