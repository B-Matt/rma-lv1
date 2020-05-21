package com.matejarlovic.birdssharedpreferences

import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var globalBirdCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadView()

        redButton.setOnClickListener {
            globalBirdCounter += 1

            updateView(globalBirdCounter.toString(), Color.RED)
            saveView()
        }

        blueButton.setOnClickListener {

            globalBirdCounter += 1
            updateView(globalBirdCounter.toString(), Color.BLUE)
            saveView()
        }

        greenButton.setOnClickListener {
            globalBirdCounter += 1

            updateView(globalBirdCounter.toString(), Color.GREEN)
            saveView()
        }

        magentaButton.setOnClickListener {
            globalBirdCounter += 1

            updateView(globalBirdCounter.toString(), Color.MAGENTA)
            saveView()
        }

        resetButton.setOnClickListener {
            globalBirdCounter = 0

            updateView(globalBirdCounter.toString(), Color.WHITE)
            saveView()
        }
    }

    private fun updateView(counter: String, background: Int) {
        birdCounter.text = counter
        mainLayout.setBackgroundColor(background)
    }

    private fun saveView() {
        val sharedPreferences =  getSharedPreferences("BIRDS_PREFERENCE", MODE_PRIVATE)
        val editor: Editor = sharedPreferences.edit()
        val background = mainLayout.background as ColorDrawable

        editor.putInt("layoutColor", background.color);
        editor.putInt("birdCounter", globalBirdCounter);
        editor.apply()
    }

    private fun loadView() {
        val sharedPreferences =  getSharedPreferences("BIRDS_PREFERENCE", MODE_PRIVATE)
        val layoutColor: Int = sharedPreferences.getInt("layoutColor", Color.WHITE)

        globalBirdCounter = sharedPreferences.getInt("birdCounter", 0)
        updateView(globalBirdCounter.toString(), layoutColor)
    }
}
