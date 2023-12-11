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
        // le inflo al menu su correspondiente xml
        menuInflater.inflate(R.menu.menu, menu)





        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // items del menu, cuando se pulse en x item te llevara al fragmento correspondiente
        // Ademas, cambio el titulo de la toolbar segun corresponda

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
                // lista de objetos de users que determinara el numero de filas que voy a insertar en la tabla
                var listaUsuarios = mutableListOf<Users>()
                // creo dialogo  y le asigno su xml
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.ranking_dialog)
                // hago query para obtener los usuarios ordenados descendentemente por puntuacion
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
                db.close()

                // recojo el tableLayout de mi xml del dialogo (ranking_dialog)
                val tableLayout = dialog.findViewById<TableLayout>(R.id.tableLayout)
                // variable que me sirve para darle una poisicion a cada fila
                var position = 1

                // recorro mi lista de usuarios para ir creando dinamicamente las filas de la tabla
                for (usuario in listaUsuarios) {
                    //creo una fila
                    val row = TableRow(this)
                    // esto lo uso para transformar de dp a pixel ya que en mi xml del ranking puse por defecto una fila
                    // con estilos, entonces para conseguir el mismo padding necesito esta conversion
                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    row.layoutParams = params
                    val scale = resources.displayMetrics.density
                    val dpValue = 8
                    val paddingPixel = (dpValue * scale + 0.5f).toInt()
                    //
                    //creo un textview que contendra la posicion y le aplico estilos
                    val positionTextView = TextView(this)
                    positionTextView.text = position.toString()
                    positionTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    positionTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    //le agrego el textview a la fila
                    row.addView(positionTextView)

                    //creo un textview que contendra el nombre del usuario y le aplico estilos
                    val userNameTextView = TextView(this)
                    userNameTextView.text = usuario.name
                    userNameTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    userNameTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    //le agrego el textview a la fila
                    row.addView(userNameTextView)

                    //creo un textview que contendra la puntuacion del usuario y le aplico estilos
                    val scoreTextView = TextView(this)
                    scoreTextView.text = "\uD83C\uDF1F " + usuario.score.toString()
                    scoreTextView.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel)
                    scoreTextView.setTextColor(Color.parseColor("#FF1E88E5"))
                    //le agrego el textview a la fila
                    row.addView(scoreTextView)
                    //le agrego al tableLayout la fila que he creado
                    tableLayout.addView(row)
                    // incremento en 1 la posicion para la siguiente fila
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