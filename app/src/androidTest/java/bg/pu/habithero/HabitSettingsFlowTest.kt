package bg.pu.habithero

import android.Manifest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitSettingsFlowTest {

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun openSettings_toggleNotifications() {
        // Отваряме Settings от главния екран
        onView(withId(R.id.btnSettingsCore)).perform(click())

        // Цъкаме switch-а
        onView(withId(R.id.switchNotificationsCore)).perform(click())

        // Проверяваме, че switch-ът все още е видим (реагира)
        onView(withId(R.id.switchNotificationsCore))
            .check(matches(isDisplayed()))
    }
}
