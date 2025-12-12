package com.example.kakodash.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakodash.model.Profile
import com.example.kakodash.network.NetworkModule
import com.example.kakodash.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val repo = ProfileRepository(NetworkModule.provideApiService())

    // ---- PROFILE ----
    private val _playerName = MutableStateFlow("Jugador")
    val playerName = _playerName.asStateFlow()

    private val _playerColor = MutableStateFlow(Color.Cyan)
    val playerColor = _playerColor.asStateFlow()

    // ---- GAME STATE ----
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
        loadProfile()
        startGameLoop()
    }

    // ---- COLOR HELPERS ----

    private fun colorFromString(s: String): Color {
        return when (s.lowercase()) {
            "green" -> Color.Green
            "magenta" -> Color.Magenta
            "yellow" -> Color.Yellow
            else -> Color.Cyan
        }
    }

    private fun colorToString(c: Color): String {
        return when (c.toArgb()) {
            Color.Green.toArgb() -> "green"
            Color.Magenta.toArgb() -> "magenta"
            Color.Yellow.toArgb() -> "yellow"
            Color.Cyan.toArgb() -> "cyan"
            else -> "cyan"
        }
    }

    // ---- SERVER ACTIONS ----

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val p = repo.getProfile()
                _playerName.value = p.name
                _playerColor.value = colorFromString(p.color)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProfile(name: String, color: Color) {
        viewModelScope.launch {
            try {
                val profile = Profile(
                    name = name,
                    color = colorToString(color)
                )

                repo.updateProfile(profile)
                loadProfile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteProfile() {
        viewModelScope.launch {
            try {
                repo.deleteProfile()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            _playerName.value = "Jugador"
            _playerColor.value = Color.Cyan
        }
    }

    // ---- GAME LOOP ----

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
