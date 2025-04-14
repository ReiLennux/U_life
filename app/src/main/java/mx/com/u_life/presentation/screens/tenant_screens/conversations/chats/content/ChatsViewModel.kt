package mx.com.u_life.presentation.screens.tenant_screens.conversations.chats.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.com.u_life.core.constants.Constants
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.chats.ChatModel
import mx.com.u_life.domain.useCases.chat.ChatUseCases
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val _chatUseCases: ChatUseCases,
    private val _dataStoreUseCases: DataStoreUseCases,
): ViewModel() {
    private val _userChats = MutableLiveData<List<ChatModel>>()
    val userChats: LiveData<List<ChatModel>> = _userChats

    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    init {
        viewModelScope.launch {
            getUserChats()
        }
    }

    suspend fun getUserChats() {
        _isLoading.value = Response.Loading
        val userId = _dataStoreUseCases.getDataString(Constants.USER_UID)
        _chatUseCases.listenForUserChats(
            userId = userId,
            onResult = { chats ->
                _userChats.postValue(chats)
                _isLoading.value = Response.Success(true)
            }
        )
    }
}