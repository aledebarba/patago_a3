package com.alemacedo.patago;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<DBRotinas> dbrot;
    private String cardViewColor = "#FFFFFF";
    public DataAdapter (ArrayList<DBRotinas> dbrot) {
        this.dbrot = dbrot;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_title.setText(dbrot.get(i).getTitle());
        viewHolder.tv_subtitle.setText(dbrot.get(i).getSubtitle());
        viewHolder.mCardView.setCardBackgroundColor(Color.parseColor(dbrot.get(i).getColor()));

    }

    @Override
    public int getItemCount() {
        return dbrot.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_subtitle;
        private TextView tv_color;
        private CardView mCardView;

        public ViewHolder(View view) {
            super(view);

            //cria botões sobre cada cardViews
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {

                    Intent intent = new Intent(view1.getContext(), rotina.class);
                    int i = getAdapterPosition();
                    String extra = dbrot.get(i).getUid().toString();
                    intent.putExtra("position",extra);
                    view1.getContext().startActivity(intent);

                }
            });

            mCardView = (CardView)view.findViewById(R.id.card);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_subtitle = (TextView)view.findViewById(R.id.tv_subtitle);

        }
    }
}