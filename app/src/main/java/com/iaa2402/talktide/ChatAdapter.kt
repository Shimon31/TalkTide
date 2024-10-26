package com.iaa2402.talktide

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.runtime.mutableStateListOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class ChatAdapter(var userIdSelf: String, private val chatList: MutableList<TextMessage>) :
    RecyclerView.Adapter<ChatAdapter.chatViewHolder>() {


    private val Left: Int = 1
    private val Right: Int = 2


    class chatViewHolder(var ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var messageTV: TextView = itemView.findViewById(R.id.chatTV)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {

        if (viewType == Right) {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.send_mesage_item, parent, false)
            return chatViewHolder(view)
        } else {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.receive_mesage_item, parent, false)
            return chatViewHolder(view)


        }

    }

    override fun getItemCount(): Int {
        return chatList.size // Ensure this returns 0 when the list is empty
    }

    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        val message = chatList[position]
        holder.messageTV.text = message.text
    }

    override fun getItemViewType(position: Int): Int {

        return if (chatList[position].senderId == userIdSelf) {

            Right
        } else {

            Left

        }

    }
}
