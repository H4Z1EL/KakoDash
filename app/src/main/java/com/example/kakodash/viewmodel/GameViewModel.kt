package com.example.kakodash.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _playerName = MutableStateFlow("Jugador")
    val playerName = _playerName.asStateFlow()

    private val _playerColor = MutableStateFlow(Color.Cyan)
    val playerColor = _playerColor.asStateFlow()

    fun getProfile(): Pair<String, Color> = Pair(_playerName.value, _playerColor.value)

    fun createProfile(name: String, color: Color) {
        _playerName.value = name
        _playerColor.value = color
    }

    fun updateProfile(name: String, color: Color) {
        _playerName.value = name
        _playerColor.value = color
    }

    fun deleteProfile() {
        _playerName.value = "Jugador"
        _playerColor.value = Color.Cyan
    }

    fun setPlayerName(name: String) {
        _playerName.value = name
    }

    fun setPlayerColor(color: Color) {
        _playerColor.value = color
    }

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver = _isGameOver.asStateFlow()

    private val _playerY = MutableStateFlow(0f)
    val playerY = _playerY.asStateFlow()

    private val _obstacleX = MutableStateFlow(1f)
    val obstacleX = _obstacleX.asStateFlow()

    private var velocityY = 0f
    private val gravity = -0.003f
    private var obstacleSpeed = 0.01f

    init {
        startGameLoop()
    }

    private fun startGameLoop() {
        Thread {
            while (true) {
                if (!_isGameOver.value) {
                    updatePlayer()
                    updateObstacle()
                    checkCollision()
                }
                Thread.sleep(16)
            }
        }.start()
    }

    private fun updatePlayer() {
        velocityY += gravity
        _playerY.value += velocityY

        if (_playerY.value < 0f) {
            _playerY.value = 0f
            velocityY = 0f
        }
    }

    private fun updateObstacle() {
        _obstacleX.value -= obstacleSpeed
        if (_obstacleX.value < -1f) {
            _obstacleX.value = 1f
        }
    }

    fun jump() {
        if (_playerY.value == 0f) {
            velocityY = 0.05f
        }
    }

    private fun checkCollision() {
        val touchingX = _obstacleX.value in -0.1f..0.1f
        val touchingY = _playerY.value < 0.15f
        if (touchingX && touchingY) _isGameOver.value = true
    }

    fun resetGame() {
        _playerY.value = 0f
        _obstacleX.value = 1f
        velocityY = 0f
        _isGameOver.value = false
    }
}