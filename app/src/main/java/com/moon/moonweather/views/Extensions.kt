package com.moon.moonweather.views

import androidx.fragment.app.Fragment

fun Fragment.loading(show: Boolean) {
    (activity as GlobalLoading).loading(show)
}