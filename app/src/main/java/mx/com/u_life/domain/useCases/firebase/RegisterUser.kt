package mx.com.u_life.domain.useCases.firebase

import mx.com.u_life.data.repository.firebase.FireAuthRepository
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val fireAuthRepository: FireAuthRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String, type: String): Boolean = fireAuthRepository.registerUser(email, password, name, type)
}