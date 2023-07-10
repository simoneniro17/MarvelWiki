package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
    l'annotazione @Expose è fornita dalla libreria GSON e viene utilizzat per indicare
    quali campi dell'oggetto devono essere inclusi nella serializzazione o deserializzazione JSON

    Per impostazione predefinita GSON considera solo i campi pubblici e non statici di una classe
    durante la serializzazione e deserializzazione JSON. Tuttavia, quando viene applicata
    l'annotazione @Exopse a un campo, questo indica che il campo deve essere incluso nella serializzazione
    o deserializzazione JSON anche se non è pubblico o statico.
*/

data class Comic(
    @SerializedName("id")
    @Expose
    var comiId: Int? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,
    var images: List<Images>
) :Serializable

//:Serializable indica che la classe comic può essere serializzata, ciò significa che gli oggetti della classe comic
//possono essere convertiti in un flusso di byte per essere trasferiti attravero la rete, salvati su disco
// o in qualsiasi altro contesto in cui sia necessaria la persistenza dei dati