package mx.com.u_life.domain.models.chats

data class ChatModel(
    val chatId: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L,
    val userId: String = "",
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val unreadCount: Int = 0
)