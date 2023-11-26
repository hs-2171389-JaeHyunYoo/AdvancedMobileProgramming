package com.example.project

data class ChatItem (
    val time: String,
    val senderId: String,
    val message: String
){
    constructor(senderId: String?, message: String) :this("","","")
}