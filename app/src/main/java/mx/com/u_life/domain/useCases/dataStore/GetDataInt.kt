package mx.com.u_life.domain.useCases.dataStore

import mx.com.u_life.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class GetDataInt @Inject constructor(
    private val _repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String): Int = _repository.getDataInt(key)
}