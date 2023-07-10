package com.LCDP.marvelwiki.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
//i commenti sono gli stessi degli altri file
data class Thumbnail(
    @SerializedName("path")
    @Expose
    var path: String? = null,

    @SerializedName("extension")
    @Expose
    var extension: String? = null
)