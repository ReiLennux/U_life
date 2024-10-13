package mx.com.u_life.domain.models.rents

data class RentLocationModel(
    val id: String,
    val nombre: String,
    val latitud: Double,
    val longitud: Double
)