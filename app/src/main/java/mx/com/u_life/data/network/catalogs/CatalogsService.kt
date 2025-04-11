package mx.com.u_life.data.network.catalogs

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import mx.com.u_life.core.constants.Constants
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CatalogsService @Inject constructor(
    private val _fireStore: FirebaseFirestore
) {
    suspend fun getPropertiesType(): QuerySnapshot {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireStore.collection(Constants.PROPERTY_TYPE).get()
                    .addOnSuccessListener { result ->
                        continuation.resume(result)
                    }
                    .addOnFailureListener { exception ->
                        println("Error: ${exception.message}")
                        continuation.resumeWithException(exception)
                    }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
