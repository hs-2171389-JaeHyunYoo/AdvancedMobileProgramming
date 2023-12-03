package com.example.project

data class item (
    val seller : String,
    val title : String,
    val explaination : String,
    val sellingItem: String,
    val price : Int,
    val status : Boolean
) {
    constructor() :this("","","","",0,false)
}

