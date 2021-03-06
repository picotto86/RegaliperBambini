// Generated by wsdl compiler for android/java
// DO NOT CHANGE!
package com.amazon.webservices.awsecommerceservice._2011_08_01.client;


import com.leansoft.nano.ws.SOAPServiceCallback;
import com.leansoft.nano.ws.NanoSOAPClient;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartGetResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartCreateResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearch;
import com.amazon.webservices.awsecommerceservice._2011_08_01.BrowseNodeLookup;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartAdd;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartClearResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.SimilarityLookup;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartAddResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartModify;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartCreate;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartClear;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemLookup;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartGet;
import com.amazon.webservices.awsecommerceservice._2011_08_01.BrowseNodeLookupResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemSearchResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.CartModifyResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.ItemLookupResponse;
import com.amazon.webservices.awsecommerceservice._2011_08_01.SimilarityLookupResponse;


/**
 This class is the SOAP client to the AWSECommerceServicePortType Web Service.
*/ 
public class AWSECommerceServicePortType_SOAPClient extends NanoSOAPClient {


    /**
     public method
    */
    public void itemSearch(ItemSearch requestObject, SOAPServiceCallback<ItemSearchResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/ItemSearch"); 
        
        super.invoke(requestObject, serviceCallback, ItemSearchResponse.class);
    }

    /**
     public method
    */
    public void itemLookup(ItemLookup requestObject, SOAPServiceCallback<ItemLookupResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/ItemLookup"); 
        
        super.invoke(requestObject, serviceCallback, ItemLookupResponse.class);
    }

    /**
     public method
    */
    public void browseNodeLookup(BrowseNodeLookup requestObject, SOAPServiceCallback<BrowseNodeLookupResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/BrowseNodeLookup"); 
        
        super.invoke(requestObject, serviceCallback, BrowseNodeLookupResponse.class);
    }

    /**
     public method
    */
    public void similarityLookup(SimilarityLookup requestObject, SOAPServiceCallback<SimilarityLookupResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/SimilarityLookup"); 
        
        super.invoke(requestObject, serviceCallback, SimilarityLookupResponse.class);
    }

    /**
     public method
    */
    public void cartGet(CartGet requestObject, SOAPServiceCallback<CartGetResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/CartGet"); 
        
        super.invoke(requestObject, serviceCallback, CartGetResponse.class);
    }

    /**
     public method
    */
    public void cartCreate(CartCreate requestObject, SOAPServiceCallback<CartCreateResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/CartCreate"); 
        
        super.invoke(requestObject, serviceCallback, CartCreateResponse.class);
    }

    /**
     public method
    */
    public void cartAdd(CartAdd requestObject, SOAPServiceCallback<CartAddResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/CartAdd"); 
        
        super.invoke(requestObject, serviceCallback, CartAddResponse.class);
    }

    /**
     public method
    */
    public void cartModify(CartModify requestObject, SOAPServiceCallback<CartModifyResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/CartModify"); 
        
        super.invoke(requestObject, serviceCallback, CartModifyResponse.class);
    }

    /**
     public method
    */
    public void cartClear(CartClear requestObject, SOAPServiceCallback<CartClearResponse> serviceCallback) {
       
        super.getAsyncHttpClient().addHeader("SOAPAction", "http://soap.amazon.com/CartClear"); 
        
        super.invoke(requestObject, serviceCallback, CartClearResponse.class);
    }


}