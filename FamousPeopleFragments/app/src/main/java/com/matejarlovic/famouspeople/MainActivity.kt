package com.matejarlovic.famouspeople

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(PersonListFragment(), "Famuous People List")
        adapter.addFragment(PersonInputFragment(), "Famuous People Input")

        personsPager.adapter = adapter
        tabs.setupWithViewPager(personsPager)
    }
}
