package uz.gita.banking.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.banking.data.remote.data.request.cardRequest.AddCardRequest
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.data.remote.data.request.cardRequest.UpdateCardRequest
import uz.gita.banking.data.remote.data.response.cardResponse.CardResponse
import uz.gita.banking.utils.NetworkResponse

interface CardRepository {
    fun addCard(addCardRequest: AddCardRequest): Flow<NetworkResponse<CardResponse>>
    fun updateCard(updateCardRequest: UpdateCardRequest): Flow<NetworkResponse<CardResponse>>
    fun getCards(): Flow<NetworkResponse<List<GetCardsResponse>>>
    fun deleteCard(id: String): Flow<NetworkResponse<CardResponse>>
}