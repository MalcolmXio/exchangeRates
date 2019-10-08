package com.exchangeRates.core.platform

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.exchangeRates.R
import com.exchangeRates.core.ext.inTransaction
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Base Activity class with helper methods for handling showStartFragment transactions and back button
 * events.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
            R.id.fragmentContainer
        ) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    fun addNextFragment(fragment: BaseFragment, sharedElement: View) {
        supportFragmentManager.inTransaction {
            fragment.sharedElementEnterTransition = TransitionInflater.from(this@BaseActivity)
                .inflateTransition(R.transition.changetransform)

            fragment.enterTransition = TransitionInflater.from(this@BaseActivity)
                .inflateTransition(R.transition.fadetransform)

            setReorderingAllowed(true)
            replace(
                R.id.fragmentContainer, fragment
            )
            addToBackStack(null)
            addSharedElement(sharedElement, sharedElement.transitionName)
        }
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer, showStartFragment()
            )
        }

    abstract fun showStartFragment(): BaseFragment
}