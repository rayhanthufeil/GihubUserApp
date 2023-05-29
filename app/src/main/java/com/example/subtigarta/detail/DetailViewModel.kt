package com.example.subtigarta.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.subtigarta.API.RetrofitClient
import com.example.subtigarta.DataClass.ResponseDetail
import com.example.subtigarta.room.DatabaseFavorite
import com.example.subtigarta.room.Favorite
import com.example.subtigarta.room.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<ResponseDetail>()

    private var favDao: FavoriteDao?
    private var favDb: DatabaseFavorite?

    init {
        favDb = DatabaseFavorite.getDatabase(application)
        favDao = favDb?.favoriteDao()
    }



    fun setDetailUser(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<ResponseDetail> {
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    t.message?.let { Log.e("rusak", it) }
                }
            })
    }

    fun getUserDetail(): LiveData<ResponseDetail> {
        return detailUser

    }

    fun addfavorite(username: String, id: Int, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = Favorite(
                username,
                id,
                avatar_url
            )
            favDao?.addFavorite(user)
        }
    }

    suspend fun cekfavorite(id: Int) = favDao?.cekFavorite(id)

    fun deletefavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFavorite(id)
        }
    }

}