package com.app.webdriver.common.template;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.app.webdriver.common.core.APPWebDriver;
import com.app.webdriver.common.core.CommonUtils;
import com.app.webdriver.common.core.TestContext;
import com.app.webdriver.common.core.annotations.Execute;
import com.app.webdriver.common.core.annotations.InBrowser;
import com.app.webdriver.common.core.configuration.Configuration;
import com.app.webdriver.common.driverprovider.DriverProvider;
import com.app.webdriver.common.logging.Log;
import com.app.webdriver.common.testnglisteners.BrowserAndTestEventListener;
import com.app.webdriver.common.testnglisteners.InvokeMethodAdapter;


@Listeners({BrowserAndTestEventListener.class,
	InvokeMethodAdapter.class})
public class TestTemplate {

	protected APPWebDriver driver;

	private void refreshDriver() {
		driver = DriverProvider.getActiveDriver();
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		prepareDirectories();
	}

	@BeforeMethod(alwaysRun = true)
	public void initTestContext(Method method) {
		DriverProvider.ACTIVE_BROWSER_INDEX.set(0);
		TestContext.writeMethodName(method);
		Log.startTest(method);

		Configuration.clearCustomTestProperties();
		String browser = Configuration.getBrowser();
		setPropertiesFromAnnotationsOnDeclaringClass(method.getDeclaringClass());
		setPropertiesFromAnnotationsOnMethod(method);
		String currentBrowser = Configuration.getBrowser();

		if (!browser.equals(currentBrowser)) {
			Log.warning("Parameter override",
					"Browser parameter changed by annotation" + ", old value: " + browser
					+ ", new value: " + currentBrowser
					);
		}

		//prepareURLs();

		driver = DriverProvider.getActiveDriver();
		setWindowSize();

		loadFirstPage();
	}

	private void setTestProperty(String key, String value) {
		if (!"".equals(value)) {
			Configuration.setTestValue(key, value);
		}
	}

	private void setPropertiesFromAnnotationsOnDeclaringClass(Class<?> declaringClass) {
		if (declaringClass.isAnnotationPresent(Execute.class)) {
			setTestProperty("wikiName", declaringClass.getAnnotation(Execute.class).onWikia());
			setTestProperty("language", declaringClass.getAnnotation(Execute.class).language());
			setTestProperty("disableFlash", declaringClass.getAnnotation(Execute.class).disableFlash());
			setTestProperty("mockAds", declaringClass.getAnnotation(Execute.class).mockAds());
			setTestProperty("disableCommunityPageSalesPitchDialog",
					declaringClass.getAnnotation(Execute.class)
					.disableCommunityPageSalesPitchDialog()
					);
		}

		if (declaringClass.isAnnotationPresent(InBrowser.class)) {
			setTestProperty("browser", declaringClass.getAnnotation(InBrowser.class).browser().getName());
			setTestProperty("browserSize", declaringClass.getAnnotation(InBrowser.class).browserSize());
		}
	}

	private void setPropertiesFromAnnotationsOnMethod(Method method) {
		if (method.isAnnotationPresent(Execute.class)) {
			setTestProperty("wikiName", method.getAnnotation(Execute.class).onWikia());
			setTestProperty("language", method.getAnnotation(Execute.class).language());
			setTestProperty("disableFlash", method.getAnnotation(Execute.class).disableFlash());
			setTestProperty("mockAds", method.getAnnotation(Execute.class).mockAds());
			setTestProperty("disableCommunityPageSalesPitchDialog",
					method.getAnnotation(Execute.class).disableCommunityPageSalesPitchDialog()
					);
		}

		if (method.isAnnotationPresent(InBrowser.class)) {
			setTestProperty("browser", method.getAnnotation(InBrowser.class).browser().getName());
			setTestProperty("browserSize", method.getAnnotation(InBrowser.class).browserSize());
		}
	}



	private void prepareDirectories() {
		CommonUtils.deleteDirectory("." + File.separator + "logs");
		CommonUtils.createDirectory("." + File.separator + "logs");
	}

	protected void setWindowSize() {
		Dimension browserSize = Configuration.getBrowserSize();


		if (browserSize != null) {
			driver.manage().window().setSize(browserSize);
		} else {
			
			//driver.manage().window().setSize(new Dimension(1600,900));
		}
	}


	//@AfterMethod(alwaysRun = true)
	@AfterMethod(alwaysRun = true)
	public void stop() {
		DriverProvider.close();
	}

	protected void switchToWindow(int index) {
		DriverProvider.switchActiveWindow(index);
		refreshDriver();

		String driverName = DriverProvider.getActiveDriver().equals(driver) ? "primary window"
				: "secondary window";
		Log.log("switchToWindow", "================ " + driverName + " ================", true);
	}

	protected void prepareURLs() {

	}

	protected void loadFirstPage()
	{
		driver.get(Configuration.getUrl());
		waitForPageLoaded();
	}
	
	public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
        	 }
    }
}

