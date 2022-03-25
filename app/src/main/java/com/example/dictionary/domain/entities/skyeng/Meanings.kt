package com.example.dictionary.domain.entities.skyeng

import com.google.gson.annotations.SerializedName

data class Meanings (

	@SerializedName("id") val id : Int,
	@SerializedName("partOfSpeechCode") val partOfSpeechCode : String,
	@SerializedName("translation") val translation : Translation,
	@SerializedName("previewUrl") val previewUrl : String,
	@SerializedName("imageUrl") val imageUrl : String,
	@SerializedName("transcription") val transcription : String,
	@SerializedName("soundUrl") val soundUrl : String
)