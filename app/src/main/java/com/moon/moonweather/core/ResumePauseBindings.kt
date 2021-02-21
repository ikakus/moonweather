package com.moon.moonweather.core

import androidx.lifecycle.LifecycleOwner
import com.badoo.mvicore.android.lifecycle.ResumePauseBinderLifecycle
import com.badoo.mvicore.binder.Binder

abstract class ResumePauseBindings<Lc : LifecycleOwner> {
    protected var binder: Binder? = null

    private fun setBinder(lifecycleOwner: LifecycleOwner) {
        binder?.dispose()
        binder = Binder(
            lifecycle = ResumePauseBinderLifecycle(
                androidLifecycle = lifecycleOwner.lifecycle
            )
        )
    }

    open fun setup(view: Lc) {
        setBinder(view)
    }
}