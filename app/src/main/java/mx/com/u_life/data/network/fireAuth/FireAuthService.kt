package mx.com.u_life.data.network.fireAuth

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireAuthService @Inject constructor(
    private val _fireAuth: FirebaseAuth
) {
    suspend fun registerUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        _fireAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }.await()
    }

    suspend fun loginUser(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        _fireAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }.await()
    }
}