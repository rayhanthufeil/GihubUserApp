package com.example.subtigarta.fav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.subtigarta.room.DatabaseFavorite
import com.example.subtigarta.room.Favorite
import com.example.subtigarta.room.FavoriteDao

class ViewModelFav (application: Application) : AndroidViewModel(application) {

    private var favDao: FavoriteDao?
    private var favDb: DatabaseFavorite?

    init {
        favDb = DatabaseFavorite.getDatabase(application)
        favDao = favDb?.favoriteDao()
    }

    fun getFavorite(): LiveData<List<Favorite>>? {
        return favDao?.getFavorite()
    }
}


