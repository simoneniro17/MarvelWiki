package com.LCDP.marvelwiki.data.repository

import com.LCDP.marvelwiki.data.API.ComicsAPICall.ComicInstance

//  Repository per la gestione delle chiamate API relative ai fumetti
class ComicsRepository {

    //  Chiama getComicByIdAPI() per ottenere i dettagli di un fumetto tramite ID
    suspend fun getComicsById_api(id: String) = ComicInstance.comicsById_api.getComicByIdAPI(id)

    //  Chiama getComicsByCharId() per ottenere i fumetti di un personaggio tramite ID
    suspend fun getComicsByCharId_api(id: String) =
        ComicInstance.latestComicByCharId_api.getLatestComicsByCharId(id)

    //  Chiama getComicsByIsbn() per ottenere un fumetto tramite ISBN
    suspend fun getComicsByIsbn_api(isbn: String) =
        ComicInstance.comicsByIsbn_api.getComicByIsbn(isbn)

    //  Chiama getLatestComic() per ottenere l'ultimo fumetto uscito
    suspend fun getLatestComic_api() = ComicInstance.latestComic_api.getLatestComic()

    //  Chiama getComicByName() per ottenere dei fumetti filtrati per titolo
    suspend fun getComicsByName_api(name: String, offset: Int) =
        ComicInstance.comicsByName_api.getComicByName(name, offset = offset)
}