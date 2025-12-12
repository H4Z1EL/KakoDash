package com.example.kakodash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.example.kakodash.ui.screens.GameScreen
import com.example.kakodash.ui.screens.ItemsScreen
import com.example.kakodash.ui.screens.EditProfileScreen
import com.example.kakodash.ui.theme.KakoDashTheme
import com.example.kakodash.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KakoDashTheme {
                val navController = rememberNavController()
                val gameViewModel: GameViewModel = viewModel()

                NavHost(navController = navController, startDestination = "game") {

                    composable("game") {
                        GameScreen(
                            navController = navController,
                            gameViewModel = gameViewModel
                        )
                    }

                    composable("items") {
                        ItemsScreen()
                    }

                    composable("edit_profile") {
                        EditProfileScreen(
                            navController = navController,
                            gameViewModel = gameViewModel
                        )
                    }
                }
            }
        }
    }
}