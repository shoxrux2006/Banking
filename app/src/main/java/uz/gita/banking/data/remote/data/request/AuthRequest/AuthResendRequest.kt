package uz.gita.banking.data.remote.data.request.AuthRequest

import com.google.gson.annotations.SerializedName

data class AuthResendRequest(

	@field:SerializedName("token")
	val token: String
)
