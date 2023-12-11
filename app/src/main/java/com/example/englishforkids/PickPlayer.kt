package com.example.englishforkids

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class PickPlayer(var supportActionBar : ActionBar?) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pick_player, container, false)
        val buttonConfirmar = view.findViewById<Button>(R.id.buttonConfirmar)
        // recojo el spinner que mostrara los usuarios disponibles

        val spinner = view.findViewById<Spinner>(R.id.spinnerPlayers)

        // Lista para almacenar los nombres de usuarios obtenidos de la bbdd
        val nombresUsuarios = mutableListOf<String>()

        // Query para obtener los nombres de los usuarios
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


        // Creo un ArrayAdapter que me servira para añadir a la lista desplegable del spinner los usuarios
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresUsuarios)

        // Diseño de la lista desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establezco el adaptador en el Spinner
        spinner.adapter = adapter


        buttonConfirmar.setOnClickListener {
            // valido que el spinner tenga al menos un usuario
            if( spinner.adapter.count > 0){
                val usuarioSeleccionado = spinner.selectedItem.toString()

                Toast.makeText(requireContext(),"Profile $usuarioSeleccionado loaded succesfully", Toast.LENGTH_SHORT).show()
                // redirigo al fragmento de los niveles pasandole el usuario seleccionado en el spinner para poder mostrarle los niveles a ese
                // usuario y tambien le paso la supportActionBar para cambiar el titulo de la toolbar segun la categoria en la que este el usuario
                parentFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, LevelFragment(usuarioSeleccionado, supportActionBar))
                    ?.commit()
            } else{
                Toast.makeText(requireContext(),"No users available", Toast.LENGTH_SHORT).show()
            }

        }


        db.close()

        return view
    }




}