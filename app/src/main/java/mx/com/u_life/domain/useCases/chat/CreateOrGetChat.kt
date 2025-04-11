package mx.com.u_life.domain.useCases.chat

import mx.com.u_life.data.repository.chat.ChatRepository
import javax.inject.Inject

class CreateOrGetChat @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userA: String, userB: String): String {
        return chatRepository.createOrGetChat(userA, userB)
    }
}