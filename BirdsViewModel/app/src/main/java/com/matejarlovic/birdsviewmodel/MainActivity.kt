package com.matejarlovic.birdsviewmodel

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matejarlovic.birdsviewmodel.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ClickCounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init ViewModel
        viewModel = ViewModelProvider(this).get(ClickCounterViewModel::class.java)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.counterViewModel, viewModel)

        // LiveData observers
        val countLiveData: LiveData<Int> = viewModel.getInitialCount()
        countLiveData.observe(this, Observer {
            binding.birdCounter.text = it.toString()
        })

        val colorLiveData: LiveData<Int> = viewModel.getInitialColor()
        colorLiveData.observe(this, Observer {
            binding.mainLayout.setBackgroundColor(it)
        })

        // Click Listeners
        redButton.setOnClickListener {
            updateData(viewModel.getCount() + 1, Color.RED)
        }

        blueButton.setOnClickListener {

            updateData(viewModel.getCount() + 1, Color.BLUE)
        }

        greenButton.setOnClickListener {

            updateData(viewModel.getCount() + 1, Color.GREEN)
        }

        magentaButton.setOnClickListener {
            updateData(viewModel.getCount() + 1, Color.MAGENTA)
        }

        resetButton.setOnClickListener {
            updateData(0, Color.WHITE)
        }
    }

    private fun updateData(newCount: Int, background: Int) {
        viewModel.setCount(newCount)
        viewModel.setColor(background)
    }
}
