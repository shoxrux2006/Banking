package uz.gita.banking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.banking.repository.AuthRepository
import uz.gita.banking.repository.CardRepository
import uz.gita.banking.repository.impl.AuthRepositoryImpl
import uz.gita.banking.repository.impl.CardRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds
    Singleton]
    fun provideAuthRepo(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
    @[Binds
    Singleton]
    fun provideCardRepo(authRepositoryImpl: CardRepositoryImpl): CardRepository
}