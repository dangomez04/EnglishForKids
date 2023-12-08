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

        buttonAceptar.setOnClickListener {
            if(editNombreJugador.text.toString() == ""){
                Toast.makeText(requireContext(),"El campo nombre es obligatorio",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(requireContext(),"Perfil creado correctamente",Toast.LENGTH_SHORT).show()
                parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, InitialFragment())
                    ?.commit()
                supportActionBar?.title ="EnglishForKids"

            }

        }
        return view
    }


    }
