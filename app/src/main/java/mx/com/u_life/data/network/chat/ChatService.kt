package mx.com.u_life.data.network.chat

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.domain.models.chats.ChatModel
import mx.com.u_life.domain.models.chats.MessageModel
import javax.inject.Inject

class ChatService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val chatsCollection = firestore.collection(Constants.CHATS_COLLECTION)

    fun getChatId(userA: String, userB: String): String {
        return listOf(userA, userB).sorted().joinToString("_")
    }

    suspend fun createChatIfNotExists(userA: String, userB: String): String {
        val chatId = getChatId(userA, userB)
        val chatRef = chatsCollection.document(chatId)
        val snapshot = chatRef.get().await()

//        if (!snapshot.exists()) {
//            val unreadCounts = mapOf(
//                userA to 0,
//                userB to 0
//            )
//
////            val newChat = ChatModel(
////                id = chatId,
////                participants = listOf(userA, userB),
////                lastMessage = "",
////                lastTimestamp = System.currentTimeMillis(),
////                unreadCounts = unreadCounts
////            )
//
//            chatRef.set(newChat).await()
//        }
        return chatId
    }

    suspend fun sendMessage(chatId: String, message: MessageModel) {
        val messagesCollection = chatsCollection.document(chatId)
            .collection("messages")

        val messageDoc = messagesCollection.document()
        val newMessage = message.copy(id = messageDoc.id)

        messageDoc.set(newMessage).await()

        chatsCollection.document(chatId).update(
            mapOf(
                "lastMessage" to newMessage.text,
                "lastTimestamp" to newMessage.timestamp
            )
        ).await()
    }

    fun listenMessages(chatId: String, onResult: (List<MessageModel>) -> Unit): ListenerRegistration {
        return chatsCollection.document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(MessageModel::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                onResult(messages)
            }
    }

    fun listenUserChats(userId: String, onResult: (List<ChatModel>) -> Unit): ListenerRegistration {
        return chatsCollection
            .whereArrayContains("participants", userId)
            .orderBy("lastTimestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val chats = snapshot?.toObjects(ChatModel::class.java) ?: emptyList()
                onResult(chats)
            }
    }
}