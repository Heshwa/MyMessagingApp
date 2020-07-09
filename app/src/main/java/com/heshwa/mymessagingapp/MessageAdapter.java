package com.heshwa.mymessagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> messsages;

    public MessageAdapter(Context context, ArrayList<String> messsages) {
        mContext = context;
        this.messsages = messsages;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.message_item,parent,false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        int pos=MessageActivity.messagePosition.get(position);
        if(pos==0)
        {
            holder.txtleft.setVisibility(View.VISIBLE);
            holder.txtleft.setText(messsages.get(position));
            holder.txtRight.setVisibility(View.INVISIBLE);
        }
        else {
            holder.txtRight.setVisibility(View.VISIBLE);
            holder.txtRight.setText(messsages.get(position));
            holder.txtleft.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return messsages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtleft,txtRight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtleft = itemView.findViewById(R.id.txtLeft);
            txtRight = itemView.findViewById(R.id.txtRight);
        }
    }
}
