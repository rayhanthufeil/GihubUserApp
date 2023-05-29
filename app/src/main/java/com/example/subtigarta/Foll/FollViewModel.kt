package com.example.subtigarta.Foll

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subtigarta.API.RetrofitClient
import com.example.subtigarta.DataClass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollViewModel : ViewModel(){
    val followingUsers = MutableLiveData<List<User>>()
    val followersUser = MutableLiveData<List<User>>()

    fun setFollowerUser(username: String) {
        RetrofitClient.apiInstance
            .getFollowersUser(username)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful) {
                        followersUser.postValue(response.body())
                        Log.e("rusak", username)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    t.message?.let { Log.e("rusak", it) }
                }
            })
    }

    fun getFollowerUser(): LiveData<List<User>> {
        return followersUser

    }

    fun setFollowingUser(username: String) {
        RetrofitClient.apiInstance
            .getFollowingUser(username)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful) {
                        followingUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    t.message?.let { Log.e("rusak", it) }
                }
            })
    }

    fun getFollowingUser(): LiveData<List<User>> {
        return followingUsers

    }
}