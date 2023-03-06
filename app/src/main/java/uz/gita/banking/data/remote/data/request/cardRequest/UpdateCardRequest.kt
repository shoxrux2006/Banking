package uz.gita.banking.data.remote.data.request.cardRequest

import com.google.gson.annotations.SerializedName

data class UpdateCardRequest(

	@field:SerializedName("theme-type")
	val themeType: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("is-visible")
	val isVisible: String
)
