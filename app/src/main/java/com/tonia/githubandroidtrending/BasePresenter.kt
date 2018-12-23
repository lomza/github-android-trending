package com.tonia.githubandroidtrending

interface BasePresenter<V> {
    fun attach(view: V)
    fun detach()
}