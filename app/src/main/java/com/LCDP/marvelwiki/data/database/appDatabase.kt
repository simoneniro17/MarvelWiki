package com.LCDP.marvelwiki.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteCharacter::class],version = 1, exportSchema = false)
abstract class appDatabase :RoomDatabase(){
    abstract fun favouriteCharacterDAO(): FavouriteCharacterDAO

    companion object{
        @Volatile
        private var INSTANCE: appDatabase? = null

        fun getDatabase(context: Context): appDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            } else {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                "app_Database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}