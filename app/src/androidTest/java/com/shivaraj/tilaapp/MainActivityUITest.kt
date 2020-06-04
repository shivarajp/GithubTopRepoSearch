package com.shivaraj.tilaapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.shivaraj.tilaapp.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class MainActivityUITest{


    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()


    val STRING_TO_BE_TYPED: String = "koral"

    @Test
    fun test_changeText_sameActivity() {

        onView(ViewMatchers.withId(R.id.searchView))
            .perform(ViewActions.typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard())

        //here we have to check this library adapter list has items
        //didn't get how to access the adapter item with withId()

        //onView(withId(com.arlib.floatingsearchview.R.id.body)).check(matches(withText(STRING_TO_BE_TYPED)))
    }

    fun getItemCount(): Int{

        var count = 0

        val rv = activityScenarioRule.scenario.onActivity {

            count = it.findViewById<RecyclerView>(R.id.recyclerView).adapter?.itemCount!!
        }

        return count
    }

}