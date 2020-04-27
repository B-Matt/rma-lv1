package com.matejarlovic.famouspeople

import android.util.Log

class PeopleRepository private constructor() {

    private val persons: MutableList<InspiringPerson> = mutableListOf()

    private object HOLDER {
        val INSTANCE = PeopleRepository()
    }

    companion object {
        val instance: PeopleRepository by lazy { HOLDER.INSTANCE }
    }

    fun add(person: InspiringPerson)
    {
        persons.add(person)
    }

    fun print()
    {
        for (i in persons)
        {
            Log.d("Person", i.name + " | " + i.lifeDates() + " | " + i.description);
        }
    }
}