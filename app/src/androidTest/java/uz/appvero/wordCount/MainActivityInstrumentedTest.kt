package uz.appvero.wordCount

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editText_isEmptyByDefault() {
        onView(withId(R.id.editText))
            .check(matches(withText("")))
    }

    @Test
    fun editText_isNotEmptyAfterTyping() {
        onView(withId(R.id.editText))
            .perform(typeText("Salom Dunyo"))
        onView(withId(R.id.editText))
            .check(matches(withText("Salom Dunyo")))
    }

    @Test
    fun countWords_isCorrect() {
        onView(withId(R.id.editText))
            .perform(typeText("Bu bir test."))
        onView(withId(R.id.resultTextViewWord))
            .check(matches(withText("3"))) // Kutilgan natija: 3 ta so'z
    }

    @Test
    fun countChars_isCorrect() {
        onView(withId(R.id.editText))
            .perform(typeText("Test"))
        onView(withId(R.id.resultTextViewChar))
            .check(matches(withText("4"))) // Kutilgan natija: 4 ta harf
    }

    @Test
    fun countSpaces_isCorrect() {
        onView(withId(R.id.editText))
            .perform(typeText("Bu ikki  probel")) // ikki probel
        onView(withId(R.id.resultTextViewSpace))
            .check(matches(withText("3"))) // Kutilgan natija: 3 ta probel
    }

    @Test
    fun initialCounts_areZero() {
        onView(withId(R.id.resultTextViewWord))
            .check(matches(withText("0")))
        onView(withId(R.id.resultTextViewChar))
            .check(matches(withText("0")))
        onView(withId(R.id.resultTextViewSpace))
            .check(matches(withText("0")))
    }

    @Test
    fun themeSwitch_changesTheme() {
        onView(withId(R.id.switch_btn)).perform(click())
        // TODO: Mavzu o'zgarganini tasdiqlovchi qo'shimcha tekshiruv qo'shing
    }
}