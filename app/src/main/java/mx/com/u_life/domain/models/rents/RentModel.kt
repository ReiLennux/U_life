package mx.com.u_life.domain.models.rents

import android.net.Uri

data class RentModel(
    val ownerId : String? = "",
    val ownerName: String? = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val images: List<String> = emptyList(),
    val restrictions: List<String> = emptyList(),
    val services: List<String> = emptyList(),
    val location: LocationModel? = null,
    val type: String = "",
    val state: String = ""
)

data class TemporalRentModel(
    val ownerId : String? = "",
    val ownerName: String? = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val images: List<Uri> = emptyList(),
    val restrictions: List<String> = emptyList(),
    val services: List<String> = emptyList(),
    val location: LocationModel? = null,
    val type: String = "",
    val state: String = ""

)