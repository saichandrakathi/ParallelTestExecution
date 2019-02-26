package com.app.webdriver.common.core.purge;

import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;


public class PurgeMethod extends HttpRequestBase {

	  public PurgeMethod(final String uri) {
	    super();
	    setURI(URI.create(uri));
	  }

	  @Override
	  public String getMethod() {
	    return "PURGE";
	  }
	}


