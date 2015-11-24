package com.picotto86.regaliperbambini;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by picot on 24/11/2015.
 */
public class RegaloAdapter extends RecyclerView.Adapter<RegaloAdapter.RegaloViewHolder> {

    List<Regalo> regaloList;

    public RegaloAdapter(List<Regalo> regaloList) {

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

        int new_index=regaloList.size()-1-i;

        Regalo regalo=regaloList.get(new_index);



        Button buttontemp;



        regaloViewHolder.vTitle.setText("Regalo n. "+regaloList.get(new_index).nome);

    }

    @Override
    public int getItemCount() {
        return regaloList.size();    }

    public class RegaloViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;

        public RegaloViewHolder(View v) {
            super(v);

            vTitle=(TextView)v.findViewById(R.id.textView3);
        }
    }
}
