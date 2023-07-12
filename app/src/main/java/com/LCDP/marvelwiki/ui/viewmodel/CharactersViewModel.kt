package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.usefulStuff.Constant
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

// Classe che estende la classe ViewModel
class CharactersViewModel(val charactersRepository: CharactersRepository):ViewModel(){

    // 'MutableLiveData' è una classe fornita da Compose che può essere osservata per il cambiamento dei dati
    val characters: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()

    var currentOffset: Int = 0

    // Blocco eseguito alla creazione dell'istanza della classe
    init {
        getCharacters()
    }

    // Metodo per ottenere i dati dei personaggi
    // viewModelScope.launch esegue il blocco in modo asincrono
    // Tramite postValue, si specifica che i dati sono in stato di loading
    // getChar_api() viene utilizzato per ottenere i dati dei personaggi
    fun getCharacters() = viewModelScope.launch {
        characters.postValue(Resource.Loading())
        val response = charactersRepository.getChar_api(offset = currentOffset)

        // Passiamo il risultato ad 'handleResponse', che gestirà la risposta
        characters.postValue(handleResponse(response))
    }

    // Funzione per ottenere i dati dei personaggi successivi oltre a quelli già caricati
    fun loadMoreCharacters() {
        // Viene incrementato l'offset di una quantità pari al numero di personaggi caricati ogni volta
        currentOffset += Constant.limit
        getCharacters()
    }

    // Funzione che si occupa di gestire le risposte ricevute dalle API
    private fun handleResponse(responses: Response<CharacterResponse>): Resource<CharacterResponse>{

        // Se la risposta è positiva, viene estratto il corpo della risposta e restituito in un oggetto Resource.Success
        if (responses.isSuccessful) {
            responses.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        // Se la risposta è negativa, si restituisce il messaggio di errore ricevuto nella risposta
        return Resource.Error(responses.message())
    }
}