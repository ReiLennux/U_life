package mx.com.u_life.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.domain.models.users.UserModel
import javax.inject.Inject
import kotlin.coroutines.resume

class FireAuthService @Inject constructor(
    private val _fireAuth: FirebaseAuth,
    private val _fireStore: FirebaseFirestore,
    private val _messagingService: FirebaseMessaging
) {
    suspend fun registerUser(email: String, password: String, name: String, type: String): Boolean {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = task.result.user?.uid ?: return@addOnCompleteListener

                            _messagingService.token
                                .addOnSuccessListener { token ->
                                    val user = hashMapOf(
                                        "id" to userId,
                                        "name" to name,
                                        "userType" to type,
                                        "email" to email,
                                        "fcmToken" to token
                                    )

                                    _fireStore.collection(Constants.USERS_COLLECTION)
                                        .document(userId)
                                        .set(user)
                                        .addOnSuccessListener {
                                            continuation.resume(true)
                                        }
                                        .addOnFailureListener {
                                            continuation.resume(false)
                                        }
                                }
                                .addOnFailureListener {
                                    continuation.resume(false)
                                }
                        }
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        } catch (e: Exception) {
            false
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
        } catch (e: Exception) {
            false
        }
    }

}