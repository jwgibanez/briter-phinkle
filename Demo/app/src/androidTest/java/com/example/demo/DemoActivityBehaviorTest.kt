package com.example.demo

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@MediumTest
class DemoActivityBehaviorTest {

    companion object {
        const val FIRST_COIN_ASC = "BNB"
        const val FIRST_COIN_DES = "XRP"
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<DemoActivity> = ActivityScenarioRule(DemoActivity::class.java)

    @Test
    fun buttons_AllButtonsWork() {

        /* CLEAR BUTTON 1 */

        clearButton_ClearsList()

        /* LOAD, SORT ASC & SORT DES */

        onView(withId(R.id.load)).perform(click())

        // Default initial sort is ASC
        onView(withId(R.id.recycler_view))
            .check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter as CurrencyListAdapter

                // There should be currencies displayed
                Assert.assertTrue(adapter.itemCount > 0)
                // First item should be FIRST_COIN_ASC
                Assert.assertEquals(FIRST_COIN_ASC, adapter.currencies[0].symbol)
            }

        // Clicking again will sort DES
        onView(withId(R.id.load)).perform(click())

        onView(withId(R.id.recycler_view))
            .check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter as CurrencyListAdapter

                // There should be currencies displayed
                Assert.assertTrue(adapter.itemCount > 0)
                // First item should be FIRST_COIN_DES
                Assert.assertEquals(FIRST_COIN_DES, adapter.currencies[0].symbol)
            }

        /* CLEAR BUTTON 2 */

        clearButton_ClearsList()
    }

    private fun clearButton_ClearsList() {
        onView(withId(R.id.clear)).perform(click())

        // There should be 0 currencies displayed
        onView(withId(R.id.recycler_view))
            .check { view, noViewFoundException ->
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }
                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter as CurrencyListAdapter
                Assert.assertTrue(adapter.itemCount == 0)
            }
    }
}