// Generated by xsd compiler for android/java
// DO NOT CHANGE!
package com.amazon.webservices.awsecommerceservice._2011_08_01;

import java.io.Serializable;
import com.leansoft.nano.annotation.*;
import java.util.List;
import com.amazon.webservices.awsecommerceservice._2011_08_01.browsenode.Ancestors;
import com.amazon.webservices.awsecommerceservice._2011_08_01.browsenode.Children;
import com.amazon.webservices.awsecommerceservice._2011_08_01.browsenode.Properties;


@com.leansoft.nano.annotation.RootElement(name = "BrowseNode", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
public class BrowseNode implements Serializable {

    private static final long serialVersionUID = -1L;

	@Element(name = "BrowseNodeId")    
	@Order(value=0)
	public String browseNodeId;	
	
	@Element(name = "Name")    
	@Order(value=1)
	public String name;	
	
	@Element(name = "IsCategoryRoot")    
	@Order(value=2)
	public Boolean isCategoryRoot;	
	
	@Element(name = "Properties")    
	@Order(value=3)
	public Properties properties;	
	
	@Element(name = "Children")    
	@Order(value=4)
	public Children children;	
	
	@Element(name = "Ancestors")    
	@Order(value=5)
	public Ancestors ancestors;	
	
	@Element(name = "TopSellers")    
	@Order(value=6)
	public TopSellers topSellers;	
	
	@Element(name = "NewReleases")    
	@Order(value=7)
	public NewReleases newReleases;	
	
	@Element(name = "TopItemSet")    
	@Order(value=8)
	public List<TopItemSet> topItemSet;	
	
    
}