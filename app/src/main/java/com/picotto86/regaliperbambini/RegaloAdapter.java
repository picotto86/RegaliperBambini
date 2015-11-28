package com.picotto86.regaliperbambini;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by picot on 24/11/2015.
 */
public class RegaloAdapter extends RecyclerView.Adapter<RegaloAdapter.RegaloViewHolder>
        implements View.OnClickListener, View.OnLongClickListener{

    ArrayList<Regalo> regaloList;

    public RegaloAdapter(ArrayList<Regalo> regaloList) {

        this.regaloList = regaloList;


    }

    @Override
    public RegaloViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);


        //Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext(), (R.anim.slide_up));
        //itemView.startAnimation(animation);

        return new RegaloViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RegaloViewHolder regaloViewHolder, int i) {

        regaloViewHolder.vName.setText(regaloList.get(i).nome);
        regaloViewHolder.vId.setText("N. "+regaloList.get(i).id);
    }

    @Override
    public int getItemCount() {
        return regaloList.size();    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public class RegaloViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName,vId;

        public RegaloViewHolder(View v) {
            super(v);
            vId=(TextView)v.findViewById(R.id.title);
            vName=(TextView)v.findViewById(R.id.textView3);
        }
    }
}
