package com.heshwa.mymessagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> names;
    private ArrayList<String> lastseen;

    public ChatListAdapter(Context context, ArrayList<String> names, ArrayList<String> lastseen) {
        mContext = context;
        this.names = names;
        this.lastseen = lastseen;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(names.get(position));
        holder.txtLastSeen.setText(lastseen.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtLastSeen,txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLastSeen = itemView.findViewById(R.id.txtLastSeen);
            txtName = itemView.findViewById(R.id.txtUserName);
        }
    }
}
