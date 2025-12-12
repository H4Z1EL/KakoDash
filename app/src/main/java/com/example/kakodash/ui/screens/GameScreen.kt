package com.example.kakodash.ui.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.example.kakodash.viewmodel.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel
) {
    val context = LocalContext.current

    var isNear by remember { mutableStateOf(false) }

    val playerY by gameViewModel.playerY.collectAsState()
    val obstacleX by gameViewModel.obstacleX.collectAsState()
    val isGameOver by gameViewModel.isGameOver.collectAsState()
    val playerColor by gameViewModel.playerColor.collectAsState()
    val playerName by gameViewModel.playerName.collectAsState()

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(SensorManager::class.java)
        val proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximity != null) {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    val d = event?.values?.firstOrNull() ?: return
                    isNear = d < proximity.maximumRange
                    if (isNear) gameViewModel.jump()
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                listener,
                proximity,
                SensorManager.SENSOR_DELAY_NORMAL
            )

            onDispose { sensorManager.unregisterListener(listener) }
        } else onDispose {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Button(
            onClick = { navController.navigate("edit_profile") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Perfil")
        }

        Text(
            text = playerName,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
        Box(
            modifier = Modifier
                .offset(y = (-playerY * 300).dp)
                .size(50.dp)
                .background(playerColor)
                .align(Alignment.BottomCenter)
        )
        Box(
            modifier = Modifier
                .offset(x = (obstacleX * 300).dp)
                .size(40.dp)
                .background(Color.Red)
                .align(Alignment.BottomCenter)
        )

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
    }
}
