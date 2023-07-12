package com.LCDP.marvelwiki.data.database.repository

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.data.database.dao.ReadComicDAO
import com.LCDP.marvelwiki.data.database.model.ReadComic


//Classe che fa da intermediario tra il ViewModel e il DAO
class ReadComicRepository(private val readComicDAO: ReadComicDAO){
    //Oggetto Live Data contiene una lista di tutti i fumetti letti, ottenuta tramite il DAO
    val readAllData: LiveData<List<ReadComic>> = readComicDAO.readAllData()

    //Funzione che permette di aggiungere un fumetto all'elenco dei fumetti letti
    suspend fun addFavouriteComic(favouriteComic: ReadComic){
        readComicDAO.addReadComic(favouriteComic)
    }
}