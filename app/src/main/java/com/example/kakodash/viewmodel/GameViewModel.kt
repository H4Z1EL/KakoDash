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

    fun jump() {
        // Implementación vendrá en próximos commits
    }

    fun resetGame() {
        _playerY.value = 0f
        _obstacleX.value = 1f
        _isGameOver.value = false
    }
}
