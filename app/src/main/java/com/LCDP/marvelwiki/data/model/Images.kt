package com.LCDP.marvelwiki.data.model

/*
* questa classe rappresenta un'immagine associata ad un elemento specifico della marvel API.
* Utilizzando questa classe si possono ottenere l'estensione e il percorso di un'immagine associata
* a un elemento specifico della marvel API
* */
data class Images(
    val extension: String,
    val path: String
)

