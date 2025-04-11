package mx.com.u_life.domain.useCases.chat

data class ChatUseCases(
    val createOrGetChat: CreateOrGetChat,
    val sendMessage: SendMessage,
    val listenForMessages: ListenForMessages,
    val listenForUserChats: ListenForUserChats
)
