package mx.com.u_life.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.com.u_life.data.repository.catalogs.CatalogsRepository
import mx.com.u_life.data.repository.dataStore.DataStoreRepository
import mx.com.u_life.data.repository.firebase.FireAuthRepository
import mx.com.u_life.data.repository.rents.RentsRepository
import mx.com.u_life.data.repository.user.UserRepository
import mx.com.u_life.domain.useCases.catalogs.CatalogsUseCases
import mx.com.u_life.domain.useCases.catalogs.GetPropertiesType
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
import mx.com.u_life.domain.useCases.rents.GetAllRents
import mx.com.u_life.domain.useCases.rents.GetRentDetails
import mx.com.u_life.domain.useCases.rents.RentsUseCases
import mx.com.u_life.domain.useCases.userUseCases.GetUser
import mx.com.u_life.domain.useCases.userUseCases.UserUseCases
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

    @Singleton
    @Provides
    fun provideCatalogsUseCases(catalogsRepository: CatalogsRepository): CatalogsUseCases =
        CatalogsUseCases(
            getPropertiesType = GetPropertiesType(catalogsRepository)
        )

    @Singleton
    @Provides
    fun provideUserUseCases(userRepository: UserRepository): UserUseCases =
        UserUseCases(
            getUser = GetUser(userRepository)
        )

    @Singleton
    @Provides
    fun provideRentsUseCases(rentsRepository: RentsRepository): RentsUseCases =
        RentsUseCases(
            getAllRents = GetAllRents(rentsRepository),
            getRentDetails = GetRentDetails(rentsRepository)
        )
}