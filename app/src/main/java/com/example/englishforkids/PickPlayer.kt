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
        val spinner = view.findViewById<Spinner>(R.id.spinnerPlayers)

        // Lista para almacenar los nombres de usuarios obtenidos de la base de datos
        val nombresUsuarios = mutableListOf<String>()

        // Obtener nombres de usuarios desde la base de datos
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


        // Crear un ArrayAdapter utilizando el contexto y el diseño predeterminado para los elementos del Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresUsuarios)

        // Especificar el diseño para el dropdown de la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador en el Spinner
        spinner.adapter = adapter


        buttonConfirmar.setOnClickListener {
            val usuarioSeleccionado = spinner.selectedItem.toString()

            Toast.makeText(requireContext(),"Perfil $usuarioSeleccionado cargado correctamente", Toast.LENGTH_SHORT).show()
            parentFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, LevelFragment(usuarioSeleccionado, supportActionBar))
                ?.commit()
        }


        db.close()

        return view
    }




}