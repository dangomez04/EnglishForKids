package com.example.englishforkids

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper (context: Context) : SQLiteOpenHelper(context, "EnglishForKids.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {


        db?.execSQL("CREATE TABLE levels (id INTEGER PRIMARY KEY, name TEXT , correct_answer TEXT, category TEXT, image TEXT)")

        db?.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, score INTEGER, id_level INTEGER, FOREIGN KEY(id_level) REFERENCES levels(id))")


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }



}