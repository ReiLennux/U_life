package mx.com.u_life.domain.useCases.rents

import mx.com.u_life.data.repository.rents.RentsRepository

data class GetMyRents(
    private val _rentsRepository: RentsRepository
){
    suspend operator fun invoke(ownerId: String) = _rentsRepository.getMyRents(ownerId)
}
