package com.commondnd

import androidx.lifecycle.ViewModel
import com.commondnd.data.user.User
import com.commondnd.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user: Flow<User?>
        get() = userRepository.monitorUser()
}
