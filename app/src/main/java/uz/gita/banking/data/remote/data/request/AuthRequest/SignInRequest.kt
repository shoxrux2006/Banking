package uz.gita.banking.data.remote.data.request.AuthRequest

import com.google.gson.annotations.SerializedName

data class SignInRequest(
	@field:SerializedName("password")
	val password: String,
	@field:SerializedName("phone")
	val phone: String
)
