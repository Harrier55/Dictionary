package com.example.dictionary.domain.entities.skyeng

import com.google.gson.annotations.SerializedName



data class Skyeng_Base (

	@SerializedName("id") val id : Int,
	@SerializedName("text") val text : String,
	@SerializedName("meanings") val meanings : List<Meanings>
)