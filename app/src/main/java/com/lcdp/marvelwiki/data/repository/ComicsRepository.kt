package com.lcdp.marvelwiki.data.repository

import com.lcdp.marvelwiki.data.API.ComicsAPICall.ComicInstance

//  Repository per la gestione delle chiamate API relative ai fumetti
class ComicsRepository {

    //  Chiama getComicByIdAPI() per ottenere i dettagli di un fumetto tramite ID
    suspend fun getComicsByIdAPI(id: String) = ComicInstance.comicsById_api.getComicByIdAPI(id)

    //  Chiama getComicsByIsbn() per ottenere un fumetto tramite ISBN
    suspend fun getComicsByIsbnAPI(isbn: String) =
        ComicInstance.comicsByIsbn_api.getComicByIsbn(isbn)

    //  Chiama getLatestComic() per ottenere l'ultimo fumetto uscito
    suspend fun getLatestComicAPI() = ComicInstance.latestComic_api.getLatestComic()

    //  Chiama getComicByName() per ottenere dei fumetti filtrati per titolo
    suspend fun getComicsByNameAPI(name: String, offset: Int) =
        ComicInstance.comicsByName_api.getComicByName(name, offset = offset)
}