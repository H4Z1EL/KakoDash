package com.example.kakodash.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kakodash.sensor.ProximitySensor
import com.example.kakodash.viewmodel.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel
) {
    val playerY by gameViewModel.playerY.collectAsState()
    val obstacleX by gameViewModel.obstacleX.collectAsState()
    val isGameOver by gameViewModel.isGameOver.collectAsState()

    val context = LocalContext.current
    var isNear by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val sensor = ProximitySensor(
            context,
            onNear = {
                isNear = true
                gameViewModel.jump()     // SALTO
            },
            onFar = {
                isNear = false
            }
        )

        sensor.start()
        onDispose { sensor.stop() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Jugador
        Box(
            modifier = Modifier
                .offset(y = (-playerY * 300).dp)
                .size(50.dp)
                .background(Color.Cyan)
                .align(Alignment.BottomCenter)
        )

        // Obstáculo
        Box(
            modifier = Modifier
                .offset(x = (obstacleX * 300).dp)
                .size(40.dp)
                .background(Color.Red)
                .align(Alignment.BottomCenter)
        )

        // GAME OVER
        if (isGameOver) {
            Column(
                Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("GAME OVER", color = Color.White)
                Spacer(Modifier.height(10.dp))
                Text("Cubre el sensor para reiniciar", color = Color.LightGray)
                if (isNear) gameViewModel.resetGame()
            }
        }


        // Botón perfil (temporal)
        Button(
            onClick = { navController.navigate("edit_profile") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Perfil")
        }
    }
}
