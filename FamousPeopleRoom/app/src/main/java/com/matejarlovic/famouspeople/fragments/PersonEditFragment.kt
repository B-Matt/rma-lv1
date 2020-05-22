package com.matejarlovic.famouspeople.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matejarlovic.famouspeople.R
import com.matejarlovic.famouspeople.room.InspiringPerson
import com.matejarlovic.famouspeople.room.PersonDatabase
import kotlinx.android.synthetic.main.fragment_people_input.*

class PersonEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_people_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val personName = bundle?.getString("personName") ?: return
        val personDao = PersonDatabase.getInstance(context!!).personDao()
        val inspiringPerson = personDao.getPersonByName(personName)

        inputPhoto.setText(inspiringPerson.photoUrl)
        inputName.setText(inspiringPerson.name)
        inputBirthDate.setText(inspiringPerson.birthDate)
        inputDeathDate.setText(inspiringPerson.deathDate)
        inputDescription.setText(inspiringPerson.description)

        inputQuote1.setText(inspiringPerson.quotes[0])
        inputQuote2.setText(inspiringPerson.quotes[1])
        inputQuote3.setText(inspiringPerson.quotes[2])

        inputButton.setOnClickListener {
            val tmpPerson = InspiringPerson(
                inputPhoto.text.toString(),
                inputName.text.toString(),
                inputBirthDate.text.toString(),
                inputDeathDate.text.toString(),
                inputDescription.text.toString(),
                arrayListOf(
                    inputQuote1.text.toString(),
                    inputQuote2.text.toString(),
                    inputQuote3.text.toString()
                )
            )

            personDao.updatePerson(tmpPerson)
            Toast.makeText(
                view.context,
                "You've saved that person in the repository!",
                Toast.LENGTH_SHORT
            ).show()
        }

        peopleListActivity.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonListFragment())
                ?.commit()
        }
    }
}