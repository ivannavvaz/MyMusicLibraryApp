package com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.playlist

import com.google.gson.annotations.SerializedName
import com.inavarro.mibibliotecamusical.common.Constants

class PlaylistInfo (
    @SerializedName(Constants.TITULO_PARAM) val tituloPlaylist: String
)


