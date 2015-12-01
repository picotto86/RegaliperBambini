// Generated by xsd compiler for android/java
// DO NOT CHANGE!
package com.amazon.webservices.awsecommerceservice._2011_08_01;

import java.io.Serializable;
import com.leansoft.nano.annotation.*;


@com.leansoft.nano.annotation.RootElement(name = "OfferSummary", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
public class OfferSummary implements Serializable {

    private static final long serialVersionUID = -1L;

	@Element(name = "LowestNewPrice")    
	@Order(value=0)
	public Price lowestNewPrice;	
	
	@Element(name = "LowestUsedPrice")    
	@Order(value=1)
	public Price lowestUsedPrice;	
	
	@Element(name = "LowestCollectiblePrice")    
	@Order(value=2)
	public Price lowestCollectiblePrice;	
	
	@Element(name = "LowestRefurbishedPrice")    
	@Order(value=3)
	public Price lowestRefurbishedPrice;	
	
	@Element(name = "TotalNew")    
	@Order(value=4)
	public String totalNew;	
	
	@Element(name = "TotalUsed")    
	@Order(value=5)
	public String totalUsed;	
	
	@Element(name = "TotalCollectible")    
	@Order(value=6)
	public String totalCollectible;	
	
	@Element(name = "TotalRefurbished")    
	@Order(value=7)
	public String totalRefurbished;	
	
    
}