package mx.com.u_life.data.repository.rents

import mx.com.u_life.data.network.rents.RentsService
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.domain.models.rents.TemporalRentModel
import javax.inject.Inject

class RentsRepository @Inject constructor(
    private val _rentsService: RentsService
) {
    suspend fun getAllRents(): Response<List<RentLocationModel>> {
        val success = _rentsService.getAllRents()
        return success
    }

    suspend fun getRentDetails(rentId: String): Response<RentModel> {
        val success = _rentsService.getRentDetails(rentId)
        return success
    }

    suspend fun postRent(rent: TemporalRentModel): Response<Boolean> {
        val success = _rentsService.postRent(rent)
        return success
    }

}