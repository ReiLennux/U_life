package mx.com.u_life.domain.models.users

data class UserModel(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val userType: String = "",
    val fcmToken: String? = null
) {
    constructor() : this("", "", "", "", null)
}
