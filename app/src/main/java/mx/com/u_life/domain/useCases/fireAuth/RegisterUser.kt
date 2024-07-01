package mx.com.u_life.domain.useCases.fireAuth

import mx.com.u_life.data.repository.fireAuth.FireAuthRepository
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = fireAuthRepository.registerUser(email, password)
}