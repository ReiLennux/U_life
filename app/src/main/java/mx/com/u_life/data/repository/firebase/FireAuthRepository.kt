package mx.com.u_life.data.repository.firebase

import mx.com.u_life.data.network.firebase.FireAuthService
import javax.inject.Inject

class FireAuthRepository @Inject constructor(
    private val _fireAuthService: FireAuthService
) {
    suspend fun registerUser(email: String, password: String, name: String, type: String): Boolean {
        val success = _fireAuthService.registerUser(email, password, name, type)
        return success
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        val success = _fireAuthService.loginUser(email, password)
        return success
    }
}