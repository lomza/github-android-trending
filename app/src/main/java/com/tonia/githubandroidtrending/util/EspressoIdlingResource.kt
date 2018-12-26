package com.tonia.githubandroidtrending.util

import androidx.test.espresso.IdlingResource

/**
 * Object to be used in Espresso tests, whenever idling the resource is necessary,
 * such as waiting for the data to be load.
 */
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }
}
