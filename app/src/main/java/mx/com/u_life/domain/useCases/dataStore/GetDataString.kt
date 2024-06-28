package mx.com.u_life.domain.useCases.dataStore

import mx.com.u_life.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class GetDataString @Inject constructor(
    private val _repository: DataStoreRepository
){
    suspend operator fun invoke(key: String): String = _repository.getDataString(key)
}