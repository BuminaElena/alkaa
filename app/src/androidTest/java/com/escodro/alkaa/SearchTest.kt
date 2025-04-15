package com.escodro.alkaa

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.printToLog
import com.escodro.shared.MainView
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class SearchTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    @DisplayName("Searching existing task")
    fun searchTask() {
        val taskName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            onNodeWithContentDescription("Add task").performClick()
            onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(taskName)
            onNodeWithText("Add").performClick()
            onNodeWithContentDescription("Add task").performClick()
            onAllNodes(hasText("Task", substring = true)).get(2).performTextInput("taskName")
            onNodeWithText("Add").performClick()
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()
            Allure.step("Search task $taskName")
            onNodeWithContentDescription("Search").performTextInput(taskName)
            onAllNodesWithText(taskName).filter(! isEditable()).get(0).assertIsDisplayed()
            onNodeWithText("taskName").assertIsNotDisplayed()
            onNodeWithText("Tasks").performClick()
            Allure.step("Complete task $taskName")
            onNodeWithText(taskName).onChild().performClick()
            Allure.step("Complete task 'taskName'")
            onNodeWithText("taskName").onChild().performClick()
        }
    }

    @Test
    @DisplayName("Searching non-existing task")
    fun searchNonExistentTask() {
        val taskName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            Allure.step("Create task $taskName")
            onNodeWithContentDescription("Add task").performClick()
            onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(taskName)
            onNodeWithText("Add").performClick()
            Allure.step("Search task 'taskName'")
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()
            onNodeWithContentDescription("Search").performTextInput("taskName")
            onNodeWithText("No tasks found").assertIsDisplayed()
            onNodeWithText("Tasks").performClick()
            Allure.step("Complete task $taskName")
            onNodeWithText(taskName).onChild().performClick()
        }
    }
}