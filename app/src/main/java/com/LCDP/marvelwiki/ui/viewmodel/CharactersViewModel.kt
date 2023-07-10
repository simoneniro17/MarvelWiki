package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CharactersViewModel(val charactersRepository: CharactersRepository):ViewModel(){
    // Questa classe estente la classe viewModel

    //La variabile characters è di tipo MutableLiveData, che è una classe fornita da Android Jetpack che può essere osservata per il cambiamento dei dati
    //Resource rappresenta lo stato dei dati (caricamento, successo, errore) e CharacterResponse è il tipo dei dati dei personaggi
    val characters: MutableLiveData<Resource<CharacterResponse>> = MutableLiveData()

    //Questo blocco viene eseguito alla creazione dell'istanza di questa questa classe
    init {
        getCharacters()

    }

    //Questo metodo serve per ottenere i dati dei personaggi.
    // viewModelScope.launch esegue il blocco in modo asincrono. Tramite postValue, si specifca che i dati sono in stato di loading
    //Il metodo getChar_api viene utilizzato per ottenere i dati dei personaggi
    fun getCharacters() = viewModelScope.launch {
        characters.postValue(Resource.Loading())
        val response = charactersRepository.getChar_api()

        //Il risultato è passato a  handleResponse che gestisce la risposta
        characters.postValue(handleResponse(response))
    }


    //Prende in input un oggetto Response<CharacterResponse> e restituisce un oggetto Resource<CharacterResponse>
    private fun handleResponse(responses: Response<CharacterResponse>): Resource<CharacterResponse>{

        //Se la risposta è positiva viene estratto il corpo della risposta e viene restituito un oggetto Resource.Success contenente il corpo
        if (responses.isSuccessful) {
            responses.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        //Se la risposta è negativa si restituisce il messaggio di errore della risposta
        return Resource.Error(responses.message())
    }

}