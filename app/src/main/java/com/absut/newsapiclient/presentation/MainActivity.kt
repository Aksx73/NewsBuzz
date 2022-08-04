package com.absut.newsapiclient.presentation

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.absut.newsapiclient.R
import com.absut.newsapiclient.databinding.ActivityMainBinding
import com.absut.newsapiclient.presentation.adapter.NewsListAdapter
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModel
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav: BottomNavigationView

    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var factory: NewsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
       // WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this);


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.forYouFragment,
                R.id.HeadlineFragment,
                R.id.searchFragment,
                R.id.SavedNewsFragment
            )
        )
        bottomNav = binding.content.bottomNavigationView
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)


        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.newsDetailFragment) {
                bottomNav.visibility = View.GONE
            } else bottomNav.visibility = View.VISIBLE
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}