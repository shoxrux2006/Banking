package uz.gita.banking.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.banking.data.remote.data.request.AuthRequest.SignInRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.SignUpRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignInRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignUpRequest
import uz.gita.banking.data.remote.data.response.AuthResponse.AuthVerifyResponse
import uz.gita.banking.data.remote.data.response.AuthResponse.SignUpResponse
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.data.remote.data.response.AuthResponse.SignInResponse


interface AuthRepository {
    fun signUp(signUpRequest: SignUpRequest): Flow<NetworkResponse<SignUpResponse>>
    fun signIn(signInRequest: SignInRequest): Flow<NetworkResponse<SignInResponse>>
    fun verifySignIn(verifySIgnInRequest: VerifySignInRequest): Flow<NetworkResponse<AuthVerifyResponse>>
    fun verifySignUp(verifySignUpRequest: VerifySignUpRequest): Flow<NetworkResponse<AuthVerifyResponse>>

}