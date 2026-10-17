package com.jtools.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jtools.app.databinding.ActivityMain

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("JToolsPrefs", Context.MODE_PRIVATE)

        val bottomNav = binding.bottomNavigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    loadFragment(InicioFragment())
                    true
                }
                R.id.nav_herramientas -> {
                    loadFragment(GestionFragment())
                    true
                }
                R.id.nav_estadisticas -> {
                    // Reutiliza o enlaza al panel de ajustes/gestión si se prefiere
                    loadFragment(GestionFragment())
                    true
                }
                R.id.nav_ajustes -> {
                    loadFragment(AjustesFragment())
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.nav_inicio
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
