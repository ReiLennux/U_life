package mx.com.u_life.data.repository.chat

import com.google.firebase.firestore.ListenerRegistration
import mx.com.u_life.data.network.chat.ChatService
import mx.com.u_life.domain.models.chats.ChatModel
import mx.com.u_life.domain.models.chats.MessageModel
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val service: ChatService
) {
    suspend fun createOrGetChat(userA: String, userB: String): String {
        return service.createChatIfNotExists(userA, userB)
    }

    suspend fun sendMessage(chatId: String, message: MessageModel) {
        service.sendMessage(chatId, message)
    }

    fun listenForMessages(chatId: String, onResult: (List<MessageModel>) -> Unit): ListenerRegistration {
        return service.listenMessages(chatId, onResult)
    }

    fun listenForUserChats(userId: String, onResult: (List<ChatModel>) -> Unit): ListenerRegistration {
        return service.listenUserChats(userId, onResult)
    }
}