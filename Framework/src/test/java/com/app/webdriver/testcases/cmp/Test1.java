package com.app.webdriver.testcases.cmp;

import org.testng.annotations.Test;

import com.app.webdriver.common.template.TestTemplate;
import com.app.webdriver.pageobjectsfactory.pageobject.authentication.LoginPage;

public class Test1 extends TestTemplate{
	
	
	@Test
	public void login() {
		LoginPage login = new LoginPage();
		login.login("hcm_user2", "Welcome1");
	}
	
	

}
