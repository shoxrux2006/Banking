package uz.gita.banking.data.remote.data.response.cardResponse

import com.google.gson.annotations.SerializedName

data class CardResponse(

	@field:SerializedName("message")
	val message: String
)
