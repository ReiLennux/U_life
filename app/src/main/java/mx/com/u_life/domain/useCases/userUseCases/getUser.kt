package mx.com.u_life.domain.useCases.userUseCases

import mx.com.u_life.data.repository.user.UserRepository
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.users.UserModel
import javax.inject.Inject

class GetUser @Inject constructor(
    private val _repository: UserRepository,
)  {
    suspend fun invoke(userId: String): Response<UserModel> = _repository.getUser(userId = userId)

}