package com.example.englishforkids

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class LevelFragment(var usuarioSeleccionado : String) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_level, container, false)
        var buttonSave = view.findViewById<Button>(R.id.buttonSave)
        var buttonNext = view.findViewById<Button>(R.id.buttonNext)
        var buttonCheck = view.findViewById<Button>(R.id.buttonCheck)
        var textNombreUsuario = view.findViewById<TextView>(R.id.textNombreUsuario)
        var textScore = view.findViewById<TextView>(R.id.textScore)
        var textLevel = view.findViewById<TextView>(R.id.textLevel)
        var textInfo = view.findViewById<TextView>(R.id.textInfo)
        var textCategory = view.findViewById<TextView>(R.id.textCategory)
        var image = view.findViewById<ImageView>(R.id.imageLevel)
        var editAnswer = view.findViewById<TextView>(R.id.editAnswer)

        var nivelUsuario : Int = 0
        var scoreUsuario : Int = 2

        var correctCond : Boolean = false
        var level_id : Int = 0
        var level_name = ""
        var level_category = ""
        var level_image = ""

        val helper = SqlHelper(requireContext())
        val db = helper.readableDatabase

        // Consulta para obtener todos los datos del usuario seleccionado
        val query = "SELECT * FROM users WHERE name = '$usuarioSeleccionado'"
        val cursor = db.rawQuery(query, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                 nivelUsuario = Integer.parseInt(cursor.getString(3))
                 scoreUsuario = Integer.parseInt(cursor.getString(2))

            }
            cursor.close()

        }


        // Consulta para obtener datos del nivel del usuario en la tabla levels
        val queryLevels = "SELECT * FROM levels WHERE id = $nivelUsuario"
        val cursorLevels = db.rawQuery(queryLevels, null)

        if (cursorLevels != null) {
            while (cursorLevels.moveToNext()) {
                    level_id = Integer.parseInt(cursorLevels.getString(0))
                    level_name = cursorLevels.getString(1)
                    level_category = cursorLevels.getString(2)
                    level_image = cursorLevels.getString(3)
            }
            cursorLevels.close()

        }

        textNombreUsuario.text = usuarioSeleccionado
        textLevel.text = "Level $level_id:"
        textCategory.text= "$level_category"
        textScore.text = "$scoreUsuario"
        val resourceId = resources.getIdentifier(level_image, "drawable", requireContext().packageName)

        Glide.with(this)
            .load(resourceId)
            .into(image)
        if(level_category == "Animals" || level_category == "Food"){
            textInfo.text = "This is a/an..."
        } else{
            textInfo.text = "This is the color..."

        }

       buttonCheck.setOnClickListener {
           if (editAnswer.text.toString().toUpperCase() == level_name.toUpperCase()) {
               correctCond = true
               Toast.makeText(requireContext(), "Congratulations!", Toast.LENGTH_SHORT).show()


               buttonNext.setBackgroundColor(Color.parseColor("#FFD700"))
           }
       }

           buttonNext.setOnClickListener {
               if(correctCond){
                   val queryUpdateScore = "UPDATE users SET score = score + 1 WHERE name = '$usuarioSeleccionado'"
                   db.execSQL(queryUpdateScore)
                   val queryUpdateLevel = "UPDATE users SET id_level = id_level + 1 WHERE name = '$usuarioSeleccionado'"
                   db.execSQL(queryUpdateLevel)
                   parentFragmentManager?.beginTransaction()
                       ?.replace(R.id.fragmentContainerView, LevelFragment(usuarioSeleccionado))
                       ?.commit()
               } else{
                   Toast.makeText(requireActivity(),"To access the next level you must unlock this!", Toast.LENGTH_SHORT).show()

               }
           }

           buttonSave.setOnClickListener {
               Toast.makeText(requireContext(),"Saved correctly ", Toast.LENGTH_SHORT).show()

               parentFragmentManager?.beginTransaction()
                   ?.replace(R.id.fragmentContainerView, InitialFragment())
                   ?.commit()
           }


        return view

    }

}