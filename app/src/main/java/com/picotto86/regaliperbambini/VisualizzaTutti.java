package com.picotto86.regaliperbambini;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by picot on 24/11/2015.
 */
public class VisualizzaTutti extends android.support.v4.app.Fragment {

    ArrayList<Regalo> result;
    RegaloAdapter ca;
    RecyclerView recList;
    LinearLayoutManager llm;


    View rootView;

    public VisualizzaTutti() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        rootView = inflater.inflate(R.layout.fragment_viewall, container, false);

        recList = (RecyclerView) rootView.findViewById(R.id.regList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

       
        createList();

        return rootView;
    }

    private void createList() {

       new DownloadRis().execute();

    }


    private class DownloadRis extends AsyncTask<Void, Void, List<Regalo>> {

        public DownloadRis(){

            result=new ArrayList<>();

        }

        protected void onPostExecute(List<Regalo> list) {


            ca=new RegaloAdapter(result);

            recList.setAdapter(ca);


            ca.notifyDataSetChanged();



            recList.addOnItemTouchListener(
                    new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // TODO Handle item click

                            Log.d("D;","toccato "+result.get(position).nome);

                            final Dialog dialog = new Dialog(view.getContext());

                            dialog.setContentView(R.layout.dialog);

                            Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);

                            buttonCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            Button buttonOk = (Button) dialog.findViewById(R.id.buttonOk);
                            buttonOk.setText(result.get(position).nome);

                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();

                                    //intent



                                }
                            });

                            dialog.show();



                        }
                    })
            );


        }

        @Override
        protected List<Regalo> doInBackground(Void... params) {

            URL url = null;
            try {
                url = new URL("http://picotto86.ilbello.com/viewall.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

// read the response
            try {
                System.out.println("Response Code: " + conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            try {
                in = new BufferedInputStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = null;
            try {
                response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }


            JSONObject  jsonRootObject = null;
            try {
                jsonRootObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("giocattoli");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String id = jsonObject.optString("Id").toString();

                String name = jsonObject.optString("Nome").toString();
                Regalo r=new Regalo(id,name);
                result.add(r);

            }


            return result;
        }
    }
}
