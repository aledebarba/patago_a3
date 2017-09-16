package com.alemacedo.patago;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alexandre on 14/09/2017.
 */

public class DBRotinasAdapter extends RecyclerView.Adapter<DBRotinasAdapter.ViewHolder> {

    private ArrayList<DBRotinas> rotinas;
    public DBRotinasAdapter (ArrayList<DBRotinas> rotinas) {
        this.rotinas = rotinas;
    }

    @Override
    public DBRotinasAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DBRotinasAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_title.setText(rotinas.get(i).getTitle());
        viewHolder.tv_subtitle.setText(rotinas.get(i).getSubtitle());

    }

    @Override
    public int getItemCount() {
        return rotinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title, tv_subtitle;
        public ViewHolder(View view) {
            super(view);
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_subtitle = (TextView)view.findViewById(R.id.tv_subtitle);
        }
    }

}
