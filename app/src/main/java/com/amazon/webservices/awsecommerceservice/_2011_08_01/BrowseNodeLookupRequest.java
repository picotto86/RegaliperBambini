// Generated by xsd compiler for android/java
// DO NOT CHANGE!
package com.amazon.webservices.awsecommerceservice._2011_08_01;

import java.io.Serializable;
import com.leansoft.nano.annotation.*;
import java.util.List;


public class BrowseNodeLookupRequest implements Serializable {

    private static final long serialVersionUID = -1L;

	@Element(name = "BrowseNodeId")    
	@Order(value=0)
	public List<String> browseNodeId;	
	
	@Element(name = "ResponseGroup")    
	@Order(value=1)
	public List<String> responseGroup;	
	
    
}