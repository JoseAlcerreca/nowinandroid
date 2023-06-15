/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("nowinandroid.android.feature")
    id("nowinandroid.android.library.compose")
    id("nowinandroid.android.library.jacoco")
    id("io.github.takahirom.roborazzi")
}

android {
    namespace = "com.google.samples.apps.nowinandroid.feature.foryou"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)

    testImplementation("io.github.takahirom.roborazzi:roborazzi:1.2.0-alpha-1")
    // JUnit rules
    testImplementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.2.0-alpha-1")
    testImplementation("org.robolectric:robolectric:4.10.1")
    testImplementation(libs.accompanist.testharness)

    debugImplementation(libs.androidx.compose.ui.testManifest)

}


