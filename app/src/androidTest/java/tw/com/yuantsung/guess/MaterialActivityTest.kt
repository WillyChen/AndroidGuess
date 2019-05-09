package tw.com.yuantsung.guess


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)


    @Test
    fun guessWrong() {
        val resource = activityTestRule.activity.resources
        var secret = activityTestRule.activity.secretNumber.secret
        for ( n in 1..10) {
            inputNumer(n)

            val message = if (n > secret) resource.getString(R.string.bigger)
            else resource.getString(R.string.smaller)

            onView(withText(message)).check(matches(isDisplayed()))
            onView(withText(resource.getString(R.string.ok))).perform(click())
        }

    }

    private fun inputNumer(n: Int) {
        onView(withId(R.id.numberText)).perform(clearText())
        onView(withId(R.id.numberText)).perform(typeText(n.toString()))
        onView(withId(R.id.okBtn)).perform(click())
    }

    @Test
    fun replay(){
        val resource = activityTestRule.activity.resources
        inputNumer(2)
        onView(withText(resource.getString(R.string.ok))).perform(click()).perform(closeSoftKeyboard())
        onView(withId(R.id.fab)).perform(click())
        onView(withText(R.string.ok)).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))
    }
}