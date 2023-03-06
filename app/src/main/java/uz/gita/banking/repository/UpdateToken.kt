package uz.gita.banking.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.banking.data.remote.data.request.AuthRequest.UpdateTokenRequest
import uz.gita.banking.data.remote.data.response.AuthResponse.UpdateTokenResponse
import uz.gita.banking.utils.NetworkResponse

interface UpdateToken {
    fun updateToken(updateTokenRequest: UpdateTokenRequest): Flow<NetworkResponse<UpdateTokenResponse>>
}