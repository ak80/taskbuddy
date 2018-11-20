package org.ak80.taskbuddy

interface BasePresenter<in T> {

    fun takeView(view: T)

    fun dropView()

}