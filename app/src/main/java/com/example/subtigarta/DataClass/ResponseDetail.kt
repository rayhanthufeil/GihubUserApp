package com.example.subtigarta.DataClass

data class ResponseDetail (
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val following: Int,
    val followers: Int,
    val company: String,
    val location: String,
    val public_repos: Int,
)