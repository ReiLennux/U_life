package mx.com.u_life.domain.useCases.fireAuth

import mx.com.u_life.data.repository.fireAuth.FireAuthRepository
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val _fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = _fireAuthRepository.loginUser(email, password)
}