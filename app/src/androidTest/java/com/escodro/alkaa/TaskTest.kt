package com.escodro.alkaa

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.escodro.shared.MainView
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Step
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class TasksTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    @DisplayName("Check you can't create task with empty name")
    fun cannotCreateEmptyTask() {
        composeRule.setContent { MainView() }
        composeRule.apply {
            onNodeWithContentDescription("Add task").performClick()
            onNodeWithText("Add").performClick()
            onNodeWithText("Wow! All tasks are completed!").assertIsDisplayed()
        }
    }

    @Test
    @DisplayName("Creating task without category")
    fun createTaskWithoutCategory() {
        val taskName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            createTask(taskName)
            onNodeWithText(taskName).assertIsDisplayed()
            completeTask(taskName)
        }
    }

    @Test
    @DisplayName("Creating task with category")
    fun createTaskWithCategory() {
        val taskName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            Allure.step("Create task $taskName with category")
            onNodeWithContentDescription("Add task").performClick()
            onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(taskName)
            onAllNodes(hasText("Work", substring = true)).get(1).performClick()
            onNodeWithText("Add").performClick()
            onNodeWithText(taskName).assertIsDisplayed()
            onNodeWithText("Work").performClick()
            onNodeWithText(taskName).assertIsDisplayed()
            onNodeWithText("Personal").performClick()
            onNodeWithText(taskName).assertDoesNotExist()
            onNodeWithText("Work").performClick()
            completeTask(taskName)
        }
    }

    @Test
    @DisplayName("Creating task with description")
    fun createTaskDescription() {
        val taskName = Helper.randomString(6)
        val description = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            createTask(taskName)
            onNodeWithText(taskName).performClick()
            Allure.step("Add description to task")
            onNodeWithContentDescription("Description").performClick()
            onNodeWithContentDescription("Description").performTextInput(description)
            onNodeWithContentDescription("Back").performClick()
            onNodeWithText(taskName).performClick()
            onNodeWithContentDescription("Description").assertTextEquals(description)
            onNodeWithContentDescription("Back").performClick()
            completeTask(taskName)
        }
    }

    @Test
    @DisplayName("Completing task")
    fun completeTask() {
        val taskName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            createTask(taskName)
            completeTask(taskName)
            onNodeWithText("Task completed").assertIsDisplayed()
            onNodeWithText(taskName).assertIsNotDisplayed()
            onNodeWithContentDescription("Search", useUnmergedTree = true).performClick()
            onNodeWithText(taskName).assertIsDisplayed()
        }
    }

    @Step("Create task {name}")
    fun createTask(name: String) {
        Allure.step("Create task $name")
        composeRule.onNodeWithContentDescription("Add task").performClick()
        composeRule.onAllNodes(hasText("Task", substring = true)).get(2).performTextInput(name)
        composeRule.onNodeWithText("Add").performClick()
    }

    fun completeTask(name: String) {
        Allure.step("Complete task $name")
        composeRule.onNodeWithText(name).onChild().performClick()
    }

}