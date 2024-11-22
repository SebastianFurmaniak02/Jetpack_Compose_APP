package com.example.jetpack_compose_app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DialogInfo(
    var isDialog: Boolean = false,
    var dialogNumber: Int = 0
)
data class FormScreenInfo(
    var firstName : String = "",
    var lastName : String = "",
    var email : String = "",
    var selectedIndex : Int = 0,
    var studentStatus: Boolean = false,
    var skillLevel: Int = 3,
    var dialogInfo: DialogInfo = DialogInfo()
)
class FormViewModel : ViewModel() {

    private val _formScreenInfo = MutableStateFlow(FormScreenInfo())
    val formScreenInfo: StateFlow<FormScreenInfo> get() = _formScreenInfo

    fun updateFirstName(firstName: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(lastName = lastName)
    }

    fun updateEmail(email: String) {
        _formScreenInfo.value = _formScreenInfo.value.copy(email = email)
    }

    fun updateGender(index: Int) {
        _formScreenInfo.value = _formScreenInfo.value.copy(selectedIndex = index)
    }

    fun toggleStudentStatus(isStudent: Boolean) {
        _formScreenInfo.value = _formScreenInfo.value.copy(studentStatus = isStudent)
    }

    fun updateSkillLevel(level: Int) {
        _formScreenInfo.value = _formScreenInfo.value.copy(skillLevel = level)
    }

    fun updateDialog(dialog: DialogInfo) {
        _formScreenInfo.value = _formScreenInfo.value.copy(dialogInfo = dialog)
    }
}