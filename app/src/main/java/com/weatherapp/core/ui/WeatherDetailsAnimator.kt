package com.weatherapp.core.ui

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weatherapp.core.ext.cancelTransition
import javax.inject.Inject


class WeatherDetailsAnimator
@Inject constructor() {

    private val TRANSITION_DELAY = 200L
    private val TRANSITION_DURATION = 400L


    internal fun postponeEnterTransition(fragment: Fragment) = fragment.postponeEnterTransition()
    internal fun cancelTransition(view: View) = view.cancelTransition()

    internal fun fadeVisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.VISIBLE)

    internal fun fadeInvisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.INVISIBLE)

    private fun beginTransitionFor(viewContainer: ViewGroup, view: View, visibility: Int) {
        viewContainer.postDelayed({
            val transition = Fade()
            transition.duration = TRANSITION_DURATION
            TransitionManager.beginDelayedTransition(viewContainer, transition)
            view.visibility = visibility
        }, TRANSITION_DELAY)
    }
}


