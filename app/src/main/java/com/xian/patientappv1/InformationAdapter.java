package com.xian.patientappv1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by PTTsiuoLIVIA on 3/19/16.
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    private Context context;

    public InformationAdapter(Context context, List<Information> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information info = data.get(position);
        holder.setPosition(position);
        holder.textView.setText(info.getTitle());
        holder.iconView.setImageResource(info.getIconId());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView iconView;
        int position;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_text);
            iconView = (ImageView) itemView.findViewById(R.id.list_icon);
            position = 0;
            itemView.setOnClickListener(this);
        }


        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            if(position == 0)
            {
                context.startActivity(new Intent(context, ProfileActivity.class));
            }
            else if(position == 1)
            {
                context.startActivity(new Intent(context, AppointmentActivity.class));
            }
            else if(position == 2)
            {
                context.startActivity(new Intent(context, CommentsActivity.class));
            }
            else if(position == 3)
            {
                context.startActivity(new Intent(context, FavoriteActivity.class));
            }
            else if(position == 4)
            {
                context.startActivity(new Intent(context, InfoActivity.class));
            }


        }
    }



}
