package com.example.subtigarta.API

import com.example.subtigarta.DataClass.ResponseDetail
import com.example.subtigarta.DataClass.ResponseUser
import com.example.subtigarta.DataClass.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") query: String
    ): Call<ResponseUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUser(
        @Path("username")username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUser(
        @Path("username")username: String
    ): Call<List<User>>
}