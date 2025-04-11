package mx.com.u_life.domain.models.chats

data class ChatModel(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastTimestamp: Long = 0L
)