package com.louis.portto_examination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navContoller = findNavController()
        appBarConfiguration = AppBarConfiguration(navContoller.graph)
        setupActionBarWithNavController(navContoller, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun findNavController(): NavController {
        return findNavController(R.id.nav_host_fragment)
    }


}