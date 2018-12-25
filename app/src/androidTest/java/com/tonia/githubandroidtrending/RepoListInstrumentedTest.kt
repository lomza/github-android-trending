package com.tonia.githubandroidtrending

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.repolist.RepoListAdapter
import com.tonia.githubandroidtrending.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("com.tonia.githubandroidtrending", appContext.packageName)
    }

    @Test
    fun repoList_isRefreshActionDisplayed() {
        onView(withId(R.id.action_refresh)).check(matches(isDisplayed()))
    }

    @Test
    fun repoList_check() {
        onView(withId(R.id.recyclerViewRepoList)).check(matches(isDisplayed()))
        onData(`is`(instanceOf(Repo::class.java)))
    }

    @Test
    fun repoList_clickShowsRepoDetails() {
        onView(withId(R.id.recyclerViewRepoList)).perform(RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, click()))
        onView(withId(R.id.parentDetails)).check(matches(isDisplayed()))
    }
}
