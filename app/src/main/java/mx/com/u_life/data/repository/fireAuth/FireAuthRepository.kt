package mx.com.u_life.data.repository.fireAuth

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import mx.com.u_life.data.network.fireAuth.FireAuthService
import javax.inject.Inject

class FireAuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val _fireAuthService: FireAuthService
) {
    suspend fun registerUser(email: String, password: String) {
        _fireAuthService.registerUser(email, password) { success, message ->
            if (!success) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun loginUser(email: String, password: String) {
        _fireAuthService.loginUser(email, password) { success, message ->
            if (!success) {
                Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}