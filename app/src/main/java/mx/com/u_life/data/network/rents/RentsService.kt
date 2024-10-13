package mx.com.u_life.data.network.rents

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.domain.models.rents.RentModel
import javax.inject.Inject

class RentsService @Inject constructor(
    private val _fireStore: FirebaseFirestore
) {
    suspend fun getAllRents(): Response<List<RentLocationModel>> {
        return try {
            val rentList = _fireStore.collection("Rentas")
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    val nombre = document.getString("nombre")
                    val latitud = document.getDouble("ubicacion.latitud")
                    val longitud = document.getDouble("ubicacion.longitud")
                    val id = document.id

                    if (latitud != null && longitud != null) {
                        RentLocationModel(id = id, latitud = latitud, longitud = longitud, nombre = nombre ?: "")
                    } else {
                        null
                    }
                }

            Response.Success(rentList)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }


    suspend fun getRentDetails(rentId: String): Response<RentModel> {
        return try {
            val rentDetails = _fireStore.document("Rentas/$rentId")
                .get()
                .await()
                .toObject(RentModel::class.java)

            if (rentDetails != null) {
                Response.Success(rentDetails)
            } else {
                Response.Error(Exception("Ocurrio un error al obtener los detalles de la renta"))
            }
        } catch (e: Exception) {
            Response.Error(e)
        }
    }


}