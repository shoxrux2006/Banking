package uz.gita.banking.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.banking.data.remote.data.request.*
import uz.gita.banking.data.remote.data.request.AuthRequest.*
import uz.gita.banking.data.remote.data.response.AuthResponse.*

interface AuthApi {
    @POST("auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body verifySignUpRequest: VerifySignUpRequest): Response<AuthVerifyResponse>

    @POST("auth/sign-in")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @POST("auth/sign-in/verify")
    suspend fun signInVerify(@Body verifySIgnInRequest: VerifySignInRequest): Response<AuthVerifyResponse>

    @POST("auth/update-token")
    suspend fun updateToken(@Body updateTokenRequest: UpdateTokenRequest): Response<UpdateTokenResponse>

    @POST("auth/sign-in/resend")
    suspend fun signInResend(
        @Body authResendRequest: AuthResendRequest
    ): Response<SignInResponse>

    @POST("auth/sign-in/resend")
    suspend fun signUpResend(
        @Body authResendRequest: AuthResendRequest
    ): Response<SignUpResponse>
}