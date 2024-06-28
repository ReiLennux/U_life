package mx.com.u_life.data.repository.dataStore

import mx.com.u_life.data.network.dataStore.DataStoreService
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val _dataStoreService: DataStoreService
) {
    suspend fun setDataString(key: String, value: String) {
        _dataStoreService.setDataString(key, value)
    }

    suspend fun getDataString(key: String): String {
        return _dataStoreService.getDataString(key)
    }

    suspend fun setDataBoolean(key: String, value: Boolean) {
        _dataStoreService.setDataBoolean(key, value)
    }

    suspend fun getDataBoolean(key: String): Boolean {
        return _dataStoreService.getDataBoolean(key)
    }

    suspend fun setDataInt(key: String, value: Int) {
        _dataStoreService.setDataInt(key, value)
    }

    suspend fun getDataInt(key: String): Int {
        return _dataStoreService.getDataInt(key)
    }

}