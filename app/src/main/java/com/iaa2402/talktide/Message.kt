package com.iaa2402.talktide

interface Message {

    val msgId: String
    val senderId: String
    val receiverId: String


}

data class TextMessage(
    val text :String? = null,
    override val msgId: String,
    override val senderId: String,
    override val receiverId: String
) : Message

data class MessagesWithImage(

    val imageLink: String = "",
    override val senderId: String = "",
    override val msgId: String = "",
    override val receiverId: String = "",

    ):Message
