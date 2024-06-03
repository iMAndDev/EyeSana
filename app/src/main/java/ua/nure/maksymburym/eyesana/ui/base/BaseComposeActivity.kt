package ua.nure.maksymburym.eyesana.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import ua.nure.maksymburym.eyesana.ui.resources.EyeSanaTheme

abstract class BaseComposeActivity : ComponentActivity() {

    private var backPressedAction: (() -> Unit)? = null
    @Composable
    abstract fun SetContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EyeSanaTheme {
                SetContent()
            }
        }
    }

    /**
     * Handles 'back-pressed' events.
     *
     * **NOTE**: It does NOT calls 'finish()' itself.
     *
     * **NOTE 2**: Child fragment-added [onBackPressedDispatcher.addCallback()] usages
     * are ignored by calling this method.
     */
    protected fun setupBackPressedDispatcher(backPressedAction: () -> Unit) {
        this.backPressedAction = backPressedAction
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressedAction()
            }
        })
    }

    fun callOnBackPressed() {
        backPressedAction?.invoke() ?: finish()
    }
}