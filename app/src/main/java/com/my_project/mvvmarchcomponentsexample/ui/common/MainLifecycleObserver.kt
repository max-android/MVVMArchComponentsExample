package com.my_project.mvvmarchcomponentsexample.ui.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log

class MainLifecycleObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {

        when(event){
            Lifecycle.Event.ON_CREATE ->  Log.d("DEBAG","ON_CREATE")
            Lifecycle.Event.ON_START ->  Log.d("DEBAG","ON_START")
            Lifecycle.Event.ON_RESUME ->  Log.d("DEBAG","ON_RESUME")
            Lifecycle.Event.ON_PAUSE ->  Log.d("DEBAG","ON_PAUSE")
            Lifecycle.Event.ON_STOP ->  Log.d("DEBAG","ON_STOP")
            Lifecycle.Event.ON_DESTROY ->  Log.d("DEBAG","ON_DESTROY")
            else -> Log.d("DEBAG","NOTHING")
        }

        when(source.lifecycle.currentState){
            Lifecycle.State.CREATED ->  Log.d("DEBAG","CREATED")
            Lifecycle.State.INITIALIZED ->  Log.d("DEBAG","INITIALIZED")
            Lifecycle.State.STARTED ->  Log.d("DEBAG","STARTED")
            Lifecycle.State.RESUMED ->  Log.d("DEBAG","RESUMED")
            Lifecycle.State.DESTROYED ->  Log.d("DEBAG","DESTROYED")
            else -> Log.d("DEBAG","NOTHING")
        }
    }
}