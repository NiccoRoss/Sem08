package com.example.copycrunchyroll

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.example.copycrunchyroll.SliderAdapter

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imageSlider: ViewPager2
    private val sliderHandler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//  DrawerLayout y Toolbar
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        // carrusel
        imageSlider = findViewById(R.id.imageSlider)
        val imageList = listOf(
            R.drawable.slide_1,
            R.drawable.slide_2,
            R.drawable.slide_3,
            R.drawable.slide_4
        )
        val sliderAdapter = SliderAdapter(imageList)
        imageSlider.adapter = sliderAdapter

        sliderRunnable = Runnable {
            val nextItem = (imageSlider.currentItem + 1) % imageList.size
            imageSlider.currentItem = nextItem
            sliderHandler.postDelayed(sliderRunnable, 3000)
        }
        sliderHandler.postDelayed(sliderRunnable, 3000)

        imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })

        // Botón "Términos y condiciones"
        val btnTerms: MaterialButton = findViewById(R.id.btnTerms)
        btnTerms.setOnClickListener {
            val intent = Intent(this, webview::class.java)
            intent.putExtra("url", "https://www.crunchyroll.com/es-es/tos/")
            intent.putExtra("title", "Términos y condiciones")
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val (title, url) = when (item.itemId) {
            R.id.nav_crear_cuenta -> "Crear cuenta" to "https://www.crunchyroll.com/register"
            R.id.nav_acceder -> "Acceder" to "https://www.crunchyroll.com/login"
            R.id.nav_novedades -> "Novedades" to "https://www.crunchyroll.com/news"
            else -> null to null
        }

        if (url != null && title != null) {
            val intent = Intent(this, webview::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", title)
            startActivity(intent)
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}