package com.matejarlovic.famouspeople

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_people_input.*

class PersonInputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_people_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
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

            Toast.makeText(view.context, "You've saved that person in the repository!", Toast.LENGTH_SHORT).show()
        }

        peopleListActivity.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonListFragment())?.commit()
        }
    }
}
