package mx.com.u_life.domain.useCases.rents

import mx.com.u_life.data.repository.rents.RentsRepository
import javax.inject.Inject

class GetRentDetails @Inject constructor(
    private val _rentsRepository: RentsRepository
) {
    suspend operator fun invoke(rentId: String) = _rentsRepository.getRentDetails(rentId)
}