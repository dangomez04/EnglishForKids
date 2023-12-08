package com.example.englishforkids

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class PickPlayer : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pick_player, container, false)
        val buttonConfirmar = view.findViewById<Button>(R.id.buttonConfirmar)
        buttonConfirmar.setOnClickListener {
            Toast.makeText(requireContext(),"Perfil cargado correctamente", Toast.LENGTH_SHORT).show()

        }
        return view
    }




}