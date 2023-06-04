package com.firatgunay.clickthebutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import com.firatgunay.clickthebutton.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer
    private var score = 0
    private var timeLeft = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.gameButton.setOnClickListener {
            increaseScore()
            moveButton()
        }

        startTimer()
        moveButton()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft--
                binding.timeTextView.text = "Time: $timeLeft"
            }

            override fun onFinish() {
                binding.gameButton.isVisible = false
                binding.gameOverTextView.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun increaseScore() {
        score++
        binding.scoreTextView.text = "Score: $score"
    }

    private fun moveButton() {
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        binding.gameButton.post {
            val randomX = Random.nextInt(0, screenWidth - binding.gameButton.width)
            val randomY = Random.nextInt(0, screenHeight - binding.gameButton.height)

            val maxX = screenWidth - binding.gameButton.width
            val maxY = screenHeight - binding.gameButton.height

            val x = randomX.coerceIn(0, maxX)
            val y = randomY.coerceIn(0, maxY)

            binding.gameButton.x = x.toFloat()
            binding.gameButton.y = y.toFloat()
        }
    }
}