package com.example.subtigarta.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorite Where favorite.id=:id")
    fun cekFavorite(id:Int): Int

    @Query("DELETE FROM favorite Where favorite.id=:id")
    fun deleteFavorite(id: Int):Int
}