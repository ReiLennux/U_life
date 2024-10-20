package mx.com.u_life.data.network.rents

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.domain.models.rents.TemporalRentModel
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
                    val name = document.getString("name")
                    val latitude = document.getDouble("location.latitude")
                    val longitude = document.getDouble("location.longitude")
                    val id = document.id

                    if (latitude != null && longitude != null) {
                        RentLocationModel(id = id, latitude = latitude, longitude = longitude, name = name ?: "")
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

    suspend fun postRent(rent: TemporalRentModel): Response<Boolean> {
        FirebaseAuth.getInstance().currentUser ?: return Response.Error(Exception("User is not authenticated"))

        val storageRef = Firebase.storage.reference.child("Photos").child(rent.ownerId!!)
        val uploadedImageUrls = mutableListOf<String>()

        rent.images.forEachIndexed { _, imageUri ->
            try {
                val imageRef = storageRef.child("image_${imageUri}")
                imageRef.putFile(imageUri).await()

                val downloadUrl = imageRef.downloadUrl.await().toString()
                uploadedImageUrls.add(downloadUrl)
            } catch (e: Exception) {
                return Response.Error(e)
            }
        }

        val finalRent = RentModel(
            ownerId = rent.ownerId,
            name = rent.name,
            description = rent.description,
            price = rent.price,
            images = uploadedImageUrls,
            restrictions = rent.restrictions,
            services = rent.services,
            location = rent.location,
            type = rent.type
        )

        return try {
            _fireStore.collection("Rentas")
                .add(finalRent)
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

}