package com.example.project

data class yjh_chat_item(
    val buyer : String,
    val msg : String,
    val title : String
) {
    constructor() : this("","", "")
}