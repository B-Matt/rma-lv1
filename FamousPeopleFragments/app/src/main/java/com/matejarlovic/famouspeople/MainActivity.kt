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

    class MainViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager)
    {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(frag: Fragment, title: String)
        {
            fragmentList.add(frag)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
