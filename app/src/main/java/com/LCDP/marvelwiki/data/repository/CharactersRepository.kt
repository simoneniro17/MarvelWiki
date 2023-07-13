package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.CharacterInstance

class CharactersRepository {

    //  Questo metodo chiama dalla API il metodo getCharacters()
    suspend fun getChar_api(offset: Int) = CharacterInstance.char_api.getCharacters(offset = offset)
}