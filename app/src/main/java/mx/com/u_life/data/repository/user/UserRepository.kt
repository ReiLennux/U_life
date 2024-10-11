package mx.com.u_life.data.repository.user

import mx.com.u_life.data.network.user.UserService
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.users.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val _service: UserService
) {
    suspend fun getUser(userId: String): Response<UserModel> {
        return try {
            _service.getUser(userId = userId)
        } catch (e: Exception) {
            Response.Error(exception = e)
        }
    }
}
