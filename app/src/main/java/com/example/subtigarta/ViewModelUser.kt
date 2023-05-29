package com.example.subtigarta

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subtigarta.API.RetrofitClient
import com.example.subtigarta.DataClass.ResponseUser
import com.example.subtigarta.DataClass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel() {
    val listUser = MutableLiveData<List<User>>()

    fun setCariUser(query: String) {
        RetrofitClient.apiInstance
            .getUsers(query)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    t.message?.let { Log.e("gagal", it) }
                }

            })
    }

    fun getCariUser(): LiveData<List<User>> {
        return listUser
    }
}