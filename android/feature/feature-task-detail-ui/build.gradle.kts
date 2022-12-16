import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":android:android-core"))
    implementation(project(":kmp:core:core-shared"))
    implementation(project(":kmp:feature:feature-task-api"))
    implementation(project(":kmp:feature:feature-task-dependency")) // TODO remove later

    implementation("androidx.core:core-ktx:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("com.google.android.material:material:_")
    implementation("androidx.compose.ui:ui:_")
    implementation("androidx.compose.material:material:_")
    implementation("androidx.compose.ui:ui-tooling:_")
    implementation("androidx.compose.material:material-icons-extended:_")
    implementation("androidx.navigation:navigation-compose:_")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:_")
    implementation("com.google.accompanist:accompanist-pager:_")
    implementation("com.soywiz.korlibs.klock:klock:_")

    implementation("io.insert-koin:koin-android:_")
    implementation("io.insert-koin:koin-androidx-compose:_")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testImplementation("org.junit.jupiter:junit-jupiter-params:_")
    testImplementation("io.kotest:kotest-assertions-core:_")
    testImplementation("io.mockk:mockk:_")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("app.cash.turbine:turbine:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:_")
    debugImplementation("androidx.compose.ui:ui-test-manifest:_")
    androidTestImplementation("androidx.test.espresso:espresso-core:_")
}
