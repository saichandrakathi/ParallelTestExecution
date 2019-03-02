package com.app.webdriver.testcases.cmp;

import org.testng.annotations.Test;

import com.app.webdriver.common.template.TestTemplate;
import com.app.webdriver.pageobjectsfactory.pageobject.authentication.LoginPage;

public class Test1 extends TestTemplate{
	
	
	@Test
	public void login() {
		System.out.println("method 1"+Thread.currentThread().getId());
		LoginPage login = new LoginPage();
		login.login("hcm_user2", "Welcome1");
	}
	
	@Test
	public void login2() {
		System.out.println("method 2"+Thread.currentThread().getId());
		LoginPage login = new LoginPage();
		login.login("mark.cmpmanager@oracle.com", "Welcome1");
	}

}
