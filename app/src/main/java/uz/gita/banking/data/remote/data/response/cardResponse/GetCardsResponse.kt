package uz.gita.banking.data.remote.data.request.cardRequest

import com.google.gson.annotations.SerializedName

data class GetCardsResponse(

	@field:SerializedName("owner")
	val owner: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("theme-type")
	val themeType: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("expired-year")
	val expiredYear: Int,

	@field:SerializedName("expired-month")
	val expiredMonth: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("is-visible")
	val isVisible: Boolean,

	@field:SerializedName("pan")
	val pan: String
)
