package com.matejarlovic.famouspeople

class PersonQuote(val quote: String) {

}

class InspiringPerson(val photoUrl: String, val name: String, private val birthDate: String, private val deathDate: String, val description: String, val quotes: MutableList<PersonQuote>)
{
    fun lifeDates(): String {
        return "$birthDate - $deathDate";
    }
}