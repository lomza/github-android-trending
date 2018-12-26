package com.tonia.githubandroidtrending

/**
 * Base presenter to be implemented by all the presenters for MVP architecture.
 */
interface BasePresenter<V> {
    fun attach(view: V)
    fun detach()
}