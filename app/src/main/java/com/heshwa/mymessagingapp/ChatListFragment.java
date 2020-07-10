package com.heshwa.mymessagingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListFragment extends Fragment {
    private RecyclerView recycularViewChatList;
    private ArrayList<String> namesList;
    private  ArrayList<String> lastSeenList;
    private ChatListAdapter mChatListAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    public  ArrayList<String> ids;




    public ChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recycularViewChatList = view.findViewById(R.id.recycularViewChatList);
        namesList = new ArrayList<>();
        lastSeenList = new ArrayList<>();
        ids = new ArrayList<>();
        mChatListAdapter = new ChatListAdapter(getContext(),namesList,lastSeenList,ids);
        recycularViewChatList.setAdapter(mChatListAdapter);
        recycularViewChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth= FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");


        userRef.child(mAuth.getCurrentUser().getUid()).child("ChatLists")
                .orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String usernameid = snapshot.getKey();

                    userRef.child(usernameid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists() && snapshot.hasChild("Name"))
                            {
                                ids.add(0,snapshot.getKey());
                                namesList.add(0,snapshot.child("Name").getValue().toString());
                                if(snapshot.child("Status").hasChild("Online"))
                                {
                                    lastSeenList.add(0,"Online");
                                }
                                else
                                {
                                    lastSeenList.add(0,"Offline");
                                }
                                mChatListAdapter.notifyItemInserted(0);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    final String usernameid = snapshot.getKey();
                    userRef.child(usernameid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists() && snapshot.hasChild("Name"))
                            {
                                int index =ids.indexOf(usernameid);
                                namesList.remove(index);
                                lastSeenList.remove(index);
                                ids.remove(index);
                                mChatListAdapter.notifyItemRemoved(index);
                                ids.add(0,usernameid);
                                namesList.add(0,snapshot.child("Name").getValue().toString());

                                if(snapshot.child("Status").hasChild("Online"))
                                {
                                    lastSeenList.add(0,"Online");
                                }
                                else
                                {
                                    lastSeenList.add(0,"Offline");
                                }
                                mChatListAdapter.notifyDataSetChanged();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return  view;
    }
}