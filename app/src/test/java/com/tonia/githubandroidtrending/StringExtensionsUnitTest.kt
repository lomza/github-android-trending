package com.tonia.githubandroidtrending

import com.tonia.githubandroidtrending.util.valueOrUnknown
import org.junit.Assert.*
import org.junit.Test

class StringExtensionsUnitTest {

    @Test
    fun `String extension - return value if provided`() {
        // GIVEN
        val license = "Apache 2.0 License"
        val licenseTwo = "MIT License"

        // WHEN

        // THEN
        assertEquals(license, license.valueOrUnknown())
        assertEquals(licenseTwo, licenseTwo.valueOrUnknown())
    }

    @Test
    fun `String extension - return ? if not provided`() {
        // GIVEN
        val license = null
        val licenseTwo = ""
        val licenseThree = "       "

        // WHEN

        // THEN
        assertEquals("?", license.valueOrUnknown())
        assertEquals("?", licenseTwo.valueOrUnknown())
        assertEquals("?", licenseThree.valueOrUnknown())
    }
}
