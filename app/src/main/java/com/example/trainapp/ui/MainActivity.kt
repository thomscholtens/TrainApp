package com.example.trainapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.trainapp.R
import com.example.trainapp.viewmodel.RouteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        viewModel.getAllStations()
        navController = findNavController(R.id.nav_host_fragment)
        fabOnClick()
        fabToggler()
    }

    private fun initActionBar() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun fabOnClick() {
        fab.setOnClickListener {
            navController.navigate(
                R.id.action_myRoutesFragment_to_addRouteFragment
            )
        }
    }

    private fun fabToggler() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.myRoutesFragment) {
                fab.show()
            } else {
                fab.hide()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}