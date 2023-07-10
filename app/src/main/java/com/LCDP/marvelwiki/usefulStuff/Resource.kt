package com.LCDP.marvelwiki.usefulStuff

/*
    Questa classe ci torna utile per rappresentare uno stato di risorsa generico con dati di tipo 'T'.
    Ovviamente, T è un parametro generico che può essere sostituito con qualsiasi tipo di dato a seconda del contesto.
    Questo permette alla classe Resource di essere flessibile e riutilizzabile in diverse situazioni.
*/
sealed class Resource <T>(
    val data: T? = null,    // Rappresenta i dati associati alla risorsa di tipo 'T'
    val message:String? = null  // Rappresenta un messaggio di errore o un messaggio informativo associato alla risorsa
){
    class Success<T>(data: T?):Resource<T>(data)    // Sottoclasse che rappresenta uno stato di risorsa di successo
    class Error<T>(message: String, data: T? = null) : Resource<T>(data,message)    // Sottoclasse che rappresenta uno stato di risorsa di errore
    class Loading<T> :Resource<T>() // Sottoclasse che rappresenta uno stato di risorsa di caricamento
}

// In questo modo, facilitiamo la gestione degli stati e la comunicazione tra il ViewModel e la UI nell'architettura MVVM.