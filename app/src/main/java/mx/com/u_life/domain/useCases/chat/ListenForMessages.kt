package mx.com.u_life.domain.useCases.chat

import com.google.firebase.firestore.ListenerRegistration
import mx.com.u_life.data.repository.chat.ChatRepository
import mx.com.u_life.domain.models.chats.MessageModel
import javax.inject.Inject

class ListenForMessages @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, onResult: (List<MessageModel>) -> Unit): ListenerRegistration {
        return chatRepository.listenForMessages(chatId, onResult)
    }
}