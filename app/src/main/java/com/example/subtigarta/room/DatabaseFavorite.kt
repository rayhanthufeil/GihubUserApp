package com.example.subtigarta.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Favorite::class],
    version = 1
)
abstract class DatabaseFavorite: RoomDatabase() {
    companion object{
        var INSTANCE:DatabaseFavorite?=null

        fun getDatabase(context: Context): DatabaseFavorite?{
            if (INSTANCE== null){
                synchronized(DatabaseFavorite::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseFavorite::class.java, "database_favorite")
                        .build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favoriteDao():FavoriteDao
}