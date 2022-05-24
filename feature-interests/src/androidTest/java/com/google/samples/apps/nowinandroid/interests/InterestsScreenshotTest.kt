package com.google.samples.apps.nowinandroid.interests

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.feature.interests.InterestsScreen
import com.google.samples.apps.nowinandroid.feature.interests.InterestsTabState
import com.google.samples.apps.nowinandroid.feature.interests.InterestsUiState
import com.google.samples.apps.nowinandroid.feature.interests.R.string
import com.karumi.shot.ScreenshotTest
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class InterestsScreenshotTest : ScreenshotTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun niaLoadingWheel_inTopics_whenScreenIsLoading_showLoading() {
        composeTestRule.setContent {
            Surface(modifier = Modifier.size(320.dp, 512.dp)) {
                InterestsScreen(
                    uiState = InterestsUiState.Interests(topics = testTopics, authors = listOf()),
                    tabIndex = 0
                )
            }
        }

        compareScreenshot(composeTestRule)
    }

    @Composable
    private fun InterestsScreen(uiState: InterestsUiState, tabIndex: Int = 0) {
        InterestsScreen(
            uiState = uiState,
            tabState = InterestsTabState(
                titles = listOf(string.interests_topics, string.interests_people),
                currentIndex = tabIndex
            ),
            followAuthor = { _, _ -> },
            followTopic = { _, _ -> },
            navigateToAuthor = {},
            navigateToTopic = {},
            switchTab = {},
        )
    }
}
