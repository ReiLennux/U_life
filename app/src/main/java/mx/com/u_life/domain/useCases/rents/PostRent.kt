package mx.com.u_life.domain.useCases.rents

import mx.com.u_life.data.repository.rents.RentsRepository
import mx.com.u_life.domain.models.rents.TemporalRentModel
import javax.inject.Inject

class PostRent @Inject constructor(
    private val _rentsRepository: RentsRepository
) {
    suspend operator fun invoke(rent: TemporalRentModel) = _rentsRepository.postRent(rent)

}