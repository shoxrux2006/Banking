package uz.gita.banking.repository.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.banking.data.remote.api.CardApi
import uz.gita.banking.data.remote.data.request.cardRequest.AddCardRequest
import uz.gita.banking.data.remote.data.request.cardRequest.GetCardsResponse
import uz.gita.banking.data.remote.data.request.cardRequest.UpdateCardRequest
import uz.gita.banking.data.remote.data.response.cardResponse.CardResponse
import uz.gita.banking.data.shp.Shp
import uz.gita.banking.repository.CardRepository
import uz.gita.banking.utils.Const.accessToken
import uz.gita.banking.utils.NetworkResponse
import uz.gita.banking.utils.hasConnection
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    val cardApi: CardApi,
    val shp: Shp,
    @ApplicationContext val context: Context
) : CardRepository {
    override fun addCard(addCardRequest: AddCardRequest): Flow<NetworkResponse<CardResponse>> =
        flow<NetworkResponse<CardResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = cardApi.addCard(shp.getString(accessToken), addCardRequest)
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

    override fun updateCard(updateCardRequest: UpdateCardRequest): Flow<NetworkResponse<CardResponse>> =
        flow<NetworkResponse<CardResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = cardApi.updateCard(shp.getString(accessToken), updateCardRequest)
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

    override fun getCards(): Flow<NetworkResponse<List<GetCardsResponse>>> =
        flow<NetworkResponse<List<GetCardsResponse>>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = cardApi.getAllCards(shp.getString(accessToken))
                if (auth.isSuccessful) {
                    auth.body()?.let {
                        emit(NetworkResponse.Loading(false))
                        emit(NetworkResponse.Success(it))
                    }
                } else {
                    emit(NetworkResponse.Error(auth.message()))
                }
            } else {
                emit(NetworkResponse.NoConnection())
            }
        }.catch {
            emit(NetworkResponse.Error("Some thing went wrong"))
        }.flowOn(Dispatchers.IO)

    override fun deleteCard(id: String): Flow<NetworkResponse<CardResponse>> =
        flow<NetworkResponse<CardResponse>> {
            if (hasConnection(context)) {
                emit(NetworkResponse.Loading(true))
                val auth = cardApi.deleteCard(shp.getString(accessToken), id)
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
}