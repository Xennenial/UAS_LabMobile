package com.mobile.mp3_final.api

import com.google.gson.annotations.SerializedName

data class HarryPotterResponse(

	@field:SerializedName("HarryPotterResponse")
	val harryPotterResponse: List<HarryPotterResponseItem?>? = null
)

data class Wand(

	@field:SerializedName("core")
	val core: String? = null,

	@field:SerializedName("length")
	val length: Int? = null,

	@field:SerializedName("wood")
	val wood: String? = null
)

data class HarryPotterResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("species")
	val species: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("wizard")
	val wizard: Boolean? = null,

	@field:SerializedName("eyeColour")
	val eyeColour: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("yearOfBirth")
	val yearOfBirth: Int? = null
)
