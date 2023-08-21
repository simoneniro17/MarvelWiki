package com.LCDP.marvelwiki.database.repository

import com.LCDP.marvelwiki.database.dao.ReadComicDAO
import com.LCDP.marvelwiki.database.model.ReadComic


// Classe che fa da intermediario tra il rispettivo ViewModel e il DAO
class ReadComicRepository(private val readComicDAO: ReadComicDAO){

    // Oggetto che contiene una lista degli ID di tutti i fumetti letti, ottenuta tramite il DAO
    val allReadComicId: List<String> = readComicDAO.getAllReadComicId()

    // Funzione che permette di aggiungere (l'ID di) un fumetto a quelli letti
    suspend fun addReadComic(readComic: ReadComic){
        readComicDAO.insertReadComic(readComic)
    }

    // Funzione che permette di rimuovere (l'ID di) un fumetto dall'elenco dei fumetti preferiti
    suspend fun deleteReadComic(readComic: ReadComic){
        readComicDAO.deleteReadComic(readComic)
    }
}