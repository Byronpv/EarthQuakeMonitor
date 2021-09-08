package com.example.earthquakemonitor.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//agregamos la entidad, cuando agregamos una nueva entidad o tabla debemos aumentar la versión
//para que se reflejen los cambios en la db
@Database(entities = [Earthquake::class], version = 1)
abstract class EqDataBase : RoomDatabase() {

    abstract val eqDao: EqDao
}

/*//Singletón: Es una variable que solo se va a instanciar una vez en toda la app, solo se podrá crear una vez
//y esa instancia la vamos a utilizar una vez en toda la app, con el fin de impedir que se creen más bases de
//datos en la aplicación, ya que puede dar problemas si intentamos editarla en multiples partes a la vez*/

private lateinit var INSTANCE: EqDataBase

fun getDatabase(context: Context): EqDataBase {
    synchronized(EqDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EqDataBase::class.java,
                "earthquake_db"
            ).build()
        }
    }
    return INSTANCE
}