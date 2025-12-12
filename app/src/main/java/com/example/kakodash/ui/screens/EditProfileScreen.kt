package com.example.kakodash.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kakodash.viewmodel.GameViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    gameViewModel: GameViewModel
) {
    var name by remember { mutableStateOf(gameViewModel.playerName.value) }
    var selectedColor by remember { mutableStateOf(gameViewModel.playerColor.value) }

    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Editar Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del jugador") }
        )

        Spacer(Modifier.height(20.dp))

        Text("Color del personaje:")

        Row(Modifier.padding(10.dp)) {
            listOf(Color.Cyan, Color.Green, Color.Magenta, Color.Yellow).forEach { c ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .background(c)
                        .border(
                            width = if (selectedColor == c) 3.dp else 0.dp,
                            color = Color.White
                        )
                        .clickable { selectedColor = c }
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {
                gameViewModel.updateProfile(name, selectedColor)
                navController.popBackStack()
            }
        ) {
            Text("Guardar")
        }

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
                gameViewModel.deleteProfile()
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Reiniciar Perfil", color = Color.White)
        }
    }
}
