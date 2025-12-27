package com.commondnd.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String,
    @SerialName("global_name")
    val globalName: String,
    @SerialName("avatar")
    val avatar: String
)
