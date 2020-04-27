package com.matejarlovic.famouspeople

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_people_input.*

class PeopleInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_input)

        inputButton.setOnClickListener {
            val newPerson = InspiringPerson(
                inputPhoto.text.toString(),
                inputName.text.toString(),
                inputBirthDate.text.toString(),
                inputDeathDate.text.toString(),
                inputDescription.text.toString(),
                listOf(
                    PersonQuote(inputQuote1.text.toString()),
                    PersonQuote(inputQuote2.text.toString()),
                    PersonQuote(inputQuote3.text.toString())
                ) as MutableList<PersonQuote>
            )
            PeopleRepository.instance.add(newPerson)

            inputPhoto.text.clear()
            inputName.text.clear()
            inputBirthDate.text.clear()
            inputDeathDate.text.clear()
            inputDescription.text.clear()

            inputQuote1.text.clear()
            inputQuote2.text.clear()
            inputQuote3.text.clear()

            Toast.makeText(this, "You've saved that person in the repository!", Toast.LENGTH_SHORT).show()
        }

        peopleListActivity.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
