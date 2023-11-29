package com.example.project

data class Chatitem (
    val senderId: String,
    val message: String
){
    constructor() :this("","")
}