package com.tonia.githubandroidtrending

import android.util.Log
import com.tonia.githubandroidtrending.util.getRepoDate
import com.tonia.githubandroidtrending.util.repoDateInputFormatter
import com.tonia.githubandroidtrending.util.repoDateOutputFormatter
import org.junit.Test

import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.TimeZone

@RunWith(PowerMockRunner::class)
@PrepareForTest(Log::class)
class RepoDatesUnitTest {

    @Test
    fun `Repo date - correct date parsing check`() {
        // GIVEN
        val repoDateOne = "2018-12-24T13:02:49Z"
        val repoDateOneExpectedString = "24 Dec 2018, 13:02"
        val repoDateTwo = "2017-01-03T03:07:45Z"
        val repoDateTwoExpectedString = "3 Jan 2017, 03:07"
        val repoDateThree = "2009-09-30T11:13:58Z"
        val repoDateThreeExpectedString = "30 Sep 2009, 11:13"

        // WHEN
        repoDateInputFormatter.timeZone = TimeZone.getTimeZone("GMT")
        repoDateOutputFormatter.timeZone = TimeZone.getTimeZone("GMT")

        // THEN
        assertEquals(repoDateOneExpectedString, getRepoDate(repoDateOne))
        assertEquals(repoDateTwoExpectedString, getRepoDate(repoDateTwo))
        assertEquals(repoDateThreeExpectedString, getRepoDate(repoDateThree))
    }

    @Test
    fun `Repo date - wrong date parsing check`() {
        // GIVEN
        PowerMockito.mockStatic(Log::class.java)

        val repoDateOne = null
        val repoDateOneExpectedString = ""
        val repoDateTwo = " "
        val repoDateTwoExpectedString = ""
        val repoDateThree = "2014-09-23"
        val repoDateThreeExpectedString = ""

        // WHEN
        repoDateInputFormatter.timeZone = TimeZone.getTimeZone("GMT")
        repoDateOutputFormatter.timeZone = TimeZone.getTimeZone("GMT")

        // THEN
        assertEquals(repoDateOneExpectedString, getRepoDate(repoDateOne))
        assertEquals(repoDateTwoExpectedString, getRepoDate(repoDateTwo))
        assertEquals(repoDateThreeExpectedString, getRepoDate(repoDateThree))
    }
}
