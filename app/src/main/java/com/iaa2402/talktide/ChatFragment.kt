package com.iaa2402.talktide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.iaa2402.talktide.databinding.FragmentChatBinding
import java.util.UUID


class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    lateinit var chatDb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        chatDb = FirebaseDatabase.getInstance().reference
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.sendBtn.setOnClickListener {

            var textMessage = TextMessage(text = binding.messageET.text.toString(), msgId = "", senderId = "", receiverId = "")

            sendMessage(textMessage)
        }

    }

    private fun sendMessage(textMessage: TextMessage) {
        var msgID = chatDb.push().key ?: UUID.randomUUID().toString()
        chatDb.child(DBNODES.CHAT).child(msgID).setValue(textMessage)

    }


}