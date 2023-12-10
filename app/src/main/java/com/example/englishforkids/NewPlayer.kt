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

        val view = inflater.inflate(R.layout.fragment_new_player, container, false)
        val buttonAceptar = view.findViewById<Button>(R.id.buttonAdd)
        var editNombreJugador = view.findViewById<EditText>(R.id.editNombreJugador)
        var nombresUsuarios = mutableListOf<String>()

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


                val queryInsertarUsuario = "INSERT INTO users (name, score, id_level) VALUES ('${nuevoUsuario.name}', ${nuevoUsuario.score}, ${nuevoUsuario.id_level})"
                db.execSQL(queryInsertarUsuario)




                Toast.makeText(requireContext(),"Profile created successfully",Toast.LENGTH_SHORT).show()
                parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, InitialFragment())
                    ?.commit()
                supportActionBar?.title ="EnglishForKids"

                db.close()
            }

        }
        db.close()
        return view
    }


    }
