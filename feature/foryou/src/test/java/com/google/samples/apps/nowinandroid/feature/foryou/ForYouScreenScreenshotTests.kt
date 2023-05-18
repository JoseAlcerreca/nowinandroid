/*
 * Copyright 2023 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.feature.foryou

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.ui.NewsFeedUiState
import com.google.samples.apps.nowinandroid.core.ui.NewsFeedUiState.Success
import com.google.samples.apps.nowinandroid.core.ui.UserNewsResourcePreviewParameterProvider
import com.google.samples.apps.nowinandroid.feature.foryou.OnboardingUiState.Loading
import com.google.samples.apps.nowinandroid.feature.foryou.OnboardingUiState.NotShown
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "w1000dp-h1000dp")
@LooperMode(LooperMode.Mode.PAUSED)
class ForYouScreenScreenshotTests {

    /**
     * Use a test activity to set the content on.
     */
    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    private lateinit var scenario: ActivityScenario<ComponentActivity>

    private val userNewsResources = UserNewsResourcePreviewParameterProvider().values.first()

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(ComponentActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testForYouScreenPopulatedFeed() {
        captureMultiDevice("ForYouScreenPopulatedFeed") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = NotShown,
                    feedState = Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenLoading() {
        captureMultiDevice("ForYouScreenLoading") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = Loading,
                    feedState = NewsFeedUiState.Loading,
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenTopicSelection() {
        captureMultiDevice("ForYouScreenTopicSelection") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = OnboardingUiState.Shown(
                        topics = userNewsResources.flatMap { news -> news.followableTopics }
                            .distinctBy { it.topic.id },
                    ),
                    feedState = NewsFeedUiState.Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenPopulatedAndLoading() {
        captureMultiDevice("ForYouScreenPopulatedAndLoading") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = true,
                    onboardingUiState = OnboardingUiState.Loading,
                    feedState = NewsFeedUiState.Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                )
            }
        }
    }

    private fun captureMultiDevice(screenshotName: String, body: @Composable () -> Unit) {
        listOf(
            "phone" to "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480",
            "foldable" to "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
            "tablet" to "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
        ).forEach {
            captureForDevice(it.first, it.second, screenshotName, body)
        }
    }

    private fun captureForDevice(
        deviceName: String,
        deviceSpec: String,
        screenshotName: String,
        body: @Composable () -> Unit,
    ) {
        val (width, height) = extractSpecs(deviceSpec)

        scenario.onActivity { activity ->
            activity.setContent {
                CompositionLocalProvider(
                    LocalInspectionMode provides true,
                ) {
                    TestHarness(size = DpSize(width.dp, height.dp)) {
                        body()
                    }
                }
            }
        }
        composeTestRule.onRoot().captureRoboImage("../../screnshots/${screenshotName}_$deviceName.png")
    }

    private fun extractSpecs(deviceSpec: String): List<Int> {
        val specs = deviceSpec.substringAfter("spec:")
            .split(",").map { it.split("=") }.associate { it[0] to it[1] }
        val width = specs["width"]?.toInt() ?: 640
        val height = specs["height"]?.toInt() ?: 480
        return listOf(width, height)
    }
}
