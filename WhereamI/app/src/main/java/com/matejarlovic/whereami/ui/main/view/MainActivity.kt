/*
 * Created by Matej Arlović
 * Copyright (c) 2020. All rights reserved.
 */


package com.matejarlovic.whereami.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matejarlovic.whereami.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.dataContainer, MainFragment.newInstance())
                .commitNow()

            supportFragmentManager.beginTransaction()
                .replace(R.id.mapContainer, MapFragment.newInstance())
                .commitNow()
        }
    }
}
