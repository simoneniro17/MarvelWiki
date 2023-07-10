package com.LCDP.marvelwiki.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ComicsViewModel(val comicsRepository: ComicsRepository): ViewModel(){
    // Questa classe estente la classe viewModel

    //La variabile comics è di tipo MutableLiveData, che è una classe fornita da Android Jetpack che può essere osservata per il cambiamento dei dati
    //Resource rappresenta lo stato dei dati (caricamento, successo, errore) e CharacterResponse è il tipo dei dati dei comics
    val comics: MutableLiveData<Resource<ComicResponse>> = MutableLiveData()


    //Questo metodo serve per ottenere i dati dei comics.
    //viewModelScope.launch esegue il blocco in modo asincrono. Tramite postValue, si specifca che i dati sono in stato di loading
    //Il metodo getComic_api viene utilizzato per ottenere i dati dei comics
    fun getComics(id: Int) = viewModelScope.launch {
        comics.postValue(Resource.Loading())
        val response = comicsRepository.getComics(id)

        //Il risultato è passato a  handleResponse che gestisce la risposta
        comics.postValue(handleResponse(response))
    }

    //Prende in input un oggetto Response<ComicResponse> e restituisce un oggetto Resource<ComicResponse>
    private fun handleResponse(response: Response<ComicResponse>): Resource<ComicResponse>{

        //Se la risposta è positiva viene estratto il corpo della risposta e viene restituito un oggetto Resource.Success contenente il corpo
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        //Se la risposta è negativa si restituisce il messaggio di errore della risposta
        return Resource.Error(response.message())
    }

}