package com.matejarlovic.birdsviewmodel

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ClickCounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ClickCounterViewModel::class.java)
        displayView()

        redButton.setOnClickListener {
            updateView(viewModel.getCount() + 1, Color.RED)
        }

        blueButton.setOnClickListener {

            updateView(viewModel.getCount() + 1, Color.BLUE)
        }

        greenButton.setOnClickListener {

            updateView(viewModel.getCount() + 1, Color.GREEN)
        }

        magentaButton.setOnClickListener {
            updateView(viewModel.getCount() + 1, Color.MAGENTA)
        }

        resetButton.setOnClickListener {
            updateView(0, Color.WHITE)
        }
    }

    private fun updateView(newCount: Int, background: Int) {

        viewModel.setCount(newCount)
        viewModel.setColor(background)
        displayView()
    }

    private fun displayView() {

        birdCounter.text = viewModel.getCount().toString()
        mainLayout.setBackgroundColor(viewModel.getColor())
    }
}
