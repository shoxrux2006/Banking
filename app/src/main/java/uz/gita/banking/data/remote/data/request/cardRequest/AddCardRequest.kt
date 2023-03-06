package uz.gita.banking.data.remote.data.request.cardRequest

import com.google.gson.annotations.SerializedName

data class AddCardRequest(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("expired-year")
	val expiredYear: String,

	@field:SerializedName("expired-month")
	val expiredMonth: String,

	@field:SerializedName("pan")
	val pan: String
)
