package com.matejarlovic.famouspeople

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_people_input.*

class PeopleInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_input)

        inputButton.setOnClickListener {
            val newPerson = InspiringPerson(inputName.text.toString(), inputBirthDate.text.toString(), inputDeathDate.text.toString(), inputDescription.text.toString())
            PeopleRepository.instance.add(newPerson)
        }
    }
}
