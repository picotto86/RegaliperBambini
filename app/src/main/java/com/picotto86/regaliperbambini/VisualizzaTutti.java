package com.picotto86.regaliperbambini;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazon.webservices.awsecommerceservice._2011_08_01.Errors;
import com.amazon.webservices.awsecommerceservice._2011_08_01.Item;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearch;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchRequest;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.Items;
import com.amazon.webservices.awsecommerceservice._2011_08_01.client.AWSECommerceServicePortType_SOAPClient;
import com.leansoft.nano.ws.SOAPServiceCallback;

import org.apache.commons.io.IOUtils;
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
    int posizione;
    WebImageView imageView;
    Item item;

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


            ca = new RegaloAdapter(result);

            recList.setAdapter(ca);


            ca.notifyDataSetChanged();


            recList.addOnItemTouchListener(
                    new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click

                            Log.d("D;", "toccato " + result.get(position).nome);

                            posizione=position;

                            AWSECommerceServicePortType_SOAPClient client = AWSECommerceClient.getSharedClient();
                            client.setDebug(true);
                            ItemSearch request = new ItemSearch();
                            request.associateTag = "teg"; // seems any tag is ok
                            request.shared = new ItemSearchRequest();
                            request.shared.searchIndex = "Baby";
                            request.shared.responseGroup = new ArrayList<String>();
                            request.shared.responseGroup.add("Images");
                            request.shared.responseGroup.add("Medium");
                            ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
                            itemSearchRequest.title = (result.get(posizione).nome);
                            request.request = new ArrayList<ItemSearchRequest>();
                            request.request.add(itemSearchRequest);
                            AWSECommerceClient.authenticateRequest("ItemSearch");
                            client.itemSearch(request, new SOAPServiceCallback<ItemSearchResponse>() {

                                @Override
                                public void onSuccess(ItemSearchResponse responseObject) {
                                    // success handling logic
                                    if (responseObject.items != null && responseObject.items.size() > 0) {
                                        Items items = responseObject.items.get(0);
                                        if (items.item != null && items.item.size() > 0) {
                                            item = items.item.get(0);

                                            imageView.setImageUrl(item.mediumImage.url);
                                            imageView.loadImage();

                                            Toast.makeText(rootView.getContext(), item.itemAttributes.title, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(rootView.getContext(), "No result", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        if (responseObject.operationRequest != null && responseObject.operationRequest.errors != null) {
                                            Errors errors = responseObject.operationRequest.errors;
                                            if (errors.error != null && errors.error.size() > 0) {
                                                com.amazon.webservices.awsecommerceservice._2011_08_01.errors.Error error = errors.error.get(0);
                                                Toast.makeText(rootView.getContext(), error.message, Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(rootView.getContext(), "No result", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(rootView.getContext(), "No result", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Throwable error, String errorMessage) { // http or parsing error
                                    Toast.makeText(rootView.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onSOAPFault(Object soapFault) { // soap fault
                                    com.leansoft.nano.soap11.Fault fault = (com.leansoft.nano.soap11.Fault) soapFault;
                                    Toast.makeText(rootView.getContext(), fault.faultstring, Toast.LENGTH_LONG).show();
                                }

                            });

                            final Dialog dialog = new Dialog(view.getContext());

                            dialog.setContentView(R.layout.dialog);

                            Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                            buttonCancel.setText("Cancel");

                            imageView=(WebImageView)dialog.findViewById(R.id.imageView3);


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


                                    String url =item.detailPageURL;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);

                                    dialog.dismiss();

                                }
                            });
                            dialog.show();
                        }

                    }));


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
                response = IOUtils.toString(in, "UTF-8");
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
