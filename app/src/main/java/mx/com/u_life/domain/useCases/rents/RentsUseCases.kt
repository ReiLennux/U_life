package mx.com.u_life.domain.useCases.rents

data class RentsUseCases(
    val getAllRents: GetAllRents,
    val getRentDetails: GetRentDetails,
    val postRent: PostRent,
    val getMyRents: GetMyRents,

)