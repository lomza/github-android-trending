package com.tonia.githubandroidtrending

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.tonia.githubandroidtrending.model.License
import com.tonia.githubandroidtrending.model.Owner
import com.tonia.githubandroidtrending.model.Repo
import com.tonia.githubandroidtrending.util.getRepoDate
import junit.framework.Assert.assertEquals
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepoDetailsInstrumentedTest {

    private val repo = Repo(
        id = "1",
        name = "octorepo",
        full_name = "github/octorepo",
        html_url = "https://github.com/googlesamples",
        description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
        language = "Kotlin",
        stargazers_count = 23,
        forks_count = 9,
        watchers_count = 48,
        open_issues_count = 14,
        updated_at = "2018-12-25T18:50:51Z",
        owner = Owner(
            login = "github",
            id = 53,
            avatar_url = "https://avatars3.githubusercontent.com/u/7378196?v=4",
            url = "https://api.github.com/users/googlesamples"
        ),
        license = License(
            key = "apache-2.0",
            name = "Apache License 2.0",
            url = "https://api.github.com/licenses/apache-2.0"
        )
    )

    @get:Rule
    val intentTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun showRepoDetailsFragment() {
        intentTestRule.activity.showRepoDetails(repo)
    }

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("com.tonia.githubandroidtrending", appContext.packageName)
    }

    @Test
    fun repoDetails_textLabelsCheck() {
        onView(withId(R.id.textViewStars)).check(matches(withText(repo.stargazers_count.toString())))
        onView(withId(R.id.textViewForks)).check(matches(withText(repo.forks_count.toString())))
        onView(withId(R.id.textViewWatchers)).check(matches(withText(repo.watchers_count.toString())))
        onView(withId(R.id.textViewOpenIssues)).check(matches(withText(repo.open_issues_count.toString())))
        onView(withId(R.id.textViewLanguage)).check(matches(withText(repo.language)))
        onView(withId(R.id.textViewLicense)).check(matches(withText(repo.license?.name)))
        onView(withId(R.id.textViewLastUpdated)).check(matches(withText(getRepoDate(repo.updated_at))))
        onView(withId(R.id.textViewFullName)).check(matches(withText(repo.full_name)))
        onView(withId(R.id.textViewDesc)).check(matches(withText(repo.description)))
    }

    @Test
    fun repoDetails_gitHubButtonIntentCheck() {
        onView(withId(R.id.imageButtonGitHubRepo)).check(matches(isDisplayed()))
        onView(withId(R.id.imageButtonGitHubRepo)).perform(click())
        intended(allOf(hasAction(Intent.ACTION_VIEW), hasData("https://github.com/googlesamples")))
    }

    @Test
    fun repoDetails_shareButtonIntentCheck() {
        onView(withId(R.id.fabShare)).check(matches(isDisplayed()))
        onView(withId(R.id.fabShare)).perform(click())
        intended(
            allOf(
                hasAction("android.intent.action.CHOOSER"),
                hasExtraWithKey("android.intent.extra.INTENT")
            )
        )
    }
}
