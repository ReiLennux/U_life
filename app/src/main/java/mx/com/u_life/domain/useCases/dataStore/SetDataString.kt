package mx.com.u_life.domain.useCases.dataStore

import mx.com.u_life.data.repository.dataStore.DataStoreRepository
import javax.inject.Inject

class SetDataString @Inject constructor(
    private val _repository: DataStoreRepository
) {
    suspend operator fun invoke(key: String, value: String) = _repository.setDataString(key, value)
}