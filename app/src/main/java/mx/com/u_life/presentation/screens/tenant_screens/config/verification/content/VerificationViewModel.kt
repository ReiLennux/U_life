package mx.com.u_life.presentation.screens.tenant_screens.config.verification.content


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel  @Inject constructor(

): ViewModel()  {


    private val _hasCameraPermission = MutableLiveData(false)
    val hasCameraPermission: LiveData<Boolean> = _hasCameraPermission

    private val _isVerifiedUser = MutableLiveData(false)
    val isVerifiedUser: LiveData<Boolean> = _isVerifiedUser

    init {
        checkVerificationStatus()
    }

    private fun checkVerificationStatus() {
        _isVerifiedUser.value = true
    }

    fun checkCameraPermission(context: Context) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        _hasCameraPermission.value = granted
    }

    fun updatePermissionResult(granted: Boolean) {
        _hasCameraPermission.value = granted
    }
}