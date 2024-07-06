package mx.com.u_life.presentation.utils

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null,
)
