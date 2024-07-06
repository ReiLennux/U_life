package mx.com.u_life.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class FireAuthService @Inject constructor(
    private val _fireAuth: FirebaseAuth,
    private val _fireStore: FirebaseFirestore
) {
    suspend fun registerUser(email: String, password: String, name: String): Boolean {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = task.result.user?.uid
                            val user = hashMapOf(
                                "name" to name,
                                "userType" to "student"
                            )
                            _fireStore.collection("students").document(userId!!).set(user)
                            continuation.resume(true)
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        } catch (_e: Exception) {
            return false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            continuation.resume(true)
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        } catch (_e: Exception) {
            false
        }
    }

}