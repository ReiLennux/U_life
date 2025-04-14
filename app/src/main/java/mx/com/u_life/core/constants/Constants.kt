package mx.com.u_life.core.constants

object Constants {
    //User constants
    const val USER_PREFERENCES = "user_preferences"
    const val USER_NAME = "user_name"
    const val USER_EMAIL = "user_email"
    const val USER_UID = "user_uid"
    const val USER_TYPE = "user_type"
    const val USER_FIELD_FCMTOKEN = "fcmToken"

    //Firestore constants
    const val USERS_COLLECTION = "Users"
    const val RENTS_COLLECTION = "Rentas"
    const val MESSAGES_COLLECTION = "Messages"
    const val CHATS_COLLECTION = "Chats"
    const val PROPERTY_TYPE = "ComCatPropertyType"

    // Rents Collection
    const val RENT_FIELD_NAME = "name"
    const val RENT_FIELD_OWNERID = "ownerId"
    const val RENT_LOCATION_LATITUDE = "location.latitude"
    const val RENT_LOCATION_LONGITUDE = "location.longitude"
    const val RENT_FIELD_TYPE = "type"

    // Firebase Storage
    const val STORAGE_PHOTOS = "Photos"
}