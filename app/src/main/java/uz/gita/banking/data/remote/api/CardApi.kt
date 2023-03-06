package uz.gita.banking.data.remote.api

import retrofit2.Response
import retrofit2.http.*
import uz.gita.banking.data.remote.data.request.cardRequest.AddCardRequest
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.data.remote.data.request.cardRequest.UpdateCardRequest
import uz.gita.banking.data.remote.data.response.cardResponse.CardResponse

interface CardApi {

    @GET("card")
    suspend fun getAllCards(
        @Header("Authorization") token: String
    ): Response<List<GetCardsResponse>>

    @POST("card")
    suspend fun addCard(
        @Header("Authorization") token: String,
        @Body addCardRequest: AddCardRequest
    ): Response<CardResponse>

    @PUT("card")
    suspend fun updateCard(
        @Header("Authorization") token: String,
        @Body updateCardRequest: UpdateCardRequest
    ): Response<CardResponse>

    @DELETE("card/{id}")
    suspend fun deleteCard(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<CardResponse>

}