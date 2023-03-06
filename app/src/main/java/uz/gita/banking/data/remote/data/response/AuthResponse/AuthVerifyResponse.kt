package uz.gita.banking.data.remote.data.response.AuthResponse

import com.google.gson.annotations.SerializedName

data class AuthVerifyResponse(

	@field:SerializedName("refresh-token")
	val refreshToken: String,

	@field:SerializedName("access-token")
	val accessToken: String
)
