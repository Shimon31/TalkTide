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

class ChatAdapter(var userIdSelf: String) :
    ListAdapter<TextMessage, ChatAdapter.chatViewHolder>(comparator) {


    val left: Int = 1
    val Right: Int = 2

    val chatList = mutableStateListOf<TextMessage>()

    class chatViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView){

         var messageTV:TextView = itemView.findViewById(R.id.chatTV)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {

        if (viewType==Right){
            var view = LayoutInflater.from(parent.context).inflate(R.layout.send_mesage_item,parent,false)
            return chatViewHolder(view)
        }else{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.receive_mesage_item,parent,false)
            return chatViewHolder(view)


        }

    }

    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        getItem(position).apply {

            chatList.add(this)
            holder.messageTV.text = this.text
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (chatList[position].senderId == userIdSelf) {

            return Right
        } else {
            return left
        }

    }

    companion object {

        val comparator = object : DiffUtil.ItemCallback<TextMessage>() {
            override fun areItemsTheSame(oldItem: TextMessage, newItem: TextMessage): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TextMessage, newItem: TextMessage): Boolean {
                return oldItem.msgId == newItem.msgId
            }

        }

    }

}