package com.tonia.githubandroidtrending

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.tonia.githubandroidtrending.util.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailsInstrumentedTest {

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
        Assert.assertEquals("com.tonia.githubandroidtrending", appContext.packageName)
    }

    @Test
    fun repoDetails_isShareActionDisplayed() {
        onView(ViewMatchers.withId(R.id.action_share)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun repoDetails_gitHubButtonCheck() {
        onView(allOf(withId(R.id.imageButtonGitHubRepo), isDisplayed(), withText("GitHub")))
    }

    @Test
    fun repoDetails_gitHubButtonIntentCheck() {
        // TODO
    }

    @Test
    fun repoDetails_shareButtonIntentCheck() {
        // TODO
    }
}
