package com.matejarlovic.famouspeople.room

import androidx.room.*


@Entity(tableName = "persons")
data class InspiringPerson(

    @ColumnInfo(name = "photo_url")
    val photoUrl: String,

    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "birth_date")
    val birthDate: String,

    @ColumnInfo(name = "death_date")
    val deathDate: String,

    val description: String,

    val quotes: ArrayList<String>
) {
    fun lifeDates(): String {
        return "$birthDate - $deathDate";
    }
}