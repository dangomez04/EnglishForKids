package com.example.englishforkids

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper (context: Context) : SQLiteOpenHelper(context, "EnglishForKids.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {


        db?.execSQL("CREATE TABLE levels (id INTEGER PRIMARY KEY, name TEXT , category TEXT, image TEXT)")

        db?.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, score INTEGER, id_level INTEGER, FOREIGN KEY(id_level) REFERENCES levels(id))")


        //Creo mis niveles para insertarlos en la bbdd
        val listaNiveles = listOf(
            //Animals
            Levels( "Lion", "Animals", "lion_image"),
            Levels( "Elephant", "Animals", "elephant_image"),
            Levels( "Giraffe", "Animals", "giraffe_image"),
            Levels( "Monkey", "Animals", "monkey_image"),
            Levels( "Tiger", "Animals", "tiger_image"),
            Levels( "Panda", "Animals", "panda_image"),
            //Food
            Levels( "Apple", "Food", "apple_image"),
            Levels( "Pizza", "Food", "pizza_image"),
            Levels( "Banana", "Food", "banana_image"),
            Levels( "Ice Cream", "Food", "icecream_image"),
            Levels( "Hamburger", "Food", "hamburger_image"),
            Levels( "Cake", "Food", "cake_image"),
            //Colors
            Levels( "Blue", "Colors", "blue_image"),
            Levels( "Red", "Colors", "red_image"),
            Levels( "Green", "Colors", "green_image"),
            Levels( "Yellow", "Colors", "yellow_image"),
            Levels( "Purple", "Colors", "purple_image"),
            Levels( "Orange", "Colors", "orange_image")
        )
        // Insertar Niveles
        for (level in listaNiveles){
            val queryInsertarNiveles = "INSERT INTO levels (name, category, image) " +
                    "VALUES ('${level.name}', '${level.category}', '${level.image}')"
            db?.execSQL(queryInsertarNiveles)
        }

        db?.close()


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }



}