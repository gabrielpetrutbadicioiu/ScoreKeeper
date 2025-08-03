package ro.gtechco.scorekeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gtechco.scorekeeper.presentantion.MainScreen
import ro.gtechco.scorekeeper.presentantion.MainScreenViewModel
import ro.gtechco.scorekeeper.ui.theme.ScoreKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScoreKeeperTheme {
                val mainScreenViewModel= getViewModel<MainScreenViewModel> ()
                MainScreen(viewModel = mainScreenViewModel)
            }
        }
    }
}
