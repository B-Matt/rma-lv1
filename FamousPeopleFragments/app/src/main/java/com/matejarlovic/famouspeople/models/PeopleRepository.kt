package com.matejarlovic.famouspeople

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

    fun set(person: InspiringPerson, index: Int) {
        persons[index] = person
    }

    fun length(): Int
    {
        return persons.size;
    }

    fun getPersons(): MutableList<InspiringPerson>
    {
        return persons;
    }

    fun getPerson(index: Int?): InspiringPerson? {
        if(index == null) {
            return null
        }
        return persons[index]
    }
}