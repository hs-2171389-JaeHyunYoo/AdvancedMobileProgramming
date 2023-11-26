package com.example.project

class Chatlistitem (
    val buyerId: String,
    val sellerId :String,
    val itemTitle: String,
    val key: Long
    ) {

        constructor(): this("", "", "", 0)
    }