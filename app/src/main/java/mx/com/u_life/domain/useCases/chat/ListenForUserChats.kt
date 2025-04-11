package mx.com.u_life.domain.useCases.chat

import com.google.firebase.firestore.ListenerRegistration
import mx.com.u_life.data.repository.chat.ChatRepository
import mx.com.u_life.domain.models.chats.ChatModel
import javax.inject.Inject

class ListenForUserChats @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String, onResult: (List<ChatModel>) -> Unit): ListenerRegistration {
        return chatRepository.listenForUserChats(userId, onResult)
    }
}