package com.matejarlovic.famouspeople


class InspiringPerson(val name: String, private val birthDate: String, private val deathDate: String, val description: String)
{
    fun lifeDates(): String {
        return "$birthDate - $deathDate";
    }
}