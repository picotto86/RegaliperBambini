package com.picotto86.regaliperbambini;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by picot on 24/11/2015.
 */
public class RicercaPerProblema extends android.support.v4.app.Fragment {

    View rootView;

    public RicercaPerProblema() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        rootView=inflater.inflate(R.layout.fragment_ricprobl,container,false);

        return rootView;
    }
}
