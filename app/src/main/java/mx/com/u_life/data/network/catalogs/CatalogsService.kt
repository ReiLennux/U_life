package mx.com.u_life.data.network.catalogs

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CatalogsService @Inject constructor(
    private val _fireStore: FirebaseFirestore
) {
    suspend fun getPropertiesType(): QuerySnapshot {
        return try {
            suspendCancellableCoroutine { continuation ->
                _fireStore.collection("ComCatPropertyType").get()
                    .addOnSuccessListener { result ->
                        // Resume the coroutine with the result
                        println(result.documents)
                        continuation.resume(result)
                    }
                    .addOnFailureListener { exception ->
                        // Resume the coroutine with an exception
                        println("Error: ${exception.message}")
                        continuation.resumeWithException(exception)
                    }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
