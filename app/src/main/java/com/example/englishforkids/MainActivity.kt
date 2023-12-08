package com.example.englishforkids

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        window.statusBarColor=Color.BLACK
        var helper = SqlHelper(this)
        var db = helper.writableDatabase





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)





        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val transaction =
            supportFragmentManager.beginTransaction()

        return when (item.itemId) {
            R.id.itemNewPlayer -> {
                val fragment = NewPlayer(supportActionBar)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit()
                supportActionBar?.title="Crear Nuevo Jugador"

                true
            }

            R.id.itemPickPlayer -> {
                val fragment = PickPlayer()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit()
                supportActionBar?.title="Seleccionar Jugador"
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }
}