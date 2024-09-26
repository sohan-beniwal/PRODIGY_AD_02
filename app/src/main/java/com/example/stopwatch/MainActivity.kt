package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var milliseconds = 0L  // To track time in milliseconds
    private var isRunning = false
    private lateinit var handler: Handler

    // UI components
    private lateinit var minuteTextView: TextView
    private lateinit var secondTextView: TextView
    private lateinit var millisecondTextView: TextView
    private lateinit var circularProgressBar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button

    // Max progress of circular progress bar (e.g., 60,000ms = 1 minute)
    private val maxProgress = 60000  // 1 minute in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        minuteTextView = findViewById(R.id.minuteTextView)
        secondTextView = findViewById(R.id.secondTextView)
        millisecondTextView = findViewById(R.id.millisecondTextView)
        circularProgressBar = findViewById(R.id.circularProgressBar)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)

        handler = Handler(Looper.getMainLooper())

        // Set click listeners for buttons
        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }
        resetButton.setOnClickListener { resetTimer() }
    }

    private fun startTimer() {
        if (!isRunning) {
            isRunning = true
            handler.post(timerRunnable)
        }
    }

    private fun stopTimer() {
        isRunning = false
    }

    private fun resetTimer() {
        isRunning = false
        milliseconds = 0L
        circularProgressBar.progress = 0
        updateTimerText()
    }

    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                milliseconds += 10  // Increment by 10 milliseconds
                updateTimerText()

                // Update the progress of the circular progress bar
                circularProgressBar.progress = (milliseconds % maxProgress).toInt()

                // Re-post the handler after 10 milliseconds
                handler.postDelayed(this, 10)
            }
        }
    }

    private fun updateTimerText() {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / 1000) / 60
        val millis = milliseconds % 1000 / 10  // Show hundredths of a second

        // Update each TextView individually
        minuteTextView.text = String.format("%02d", minutes)
        secondTextView.text = String.format("%02d", seconds)
        millisecondTextView.text = String.format("%02d", millis)
    }
}
