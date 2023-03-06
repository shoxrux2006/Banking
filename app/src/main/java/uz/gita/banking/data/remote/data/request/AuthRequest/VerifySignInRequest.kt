package uz.gita.banking.data.remote.data.request.AuthRequest

import com.google.gson.annotations.SerializedName

data class VerifySignInRequest(
	@field:SerializedName("code")
	val code: String,
	@field:SerializedName("token")
	val token: String
)
