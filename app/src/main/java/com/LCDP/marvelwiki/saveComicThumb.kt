package com.LCDP.marvelwiki

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.Comic
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

//  Funzione per scaricare e salvare sul dispositivo l'immagine del fumetto che vogliamo aggiungere ai preferiti o ai letti
fun saveComicThumb(context: Context, comic: Comic): String {

    //  Nome dell'immagine basato sull'ID e sul titolo del fumetto
    val imageName = "${comic.title}" + "${comic.comicId}" + ".jpg"

    //  File in cui verrà salvata l'immagine
    val imageFile = File(getFavComicThumbDirectory(context), imageName)

    //  URL completo della thumbnail
    val thumbnail = "${comic.thumbnail?.extension}" + "${comic.thumbnail?.path}"

    //  Utilizziamo Picasso per scaricare l'immagine e salvarla nel file
    Picasso.get().load(thumbnail).into(object : com.squareup.picasso.Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            bitmap?.let {

                //  Creiamo un oggetto per scrivere dati nel file specificato 'imageFile'
                val outputStream = FileOutputStream(imageFile)

                //  Viene compressa e scritta l'immagine bitmap in formato JPEG con qualità 100 nell'output stream del file
                it.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

                //  Chiudiamo l'outputStream dopo aver completato il salvataggio dell'immagine
                outputStream.close()
            }
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            //TODO gestione caso in cui download negativo
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //TODO gestione evento di caricamento
        }
    })

    //  Restituiamo il percorso assoluto del file appena creato
    return imageFile.absolutePath
}


//  Funzione per ottenere (o creare) la directory favComicThumbs
private fun getFavComicThumbDirectory(context: Context): File {

    //  Creiamo il percorso della directory
    val directory = File(context.filesDir, "favComicThumbs")
    if (!directory.exists()) {

        // Creiamo la directory se non esiste già
        directory.mkdirs()
    }

    //  Restituiamo la directory
    return directory
}
