package com.google.samples.apps.nowinandroid.interests

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

class InterestsScreenshotTest : ScreenshotTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun niaLoadingWheel_inTopics_whenScreenIsLoading_showLoading() {
        composeTestRule.setContent {
            Surface(modifier = Modifier.size(300.dp)) {
                Text("Hello Shot")
            }
        }

        compareScreenshot(composeTestRule)
    }
}
