package com.example.pelisapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pelisapp.R
import com.example.pelisapp.databinding.ActivityMainBinding
import com.example.pelisapp.utils.OConstantes
import com.example.pelisapp.viewmodel.ViewModelItems
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModelItems: ViewModelItems by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModelItems.ocultaImagenToolbar.observe(this) {
            if (viewModelItems.ocultaImagenToolbar.value == true) {
                binding.imageView.visibility = View.GONE
            } else {
                binding.imageView.visibility = View.VISIBLE
            }
        }

        // --------------------------------------------------------------------------------------------------
        setSupportActionBar(binding.toolbar)
        // --------------------------------------------------------------------------------------------------

        //--------------Pruebas para el scroll-------------------------------
        binding.nst.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.appbar.setExpanded(false)

                return@setOnScrollChangeListener
            }
            if (scrollY < oldScrollY) {
                binding.appbar.setExpanded(true)

                return@setOnScrollChangeListener
            }
        }

        //--------------Pruebas para el scroll-------------------------------

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        navController = findNavController(R.id.fragment)

        //Especifica en que vistas se va a ver el menú lateral, en los que no esté
        //se creará automáticamente una flecha para volver a la vista anterior
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentInicio, R.id.nav_fragment_peliculas
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navigationView.setNavigationItemSelectedListener {

            binding.drawerLayout.closeDrawer(GravityCompat.START)

            when (it.itemId) {
                R.id.fragmentInicio         -> {
                    binding.navigationView.setCheckedItem(R.id.fragmentInicio)
                    viewModelItems.ocultaImagenToolbar.value = false
                    navController.navigate(R.id.fragmentInicio)
                    binding.appbar.setExpanded(true)
                    return@setNavigationItemSelectedListener true
                }

                R.id.nav_fragment_peliculas -> {
                    binding.navigationView.setCheckedItem(R.id.nav_fragment_peliculas)
                    viewModelItems.ocultaImagenToolbar.value = true
                    // Registra el tipo de item que se está consultando
                    viewModelItems.tipoItem.value = OConstantes.PELICULAS
                    binding.appbar.setExpanded(false)
                    fragmetLista()
                    return@setNavigationItemSelectedListener true
                }

                R.id.nav_fragment_series    -> {
                    binding.navigationView.setCheckedItem(R.id.nav_fragment_series)
                    viewModelItems.ocultaImagenToolbar.value = true
                    // Registra el tipo de item que se está consultando
                    viewModelItems.tipoItem.value = OConstantes.SERIES
                    binding.appbar.setExpanded(false)
                    fragmetLista()
                    return@setNavigationItemSelectedListener true
                }

                R.id.nav_fragment_ranking   -> {
                    binding.navigationView.setCheckedItem(R.id.nav_fragment_ranking)
                    viewModelItems.ocultaImagenToolbar.value = true
                    // Registra el tipo de item que se está consultando
                    viewModelItems.tipoItem.value = OConstantes.RANKING
                    binding.appbar.setExpanded(false)
                    fragmetLista()
                    return@setNavigationItemSelectedListener true
                }

                else                        -> {
                    true
                }
            }

        }
    }

    // Se llama a este método cada vez que el usuario elige navegar hacia arriba dentro de la jerarquía de actividad de su aplicación desde la barra de acción.
    override fun onSupportNavigateUp(): Boolean {
        viewModelItems.ocultaImagenToolbar.value = false
        navController.navigate(R.id.fragmentInicio)
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)

        val item = menu?.findItem(R.id.app_bar_search)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModelItems.tipoItem.value = OConstantes.BUSQUEDA
                if (query != null) {
                    // Oculta la imagen del toolbar
                    viewModelItems.ocultaImagenToolbar.value = true
                    // Abrimos el fragmento de la lista de peliculas
                    fragmetLista()
                    viewModelItems.palabraBuscada.value = query
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {

                true
            }
            else                -> return super.onOptionsItemSelected(item)
        }
    }

    private fun fragmetLista() {
        navController.navigate(R.id.nav_fragment_peliculas)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        viewModelItems.ocultaImagenToolbar.value = false
        navController.navigate(R.id.fragmentInicio)
        return true
    }
}