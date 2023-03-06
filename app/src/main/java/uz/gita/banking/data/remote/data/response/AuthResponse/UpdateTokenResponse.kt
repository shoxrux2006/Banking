package uz.gita.banking.data.remote.data.response.AuthResponse

data class UpdateTokenResponse(
    val refreshToken: String,
    val accessToken: String
)