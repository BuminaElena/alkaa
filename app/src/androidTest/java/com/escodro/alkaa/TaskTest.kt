package com.escodro.alkaa

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class TaskTest {

    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>() // не находит MainActivity
    val composeTestRule = createComposeRule()


    @Test
    fun testMain() {
//        composeTestRule.setContent { AlkaaApp() }
        composeTestRule.onNodeWithText("Personal").assertExists()
    }
}