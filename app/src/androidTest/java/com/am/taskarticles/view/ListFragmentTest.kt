package com.am.taskarticles.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.am.taskarticles.R
import com.am.taskarticles.helper.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun onLoadArticles_progressBarGone_recyclerViewVisible() {
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.articles_rv)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun onClickItemInArticleGoToDetails() {

        onView(withId(R.id.articles_rv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.articles_rv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.articles_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                ViewActions.click()
            )
        )
        onView(withId(R.id.details_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onClickItemInArticleGoToDetails_thenGetBackToList() {
        onView(withId(R.id.articles_rv))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.articles_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                ViewActions.click()
            )
        )

        pressBack()

        onView(withId(R.id.articles_rv))
            .check(matches(isDisplayed()))

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }
}