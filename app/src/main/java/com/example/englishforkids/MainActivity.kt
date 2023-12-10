package com.example.englishforkids

import android.app.Dialog
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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.internal.ViewUtils.dpToPx


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        window.statusBarColor=Color.BLACK


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
                val fragment = PickPlayer(supportActionBar)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .addToBackStack(null)
                    .commit()
                supportActionBar?.title="Seleccionar Jugador"
                true
            }

            R.id.itemRanking -> {
                var listaUsuarios = mutableListOf<Users>()

                val dialog = Dialog(this)
                dialog.setContentView(R.layout.ranking_dialog)
                val helper = SqlHelper(this)
                val db = helper.readableDatabase

                val cursor = db.rawQuery("SELECT name,score,id_level FROM users ORDER BY score DESC", null)
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val nombreUsuario = cursor.getString(0)
                        val score = Integer.parseInt(cursor.getString(1))
                        val id_level = Integer.parseInt(cursor.getString(2))
                        var objetoUsuario =Users(nombreUsuario,score,id_level)
                        listaUsuarios.add(objetoUsuario)
                    }
                    cursor.close()
                }


                val tableLayout = dialog.findViewById<TableLayout>(R.id.tableLayout)
                var position = 1

                for (usuario in listaUsuarios) {
                    val row = TableRow(this)
                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    row.layoutParams = params
                    val scale = resources.displayMetrics.density
                    val dpValue = 8
                    val paddingPixel = (dpValue * scale + 0.5f).toInt()

                    val positionTextView = TextView(this)
                    positionTextView.text = position.toString()
                    positionTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    positionTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    row.addView(positionTextView)

                    val userNameTextView = TextView(this)
                    userNameTextView.text = usuario.name
                    userNameTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    userNameTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    row.addView(userNameTextView)

                    val scoreTextView = TextView(this)
                    scoreTextView.text = "\uD83C\uDF1F " + usuario.score.toString()
                    scoreTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    scoreTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    row.addView(scoreTextView)

                    tableLayout.addView(row)
                    position++
                }

                dialog.show()

               true
            }

            R.id.itemAbout -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.about_dialog)
                dialog.setTitle("Acerca De")
                dialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}