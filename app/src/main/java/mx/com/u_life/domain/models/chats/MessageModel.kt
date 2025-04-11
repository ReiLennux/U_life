package mx.com.u_life.domain.models.chats

data class MessageModel(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)