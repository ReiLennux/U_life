package mx.com.u_life.data.network.dataStore

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import mx.com.u_life.core.constants.Constants.USER_PREFERENCES
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES
)

class DataStoreService @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreClient {
    override suspend fun setDataString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataString(key: String): String {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: ""
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            "error"
        }
    }

    override suspend fun setDataBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataBoolean(key: String): Boolean {
        return try {
            val preferencesKey = booleanPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: false
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            false
        }
    }

    override suspend fun setDataInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getDataInt(key: String): Int {
        return try {
            val preferencesKey = intPreferencesKey(key)
            val preferencesFlow = context.dataStore.data.map { preferences ->
                preferences[preferencesKey] ?: 0
            }
            preferencesFlow.first()
        } catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            0
        }
    }

}