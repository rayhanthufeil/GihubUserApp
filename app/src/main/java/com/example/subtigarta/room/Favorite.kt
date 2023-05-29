package com.example.subtigarta.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class Favorite(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url:String,
): Serializable
