package mx.com.u_life.domain.useCases.rents

import mx.com.u_life.data.repository.rents.RentsRepository
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentLocationModel
import javax.inject.Inject

class GetAllRents @Inject constructor(
    private val _rentsRepository: RentsRepository
) {
    suspend operator fun invoke(): Response<List<RentLocationModel>> = _rentsRepository.getAllRents()
}