package com.picotto86.regaliperbambini;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by picot on 24/11/2015.
 */
public class VisualizzaTutti extends android.support.v4.app.Fragment {

    List<Regalo> result =new ArrayList<Regalo>();
    RegaloAdapter ca;
    String data_corrente;
    RecyclerView recList;


    View rootView;

    public VisualizzaTutti() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        rootView=inflater.inflate(R.layout.fragment_viewall,container,false);

        recList = (RecyclerView) rootView.findViewById(R.id.regList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

       
        createList();
        

        ca = new RegaloAdapter(result);
        recList.setAdapter(ca);

        return rootView;
    }

    private void createList() {

        new DownloadRis().execute();

    }


    private class DownloadRis extends AsyncTask<Void, Void, List<Regalo>> {

        public DownloadRis(){

            result=new ArrayList<Regalo>();

        }

        @Override
        protected List<Regalo> doInBackground(Void... params) {


            return result;
        }
    }
}
