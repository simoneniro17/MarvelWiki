package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.usefulStuff.Constant
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ComicsViewModel(private val comicsRepository: ComicsRepository, operationCode : Int, id: Int = 0 , isbn: String = "",name: String = ""): ViewModel() {
    // Questa classe estente la classe viewModel

    //La variabile comics è di tipo MutableLiveData, che è una classe fornita da Android Jetpack che può essere osservata per il cambiamento dei dati
    //Resource rappresenta lo stato dei dati (caricamento, successo, errore) e CharacterResponse è il tipo dei dati dei comics
    val comics: MutableLiveData<Resource<ComicResponse>> = MutableLiveData()

    private var currentOffset = 0

    private var nameToSearch = name
    private var idToSearch = id
    private var isbnToSearch: String = isbn

    init {
        when (operationCode) {
            1 -> getComicsByIsbn()
            2 -> getLatestComicsByCharId()
            3 -> getLatestComic()
            4 -> getComicByName()
            else -> {
                println("Wrong Operation Code")
            }
        }
    }
    //Questo metodo serve per ottenere i dati dei comics.
    //viewModelScope.launch esegue il blocco in modo asincrono. Tramite postValue, si specifca che i dati sono in stato di loading
    //Il metodo getComic_api viene utilizzato per ottenere i dati dei comics
    private fun getLatestComicsByCharId() = viewModelScope.launch {
        comics.postValue(Resource.Loading())
        val response = comicsRepository.getComicsByCharId(idToSearch)

        //Il risultato è passato a  handleResponse che gestisce la risposta
        comics.postValue(handleResponse(response))
    }

    private fun getComicsByIsbn() = viewModelScope.launch {
        comics.postValue(Resource.Loading())
        val response = comicsRepository.getComicsByIsbn(isbnToSearch)

        //Il risultato è passato a  handleResponse che gestisce la risposta
        comics.postValue(handleResponse(response))
    }

    private fun getLatestComic() = viewModelScope.launch {
        comics.postValue(Resource.Loading())
        val response = comicsRepository.getLatestComic()

        //Il risultato è passato a  handleResponse che gestisce la risposta
        comics.postValue(handleResponse(response))
    }


    private fun getComicByName() = viewModelScope.launch {
        comics.postValue(Resource.Loading())
        val response = comicsRepository.getComicsByName(nameToSearch,currentOffset)

        //Il risultato è passato a  handleResponse che gestisce la risposta
        comics.postValue(handleResponse(response))
    }

    fun loadMoreComics() {
        currentOffset += Constant.limit
        getComicByName()
    }


    //Prende in input un oggetto Response<ComicResponse> e restituisce un oggetto Resource<ComicResponse>
    private fun handleResponse(response: Response<ComicResponse>): Resource<ComicResponse> {

        //Se la risposta è positiva viene estratto il corpo della risposta e viene restituito un oggetto Resource.Success contenente il corpo
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        //Se la risposta è negativa si restituisce il messaggio di errore della risposta
        return Resource.Error(response.message())
    }

}


