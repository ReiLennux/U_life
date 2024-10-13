package mx.com.u_life.domain.models.rents

data class RentModel(
    val costoMensual: Int = 0,
    val descripcion: String = "",
    val imagenes: List<String> = emptyList(),
    val nombre: String = "",
    val restricciones: String = "",
    val serviciosIncluidos: List<String> = emptyList(),
    val ubicacion: UbicacionModel? = null
)