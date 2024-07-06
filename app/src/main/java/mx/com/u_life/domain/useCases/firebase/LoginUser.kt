package mx.com.u_life.domain.useCases.firebase

import mx.com.u_life.data.repository.firebase.FireAuthRepository
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val _fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Boolean = _fireAuthRepository.loginUser(email, password)
}