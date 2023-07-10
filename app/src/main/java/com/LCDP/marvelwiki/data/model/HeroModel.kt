package com.LCDP.marvelwiki.data.model

data class HeroModel(

    //THIS MODEL IS NOT DEFINITIVE : IT WILL RELY ON API
    val id : Int,
    val name : String,
    val heroPic : Int,
    val realName : String,
    val creator : String,
    val creationDate : String,
    val firstAppearance : String,
    val info : String,
    val origins : String

)