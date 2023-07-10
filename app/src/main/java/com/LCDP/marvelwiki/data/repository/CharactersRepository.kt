package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.CharacterInstance

class CharactersRepository {
    //Questo metodo chiama dalla api il metodo get character
    suspend fun getChar_api() = CharacterInstance.char_api.getCharacters()

}