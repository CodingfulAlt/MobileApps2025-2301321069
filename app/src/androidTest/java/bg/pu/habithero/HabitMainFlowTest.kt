package bg.pu.habithero

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitMainFlowTest {

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addHabit_showsInMainList() {
        // Отваряме екрана за добавяне
        onView(withId(R.id.btnAddNewHabitCore)).perform(click())

        // Попълваме полетата с replaceText
        onView(withId(R.id.inputHabitNameCore))
            .perform(replaceText("Espresso Habit"), closeSoftKeyboard())

        onView(withId(R.id.inputHabitDescriptionCore))
            .perform(replaceText("UI тест описание"), closeSoftKeyboard())

        onView(withId(R.id.inputGoalPerDayCore))
            .perform(replaceText("3"), closeSoftKeyboard())

        // Запазваме
        onView(withId(R.id.btnSaveHabitCore)).perform(click())

        // Проверяваме, че се вижда в списъка
        onView(withText("Espresso Habit"))
            .check(matches(isDisplayed()))
    }
}
