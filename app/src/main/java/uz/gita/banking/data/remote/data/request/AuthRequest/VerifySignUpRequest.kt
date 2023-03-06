package uz.gita.banking.data.remote.data.request.AuthRequest

data class VerifySignUpRequest(
    val code: String,
    val token: String
)
