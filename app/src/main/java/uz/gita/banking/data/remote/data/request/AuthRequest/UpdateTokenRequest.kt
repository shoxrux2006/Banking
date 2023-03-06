package uz.gita.banking.data.remote.data.request.AuthRequest

import com.google.gson.annotations.SerializedName

data class UpdateTokenRequest(
	val refreshToken: String
)
