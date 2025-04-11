package mx.com.u_life.data.network.user

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.users.UserModel
import javax.inject.Inject

class UserService @Inject constructor(
    private val _fireStore: FirebaseFirestore
) {
    suspend fun getUser(userId: String): Response<UserModel> {
        return try {
            val documentSnapshot = _fireStore.collection(Constants.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()
            val user = documentSnapshot.toObject(UserModel::class.java)
            println(user)
            if (user != null) {
                Response.Success(user)
            } else {
                Response.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}