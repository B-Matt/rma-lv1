package com.matejarlovic.famouspeople

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_people_input.*

class PersonEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_people_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val bundle = arguments
        val personId = bundle?.getInt("personId")
        val inspiringPerson = PeopleRepository.instance.getPerson(personId)

        inputPhoto.setText(inspiringPerson?.photoUrl)
        inputName.setText(inspiringPerson?.name)
        inputBirthDate.setText(inspiringPerson?.birthDate)
        inputDeathDate.setText(inspiringPerson?.deathDate)
        inputDescription.setText(inspiringPerson?.description)

        inputQuote1.setText(inspiringPerson?.quotes?.get(0)?.quote)
        inputQuote2.setText(inspiringPerson?.quotes?.get(1)?.quote)
        inputQuote3.setText(inspiringPerson?.quotes?.get(2)?.quote)

        inputButton.setOnClickListener {
            val tmpPerson = InspiringPerson(
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

            if (personId != null) {
                PeopleRepository.instance.set(tmpPerson, personId)
                Toast.makeText(view.context, "You've saved that person in the repository!", Toast.LENGTH_SHORT).show()
            }
        }

        peopleListActivity.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonListFragment())?.commit()
        }
    }
}