package ua.nure.maksymburym.eyesana.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import ua.nure.maksymburym.eyesana.ui.resources.EyeSanaTheme

abstract class BaseComposeActivity : ComponentActivity() {

    protected open val viewModel: ViewModel? = null
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

}