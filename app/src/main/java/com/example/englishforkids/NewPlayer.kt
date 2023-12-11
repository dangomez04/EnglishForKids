package com.example.englishforkids

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar


class NewPlayer(var supportActionBar : ActionBar?) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflo el layout de mi fragmento y recojo las variables del xml del fragmento
        val view = inflater.inflate(R.layout.fragment_new_player, container, false)
        val buttonAceptar = view.findViewById<Button>(R.id.buttonAdd)
        var editNombreJugador = view.findViewById<EditText>(R.id.editNombreJugador)
        var nombresUsuarios = mutableListOf<String>()

        // hago una query que me servira como validacion para comprobar si el nombre de usuario intorducido ya existe
        val helper = SqlHelper(requireContext())
        val db = helper.readableDatabase

        val cursor = db.rawQuery("SELECT name FROM users", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val nombreUsuario = cursor.getString(0)
                nombresUsuarios.add(nombreUsuario)
            }
            cursor.close()
        }



        buttonAceptar.setOnClickListener {
            val helper = SqlHelper(requireContext())
            val db = helper.writableDatabase
            if(editNombreJugador.text.toString() == ""){
                Toast.makeText(requireContext(),"This field is required",Toast.LENGTH_SHORT).show()

            }else if(nombresUsuarios.contains(editNombreJugador.text.toString())){

                Toast.makeText(requireContext(),"This name alredy exists",Toast.LENGTH_SHORT).show()


            }else{
                var nuevoUsuario = Users(editNombreJugador.text.toString(),0,1)

                // despues de las validaciones correspondientes hago la insercion en mi bbdd
                val queryInsertarUsuario = "INSERT INTO users (name, score, id_level) VALUES ('${nuevoUsuario.name}', ${nuevoUsuario.score}, ${nuevoUsuario.id_level})"
                db.execSQL(queryInsertarUsuario)




                Toast.makeText(requireContext(),"Profile created successfully",Toast.LENGTH_SHORT).show()
                // redirigo al fragmento inicial una vez se ha creado correctamente el usuario
                parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, InitialFragment())
                    ?.commit()
                // cambio el titulo de la toolbar para que al volver al fragmento inicial el titulo sea el nombre de la aplicacion
                supportActionBar?.title ="EnglishForKids"

                db.close()
            }

        }
        db.close()
        return view
    }


    }
