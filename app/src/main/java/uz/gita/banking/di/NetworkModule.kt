package uz.gita.banking.di

import android.content.Context
import android.util.Log
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.banking.data.remote.api.AuthApi
import uz.gita.banking.data.remote.api.CardApi
import uz.gita.banking.utils.Const
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @[Provides Singleton]
    fun provideOkHTTP(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(context))
        .build()

    @[Provides Singleton]
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Const.baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton]
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun provideCardApi(retrofit: Retrofit): CardApi = retrofit.create(CardApi::class.java)

}

