package com.iaa2402.talktide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iaa2402.talktide.databinding.FragmentChatBinding
import java.util.UUID


class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    lateinit var chatDb: DatabaseReference
    lateinit var userIdSelf: String
    lateinit var userIdRemote: String

    val chatList = mutableListOf<TextMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {



        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        chatDb = FirebaseDatabase.getInstance().reference



        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true

        binding.messageRCV.layoutManager = layoutManager


        requireArguments().getString(USERID)?.let {
            userIdRemote = it
        }

        FirebaseAuth.getInstance().currentUser?.let {

            userIdSelf = it.uid
        }

        chatDb.child(DBNODES.USER).child(userIdRemote)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let {

                        binding.apply {

                            Glide.with(requireContext()).load(it.profilePicture)
                                .placeholder(R.drawable.placeholder).into(profileImage)
                            profileName.text = it.fullName
                            emailName.text = it.email


                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })

        messageToShow()

        return binding.root
    }

    private fun messageToShow() {
        chatDb.child(DBNODES.CHAT).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()

                snapshot.children.forEach { snp ->
                    snp.getValue(TextMessage::class.java)?.let {
                        if ((it.senderId == userIdSelf && it.receiverId == userIdRemote) ||
                            (it.senderId == userIdRemote && it.receiverId == userIdSelf)) {
                            chatList.add(it)
                        }
                    }
                }

                // Create a new adapter with the updated chat list
                val adapter = ChatAdapter(userIdSelf,chatList)


                binding.messageRCV.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error (log it or show a Toast)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.sendBtn.setOnClickListener {

            var textMessage = TextMessage(
                text = binding.messageET.text.toString(),
                msgId = "",
                senderId = userIdSelf,
                receiverId = userIdRemote
            )

            sendMessage(textMessage)
        }

    }

    companion object {
        private var USERID = "id"
    }

    private fun sendMessage(textMessage: TextMessage) {
        var msgID = chatDb.push().key ?: UUID.randomUUID().toString()

        textMessage.msgId = msgID

        chatDb.child(DBNODES.CHAT).child(msgID).setValue(textMessage).addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Message Sent Successful", Toast.LENGTH_SHORT)
                    .show()
                binding.messageET.setText("")
            } else {
                Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_SHORT)
                    .show()
            }

        }
 
    }


}