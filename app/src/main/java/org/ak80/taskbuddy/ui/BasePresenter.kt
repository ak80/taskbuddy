package org.ak80.taskbuddy.ui

interface BasePresenter<in T> {

    fun takeView(view: T)

    fun dropView()

}