package com.example.makeupstore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.makeupstore.models.CartProduct
import com.example.makeupstore.models.FavProduct

@Database(entities = [CartProduct::class,FavProduct::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "makeup_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}