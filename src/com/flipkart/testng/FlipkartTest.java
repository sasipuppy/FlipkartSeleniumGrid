package com.flipkart.testng;


import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FlipkartTest {
	
	public static WebDriver driver;
	String Node="http://192.168.1.80:4444/wd/hub";
	String Node1="http://192.168.56.101:4444/wd/hub";
	
	protected ThreadLocal<RemoteWebDriver> threadDriver=null;
	 
	 @BeforeClass(groups= {"Regression","Sanity"})
	 @Parameters("node")
	 public void setup(String node) throws MalformedURLException{
		 if(node.equalsIgnoreCase("local")) {
			 DesiredCapabilities capabilities =DesiredCapabilities.chrome(); 
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(Platform.WINDOWS);
				driver= new RemoteWebDriver(new URL(Node), capabilities);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();
		}
		 else if (node.equalsIgnoreCase("external")) {
			 DesiredCapabilities capabilities =DesiredCapabilities.firefox(); 
				capabilities.setBrowserName("firefox");
				capabilities.setPlatform(Platform.LINUX);
				driver= new RemoteWebDriver(new URL(Node1), capabilities);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();
		 }
	}
	 
	@Test(groups= {"Regression","Sanity"},priority=0)
	public void urlVerification(){
		driver.get("https://www.flipkart.com/");  
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Reporter.log("Launching the browser");
		String actualUrl= driver.getCurrentUrl();
		String expectedUrl="https://www.flipkart.com/";
		Assert.assertEquals(actualUrl, expectedUrl);
		Reporter.log("Verification of url");
	}
	
	@Test(groups="Regression",priority=1)
	public void modalAlertButtonVerification(){
		WebElement modalAlertBtn = driver.findElement(By.xpath("/html/body/div[2]/div/div/button"));
		modalAlertBtn.click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Reporter.log("Finding ModalAlert button and closing");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=2)
	public void hoverMenuElectronicsMiVerification(){
		WebElement electronics=driver.findElement(By.xpath("//span[contains(text(),'Electronics')]"));
		Actions action=new Actions(driver);//instantiating object of actions class
		action.moveToElement(electronics).build().perform();
		//The following piece of code works in selenium script but not with TestNG. So launching MI page with the link to proceed with the test.
				/*WebDriverWait wait = new WebDriverWait(driver, 40);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div/div[1]/a[2]")));
				WebElement MI= driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div/div[1]/a[2]"));
				action.moveToElement(MI).click().build().perform();
				MI.click();*/
		driver.get("https://www.flipkart.com/mobiles/mi~brand/pr?sid=tyy,4io&otracker=nmenu_sub_Electronics_0_Mi");
		Reporter.log("Hovering to Electronics>>Mi");
		}
	 
	
	@Test(groups="Regression",priority=3)
	public void labelVerification()
	 {
	  WebElement paragraph= driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[2]/div[1]/div[2]/div[1]/div/div/div[2]/div/p"));
	  String labelactual= paragraph.getText();
	  String labelExpected="Latest from MI : Redmi Go";
	  Assert.assertEquals(labelactual, labelExpected);
	  Reporter.log("Verifying the label Latest from MI : Redmi Go");
	 }
	
	@Test(groups="Regression",priority=4)
	public void sliderVerification() throws InterruptedException
	 {	
	  WebElement slider =driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[2]/div/div[1]/div/div[1]/div/section[2]/div[3]/div[1]/div[1]/div"));
	  Thread.sleep(1000);
	  Actions action=new Actions(driver);
	  action.dragAndDropBy(slider,100, 0).click().build().perform();
	  Reporter.log("sliding price slider");
	 }
	
	@Test(groups="Regression",priority=5)
	public void dropdownSelectionVerification() throws InterruptedException
	{
	  WebElement ele= driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div/div[1]/div/div[1]/div/section[2]/div[4]/div[3]/select"));
	  Thread.sleep(1000);
	  Select dropDownMax =new Select(ele);
	  dropDownMax.selectByValue("25000");
	  Reporter.log("Selecting third value from the drop down");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=6)
	public void searchVerification()
	{
	  WebElement search =driver.findElement(By.name("q"));
	  search.sendKeys("redmi go (black, 8 gb)"+ "\n");
	  Reporter.log("Selecting third value from the drop down");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=7)
	public void selectFirstElementVerification(){
	  WebElement firstProductLink= driver.findElement(By.xpath("//a[contains(@href, 'redmi-go-black-8')]"));
	  String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
	  firstProductLink.sendKeys(selectLinkOpeninNewTab);
	  Reporter.log("Selecting the first product from the search");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=8)
	public void switchToNewWindowVerification(){
		String parent_handle= driver.getWindowHandle();
		   Set<String> handles = driver.getWindowHandles();
		   for(String handle1:handles)
		       if(!parent_handle.equals(handle1))
		       {
		           driver.switchTo().window(handle1);
		       }
		Reporter.log("Switching to a new window");
	}
	
	@Test(groups="Regression",priority=9)
	public void priceVerification() {
		WebElement priceElement = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[2]/div/div[4]/div[1]/div/div[1]"));
		String priceText= priceElement.getText();
		String[] price= priceText.split("\u20B9");
		String price1=price[1];
		System.out.println(price1);
		String[] priceNew= price1.split(",");
		String priceactual=priceNew[0]+priceNew[1];
		int newPrice=Integer.parseInt(priceactual);
		assertTrue(newPrice>=0);
		Reporter.log("Finding the price and verification of price for greater than or equal to zero condition");
	}
	
	@Test(groups="Regression",priority=10)
	public void videoThumbnailVerification() throws InterruptedException {
		WebElement item=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[1]/div/div[1]/div[1]/div/div[1]/ul/li[2]/div/div[1]"));
		Actions action=new Actions(driver);
		action.moveToElement(item).click().build().perform();
		Thread.sleep(1000);
		WebElement videoIframe=driver.findElement(By.xpath("//iframe[@src='https://www.youtube.com/embed/Lvvw23qlU-Q?rel=0']"));
		driver.switchTo().frame(videoIframe);
		Reporter.log("Hovering and selecting thumbnail that displays video");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=11)
	public void keyboardEventVerification() throws InterruptedException {
		String link2= "https://www.flipkart.com/samsung-m31-prime-ocean-blue-128-gb/p/itm7c94a171225f2?pid=MOBFWVNZNW8D8BJK&lid=LSTMOBFWVNZNW8D8BJKQEEEM9&marketplace=FLIPKART&fm=neo%2Fmerchandising&iid=M_773e4f37-3d58-4e6c-bb46-22f9fc291342_1_1BUWY8OBA8L9_MC.MOBFWVNZNW8D8BJK&ppt=clp&ppn=samsung-mobile-store&ssid=3qa5phk4ww0000001609939606772&otracker=clp_pmu_v2_Latest%2BSamsung%2Bmobiles%2B_1_1.productCard.PMU_V2_Samsung%2BM31%2BPrime%2B%2528Ocean%2BBlue%252C%2B128%2BGB%2529_samsung-mobile-store_MOBFWVNZNW8D8BJK_neo%2Fmerchandising_0&otracker1=clp_pmu_v2_PINNED_neo%2Fmerchandising_Latest%2BSamsung%2Bmobiles%2B_LIST_productCard_cc_1_NA_view-all&cid=MOBFWVNZNW8D8BJK";
	    driver.get(link2);
	    WebElement inputText= driver.findElement(By.id("pincodeInputId"));
	    inputText.sendKeys(Keys.CONTROL);
	    inputText.sendKeys("636007");
	    inputText.sendKeys(Keys.ENTER);
	    Thread.sleep(1000);
	    Reporter.log("Entering pincode");
	}
	
	@Test(groups="Regression",priority=12)
	public void viewDetailsVerification() throws InterruptedException
	{
		WebElement viewDetails= driver.findElement(By.xpath("//span[contains(text(),'View Details')]"));
	    viewDetails.click();
	    Thread.sleep(1000);
	    WebElement ModalButton = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/button"));
	    ModalButton.click();
	    Reporter.log("Clicking on view details and closing it.");
	}
	
	@Test(groups= {"Regression","Sanity"},priority=13)
	public void addToCartVerification() {
		WebElement AddToCart =driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[2]/div/ul/li[1]/button"));
	    AddToCart.click();
	    Reporter.log("Adding the product to the cart");
	}
	
	@AfterSuite(groups="Regression")
	public void closeBrowser(){
		 driver.quit();
		 Reporter.log("Closing the driver");
	 }

}

