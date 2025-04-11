package mx.com.u_life.domain.useCases.chat

import mx.com.u_life.data.repository.chat.ChatRepository
import mx.com.u_life.domain.models.chats.MessageModel
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, message: MessageModel) {
        chatRepository.sendMessage(chatId, message)
    }
}