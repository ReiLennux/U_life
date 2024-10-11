package mx.com.u_life.domain.models.users

data class UserModel(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val userType: String = ""
) {
    // Constructor para Firebase
    constructor() : this("", "", "", "")
}
