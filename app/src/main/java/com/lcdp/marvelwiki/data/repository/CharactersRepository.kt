package com.lcdp.marvelwiki.data.repository

import com.lcdp.marvelwiki.data.API.CharactersAPICall.CharacterInstance

//  Repository per la gestione delle chiamate API relative ai personaggi
class CharactersRepository {

    //  Chiama getCharacters() dall'API per ottenere l'elenco dei personaggi
    suspend fun getCharAPI(offset: Int) = CharacterInstance.char_api.getCharacters(offset = offset)

    //  Chiama getCharacterByName() dall'API per ottenere dei personaggi filtrati per nome
    suspend fun getCharByNameAPI(offset: Int, name: String) =
        CharacterInstance.charByName_api.getCharacterByNameAPI(
            nameStartsWith = name,
            offset = offset
        )

    //  Chiama getCharacterByIdAPI() dall'API per ottenere i dettagli di un personaggio tramite ID
    suspend fun getCharByIdAPI(id: String) =
        CharacterInstance.charById_api.getCharacterByIdAPI(id = id)
}