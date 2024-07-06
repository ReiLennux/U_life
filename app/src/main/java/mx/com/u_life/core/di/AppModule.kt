package mx.com.u_life.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.u_life.data.repository.dataStore.DataStoreRepository
import mx.com.u_life.data.repository.firebase.FireAuthRepository
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.dataStore.GetDataBoolean
import mx.com.u_life.domain.useCases.dataStore.GetDataInt
import mx.com.u_life.domain.useCases.dataStore.GetDataString
import mx.com.u_life.domain.useCases.dataStore.SetDataBoolean
import mx.com.u_life.domain.useCases.dataStore.SetDataInt
import mx.com.u_life.domain.useCases.dataStore.SetDataString
import mx.com.u_life.domain.useCases.firebase.FireAuthUseCases
import mx.com.u_life.domain.useCases.firebase.LoginUser
import mx.com.u_life.domain.useCases.firebase.RegisterUser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDataStoreUseCases(dataStoreRepository: DataStoreRepository): DataStoreUseCases =
        DataStoreUseCases(
            setDataString = SetDataString(dataStoreRepository),
            getDataString = GetDataString(dataStoreRepository),
            setDataBoolean = SetDataBoolean(dataStoreRepository),
            getDataBoolean = GetDataBoolean(dataStoreRepository),
            setDataInt = SetDataInt(dataStoreRepository),
            getDataInt = GetDataInt(dataStoreRepository)
        )

    @Singleton
    @Provides
    fun provideFireAuthUseCases(fireAuthRepository: FireAuthRepository): FireAuthUseCases =
        FireAuthUseCases(
            registerUser = RegisterUser(fireAuthRepository),
            loginUser = LoginUser(fireAuthRepository)
        )
}