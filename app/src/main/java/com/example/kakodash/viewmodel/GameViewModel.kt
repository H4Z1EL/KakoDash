package com.example.kakodash.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver = _isGameOver.asStateFlow()

    private val _playerY = MutableStateFlow(0f)
    val playerY = _playerY.asStateFlow()

    private val _obstacleX = MutableStateFlow(1f)
    val obstacleX = _obstacleX.asStateFlow()

    // Variables de física (Commit 21)
    private var velocityY = 0f
    private val gravity = -0.003f
    private var obstacleSpeed = 0.01f

    fun jump() {
        // Implementación vendrá en próximos commits
    }

    fun resetGame() {
        _playerY.value = 0f
        _obstacleX.value = 1f
        _isGameOver.value = false
        velocityY = 0f
    }

    // Commit 22: actualizar jugador con gravedad y límite de piso
    private fun updatePlayer() {
        velocityY += gravity
        _playerY.value += velocityY

        if (_playerY.value < 0f) {
            _playerY.value = 0f
            velocityY = 0f
        }
    }

    // Commit 23: movimiento del obstáculo
    private fun updateObstacle() {
        _obstacleX.value -= obstacleSpeed
        if (_obstacleX.value < -1f) {
            _obstacleX.value = 1f
        }
    }
}
