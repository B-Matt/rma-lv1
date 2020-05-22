package com.matejarlovic.famouspeople.room

import androidx.room.*

@Dao
interface PersonDao {

    @Transaction
    @Query("SELECT * FROM persons")
    fun getPersons(): List<InspiringPerson>

    @Transaction
    @Query("SELECT * FROM persons WHERE name = :name")
    fun getPersonByName(name: String): InspiringPerson

    @Query("DELETE FROM persons WHERE name = :name")
    fun deletePerson(name: String)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertPerson(person: InspiringPerson)

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertPersons(person: List<InspiringPerson>)

    @Update
    fun updatePerson(person: InspiringPerson)
}