package uz.gita.banking.repository.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.banking.data.remote.api.AuthApi
import uz.gita.banking.data.remote.data.request.AuthRequest.SignInRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.SignUpRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignInRequest
import uz.gita.banking.data.remote.data.request.AuthRequest.VerifySignUpRequest
import uz.gita.banking.data.remote.data.response.AuthResponse.AuthVerifyResponse
import uz.gita.banking.data.remote.data.response.AuthResponse.SignInResponse
import uz.gita.banking.data.remote.data.response.AuthResponse.SignUpResponse
import uz.gita.banking.data.shp.Shp
import uz.gita.banking.repository.AuthRepository
import uz.gita.banking.utils.Const
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.utils.hasConnection
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    val shp: Shp,
    val authApi: AuthApi
) : AuthRepository {
    override fun signUp(signUpRequest: SignUpRequest): Flow<NetworkResponse<SignUpResponse>> =
        flow<NetworkResponse<SignUpResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = authApi.signUp(signUpRequest)
                if (auth.isSuccessful) {
                    auth.body()?.let {
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(it))
                    }
                } else {
                    emit(NetworkResponse.Loading(false))
                    emit(NetworkResponse.Error(auth.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }
        }.catch {
            emit(NetworkResponse.Error("Some thing went wrong"))
        }.flowOn(Dispatchers.IO)

    override fun signIn(signInRequest: SignInRequest): Flow<NetworkResponse<SignInResponse>> =
        flow<NetworkResponse<SignInResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = authApi.signIn(signInRequest)
                if (auth.isSuccessful) {
                    auth.body()?.let {
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(it))
                    }
                } else {
                    emit(NetworkResponse.Loading(false))
                    emit(NetworkResponse.Error(auth.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }
        }.catch {
            emit(NetworkResponse.Error("Some thing went wrong"))
        }.flowOn(Dispatchers.IO)

    override fun verifySignIn(verifySIgnInRequest: VerifySignInRequest): Flow<NetworkResponse<AuthVerifyResponse>> =
        flow<NetworkResponse<AuthVerifyResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = authApi.signInVerify(verifySIgnInRequest)
                if (auth.isSuccessful) {
                    auth.body()?.let {
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(it))
                        shp.setBool(Const.isFirst, false)
                        shp.setString(Const.accessToken, "Bearer ${it.accessToken}")
                        shp.setString(Const.refreshToken, "Bearer ${it.refreshToken}")
                    }
                } else {
                    emit(NetworkResponse.Loading(false))
                    emit(NetworkResponse.Error(auth.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }
        }.catch {
            emit(NetworkResponse.Error("Some thing went wrong"))
        }.flowOn(Dispatchers.IO)

    override fun verifySignUp(verifySignUpRequest: VerifySignUpRequest): Flow<NetworkResponse<AuthVerifyResponse>> =
        flow<NetworkResponse<AuthVerifyResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = authApi.signUpVerify(verifySignUpRequest)
                if (auth.isSuccessful) {
                    auth.body()?.let {
                        shp.setBool(Const.isFirst, false)
                        shp.setString(Const.accessToken, it.accessToken)
                        shp.setString(Const.refreshToken, it.refreshToken)
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(it))
                    }
                } else {
                    emit(NetworkResponse.Loading(false))
                    emit(NetworkResponse.Error(auth.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }

        }.catch {
            emit(NetworkResponse.Error("Some thing went wrong"))
        }.flowOn(Dispatchers.IO)


}