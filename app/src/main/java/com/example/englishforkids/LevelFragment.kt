package com.example.englishforkids

import android.app.Dialog
import android.content.Context
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
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide


class LevelFragment(var usuarioSeleccionado : String, var supportActionBar : ActionBar?) : Fragment() {


   // override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)

  //  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Le inflo el xml del fragmento y recojo todas las variables de ese xml
        val view = inflater.inflate(R.layout.fragment_level, container, false)
        var buttonSave = view.findViewById<Button>(R.id.buttonSave)
        var buttonNext = view.findViewById<Button>(R.id.buttonNext)
        var buttonCheck = view.findViewById<Button>(R.id.buttonCheck)
        var textNombreUsuario = view.findViewById<TextView>(R.id.textUserName)
        var textScore = view.findViewById<TextView>(R.id.textScore)
        var textLevel = view.findViewById<TextView>(R.id.textLevel)
        var textInfo = view.findViewById<TextView>(R.id.textInfo)
        var textCategory = view.findViewById<TextView>(R.id.textCategory)
        var image = view.findViewById<ImageView>(R.id.imageLevel)
        var editAnswer = view.findViewById<TextView>(R.id.editAnswer)

        //inicializo variables que me serviran mas adelante
        var nivelUsuario : Int = 0
        var scoreUsuario : Int = 2

        var correctCond : Boolean = false
        var condLastLevel : Boolean = false
        var level_id : Int = 0
        var level_name = ""
        var level_category = ""
        var level_image = ""


        //hago una query para obtener todos los datos del usuario que ha sido seleccionado en el spinner
        val helper = SqlHelper(requireContext())
        val db = helper.readableDatabase

        // Consulta para obtener todos los datos del usuario seleccionado
        val query = "SELECT * FROM users WHERE name = '$usuarioSeleccionado'"
        val cursor = db.rawQuery(query, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // recojo datos que voy a necesitar
                 nivelUsuario = Integer.parseInt(cursor.getString(3))
                 scoreUsuario = Integer.parseInt(cursor.getString(2))

            }
            cursor.close()

        }

        if(nivelUsuario == 18){
            //condicion que me sirve para cuando el usuario llegue al ultimo nivel hacer x cosa
            condLastLevel = true
        }

        // Consulta para obtener datos del nivel del usuario
        val queryLevels = "SELECT * FROM levels WHERE id = $nivelUsuario"
        val cursorLevels = db.rawQuery(queryLevels, null)

        if (cursorLevels != null) {
            while (cursorLevels.moveToNext()) {
                    // recojo los datos del nivel
                    level_id = Integer.parseInt(cursorLevels.getString(0))
                    level_name = cursorLevels.getString(1)
                    level_category = cursorLevels.getString(2)
                    level_image = cursorLevels.getString(3)
            }
            cursorLevels.close()

        }
        // asigno a las variables sus correspondientes valores
        textNombreUsuario.text = usuarioSeleccionado
        textLevel.text = "Level $level_id:"
        textCategory.text= "$level_category"
        textScore.text = "$scoreUsuario"

        // con esta linea obtengo el recurso de esa imagen que quiero asignar
        val resourceId = resources.getIdentifier(level_image, "drawable", requireContext().packageName)

        // utilizo la libreria Glide para poder asignarle la imagen correspondiente a mi imageview del fragmento
        Glide.with(this)
            .load(resourceId)
            .into(image)

        //condiciones para cambiar el titulo del fragmento segun la categoria en la que este el usuario
        if(level_category == "Animals"){
            textInfo.text = "This is a/an..."
            supportActionBar?.title="Animals"

        }else if(level_category == "Food"){
            textInfo.text = "This is a/an..."
            supportActionBar?.title="Food"

        } else{
            textInfo.text = "This is the color..."
            supportActionBar?.title="Colors"


        }

        // validaciones del boton check
       buttonCheck.setOnClickListener {
           if (editAnswer.text.toString().toUpperCase() == level_name.toUpperCase()) {
               // condicion que me servira para determinar si puede pasar el usuario al siguiente nivel
               correctCond = true
               Toast.makeText(requireContext(), "Congratulations!", Toast.LENGTH_SHORT).show()


               buttonNext.setBackgroundColor(Color.parseColor("#FFD700"))
           } else if(editAnswer.text.toString() == ""){
               Toast.makeText(requireContext(),"This field is required",Toast.LENGTH_SHORT).show()

           }else {
               Toast.makeText(requireContext(), "Every mistake is a step closer to mastery. Keep going, you're making progress!", Toast.LENGTH_LONG).show()

           }
       }
            // validaciones boton next
           buttonNext.setOnClickListener {

               val helper = SqlHelper(requireContext())
               val db = helper.writableDatabase

               if(correctCond){
                   if(condLastLevel){
                       // si cumple las condiciones ejecuta las querys y aparece un dialogo de felicitacion
                       val queryLastLevel = "UPDATE users SET id_level =  1 WHERE name = '$usuarioSeleccionado'"
                       db.execSQL(queryLastLevel)
                       val queryUpdateScore = "UPDATE users SET score = score + 1 WHERE name = '$usuarioSeleccionado'"
                       db.execSQL(queryUpdateScore)

                       val dialog = Dialog(requireContext())
                       dialog.setContentView(R.layout.congratulations_dialog)



                       val acceptDialogButton = dialog.findViewById<Button>(R.id.acceptDialogButton)
                       acceptDialogButton.setOnClickListener {
                           dialog.dismiss()

                       }


                       dialog.show()


                   }else{
                       // si cumple la condicion del check ejecuta las querys
                       val queryUpdateScore = "UPDATE users SET score = score + 1 WHERE name = '$usuarioSeleccionado'"
                       db.execSQL(queryUpdateScore)
                       val queryUpdateLevel = "UPDATE users SET id_level = id_level + 1 WHERE name = '$usuarioSeleccionado'"
                       db.execSQL(queryUpdateLevel)
                   }

                    // una vez ejecutadas las querys se llama al mismo fragmento para acutualizar el nivel y sus datos correspondientes
                   parentFragmentManager?.beginTransaction()
                       ?.replace(R.id.fragmentContainerView, LevelFragment(usuarioSeleccionado,supportActionBar))
                       ?.commit()
                   db.close()

               } else{
                   Toast.makeText(requireActivity(),"To access the next level you must unlock this!", Toast.LENGTH_SHORT).show()

               }
           }

            // save & exit
           buttonSave.setOnClickListener {
               Toast.makeText(requireContext(),"Saved correctly ", Toast.LENGTH_SHORT).show()
                // te redirige al fragmento inicial y cambia el titulo de la toolbar
               parentFragmentManager?.beginTransaction()
                   ?.replace(R.id.fragmentContainerView, InitialFragment())
                   ?.commit()
               supportActionBar?.title="EnglishForKids"
           }


        db.close()
        return view

    }

}

