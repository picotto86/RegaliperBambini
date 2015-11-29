package com.picotto86.regaliperbambini;

/**
 * Created by picot on 29/11/2015.
 */
public class AwsCaller {

    private static String INDEX = "Books";

    //Chiavi di accesso
    private String AWSAccessKey;
    private String AWSAssociateTag;

    /*
     * Bridge ai servizi SOAP, attraverso tali classi avremo accesso a tutte
     * le funzioni di Amazon Product API
     */
    AWSECommerceService service;
    AWSECommerceServicePortType port;

    //Costruttore
    public AWSCaller(String AWSAccessKey, String AWSSecretKey, String AWSAssociateTag) {
        this.AWSAccessKey = AWSAccessKey;
        this.AWSAssociateTag = AWSAssociateTag;

        /*
         * Inizializziamo il sistema: per poter funzionare dobbiamo associare
         * l'handler ad-hoc creato in precedenza
         */
        service = new AWSECommerceService();
        service.setHandlerResolver(new AwsHandlerResolver(AWSSecretKey));
        port = service.getAWSECommerceServicePort();

    }

}
