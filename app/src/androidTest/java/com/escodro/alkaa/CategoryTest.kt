package com.escodro.alkaa

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.escodro.shared.MainView
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Step
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AllureAndroidJUnit4::class)
class CategoryTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    @DisplayName("Creating category")
    fun checkCreateCategory() {
        val categoryName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            onNodeWithText("Categories").performClick()
            createCategory(categoryName)
            onNodeWithText(categoryName).assertExists()
        }
    }

    @Test
    @DisplayName("Deleting category")
    fun checkDeleteCategory() {
        val categoryName = Helper.randomString(6)
        composeRule.setContent { MainView() }
        composeRule.apply {
            onNodeWithText("Categories").performClick()
            createCategory(categoryName)
            onNodeWithText(categoryName).performClick()
            Allure.step("Remove category $categoryName")
            onNodeWithContentDescription("Remove").performClick()
            onNodeWithText("Remove").performClick()
            onNodeWithText(categoryName).assertDoesNotExist()
        }
    }

    @Step("Create category {name}")
    fun createCategory(name: String) {
        Allure.step("Create category $name")
        composeRule.onNodeWithContentDescription("Add category").performClick()
        composeRule.onNodeWithText("Category").performTextInput(name)
        composeRule.onNodeWithText("Save").performClick()
    }

}