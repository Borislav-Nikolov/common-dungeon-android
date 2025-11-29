package com.commondnd.ui.more

import androidx.lifecycle.ViewModel
import com.commondnd.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun logout() {
        userRepository.logout()
    }
}
