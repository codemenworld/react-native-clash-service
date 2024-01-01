plugins {
    kotlin("android")
    kotlin("plugin.serialization") version "1.7.0"
    id("com.android.library")
    id("com.google.devtools.ksp") version "1.7.0-1.0.6"
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    maven("https://maven.kr328.app/releases")
    maven("https://jitpack.io")
}

android {
    ndkVersion = "23.0.7599858"

    compileSdk = 31

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("consumer-rules.pro")
    }

    packagingOptions {
        resources {
            excludes.add("DebugProbesKt.bin")
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.findByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}


val coroutine = "1.6.3"
val coreKtx = "1.8.0"
val serialization = "1.3.3"
val kaidl = "1.15"
val room = "2.4.2"
val multiprocess = "1.0.0"

dependencies {
    implementation("com.github.codemenworld:react-native-clash-core:1.0.0")
    implementation("com.github.codemenworld:react-native-clash-common:2c2b543ac5")

    // ksp("com.github.kr328.kaidl:kaidl:$kaidl")
    ksp("androidx.room:room-compiler:$room")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization")
    implementation("androidx.core:core-ktx:$coreKtx")
    implementation("androidx.room:room-runtime:$room")
    implementation("androidx.room:room-ktx:$room")
    // implementation("com.github.kr328.kaidl:kaidl-runtime:$kaidl")
    implementation("dev.rikka.rikkax.preference:multiprocess:$multiprocess")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
}

afterEvaluate {
    android {
        libraryVariants.forEach {
            sourceSets[it.name].kotlin.srcDir(buildDir.resolve("generated/ksp/${it.name}/kotlin"))
            sourceSets[it.name].java.srcDir(buildDir.resolve("generated/ksp/${it.name}/java"))
        }
    }
}

publishing {
  publications {
    register<MavenPublication>("release") {
      groupId = "com.github.codemenworld"
      artifactId = "react-native-clash-service"
      version = "1.0.0"

      afterEvaluate {
        from(components["release"])
      }
    }
  }
}