package com.example.block_take_home_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.block_take_home_project.ui.EmployeeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check savedInstanceState to ensure we don't re-create EmployeeFragment when Fragment is destroyed
        // single activity architecture
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.app_container, EmployeeFragment())
            }
        }
    }
}