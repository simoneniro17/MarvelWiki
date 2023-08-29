package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*  '@SerializedName' specifica il nome del campo JSON corrispondente alla proprietà
    della classe 'Character' durante il funzionamento di Gson.
    '@Expose' indica che il campo è incluso nel processo di serializzazione
    e deserializzazione con Gson.   */
data class Character(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("events")
    @Expose
    var events: EventWrapper? = null, // Qui puoi usare una classe EventWrapper simile a ComicsWrapper

    @SerializedName("comics")
    @Expose
    var comics: ComicsWrapper? = null, // Qui puoi usare una classe ComicsWrapper simile a ComicsWrapper

    @SerializedName("stories")
    @Expose
    var stories: StoriesWrapper? = null // Qui puoi usare una classe StoriesWrapper simile a ComicsWrapper
) : Serializable

data class EventWrapper(
    @SerializedName("available")
    @Expose
    var available: Int
) : Serializable

data class ComicsWrapper(
    @SerializedName("available")
    @Expose
    var available: Int
) : Serializable

data class StoriesWrapper(
    @SerializedName("available")
    @Expose
    var available: Int
) : Serializable
