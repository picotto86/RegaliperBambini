package com.picotto86.regaliperbambini;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by picot on 23/11/2015.
 */
public class RicercaPerEta extends android.support.v4.app.Fragment {


    View rootView;

    public RicercaPerEta() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        rootView=inflater.inflate(R.layout.fragment_riceta,container,false);

        return rootView;
    }
}
