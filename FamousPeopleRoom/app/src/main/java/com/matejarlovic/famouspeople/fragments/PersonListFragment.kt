package com.matejarlovic.famouspeople.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matejarlovic.famouspeople.PersonInputFragment
import com.matejarlovic.famouspeople.R
import com.matejarlovic.famouspeople.adapters.ItemClickType
import com.matejarlovic.famouspeople.adapters.PersonListAdapter
import com.matejarlovic.famouspeople.room.PersonDatabase
import kotlinx.android.synthetic.main.fragment_person_list.*


class PersonListFragment : Fragment(), PersonListAdapter.ContentListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_person_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Get Room instance and person data
        val personData = context?.let { PersonDatabase.getInstance(it).personDao().getPersons() }

        // Apply Person List Adapter to the view
        personList.layoutManager = LinearLayoutManager(view.context)
        personList.adapter = PersonListAdapter(personData?.toMutableList(), this)

        // Click Listeners
        inputPeopleButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonInputFragment())?.commit()
        }
    }

    override fun onItemClick(id: Int, name: String, clickType: ItemClickType) {

        if(clickType == ItemClickType.EDIT) {
            val bundle = Bundle()
            bundle.putString("personName", name)

            val editFragment = PersonEditFragment()
            editFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.container, editFragment)?.commit()
        }
        else if(clickType == ItemClickType.REMOVE) {

            context?.let { PersonDatabase.getInstance(it).personDao().deletePerson(name) }
            (personList.adapter as PersonListAdapter).removeItem(id)
        }
    }
}
