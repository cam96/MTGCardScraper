import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CardScraper {
	private WebDriver driver;
	
	public CardScraper() {
		driver = new FirefoxDriver();
//		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
	}
	
	public static void main(String[] args) {
		CardScraper mtgCardScraper = new CardScraper();
		MTGCard mtgcard = mtgCardScraper.createMTGCardFromWebPage("http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=386465");
		System.out.println(mtgcard);
//		mtgCardScraper.getAllCardsFromMTGGathererSite();
//		mtgCardScraper.completeScraping();
        System.out.println("End of Processing");
	}
	
	public void getAllCardsFromMTGGathererSite() {
		List<String> cardLinksForAllSets = new LinkedList<String>();
        driver.get("http://gatherer.wizards.com/Pages/Default.aspx");
        
        List<String> sets = SeleniumHelper.getOptionsFromSelectControl(driver, 
        						By.id("ctl00_ctl00_MainContent_Content_SearchControls_setAddText"));
        
        for (String setName: sets) {
        	driver.get("http://gatherer.wizards.com/Pages/Search/Default.aspx?set=[%22" + setName + "%22]");
        	List<String> cardLinks = SeleniumHelper.getAllHrefOnPage(driver,
								By.xpath("//span[@class='cardTitle']/a"));
        	cardLinksForAllSets = addNotDuplicateElementsToList(cardLinksForAllSets, cardLinks);
        }

        for (String cardLink: cardLinksForAllSets) {
        	MTGCard mtgCard = createMTGCardFromWebPage(cardLink);
        	System.out.println(mtgCard + "\n");
        }
	}
	
	public MTGCard createMTGCardFromWebPage(String href) {
		driver.get(href);
		String cardName = SeleniumHelper.getTextFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow']/div[@class='value']"));
		int convertedManaCost = SeleniumHelper.getIntFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_cmcRow']/div[@class='value']"));
		String effectText = SeleniumHelper.getTextFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow']/div[@class='value']"));
		String flavourText = SeleniumHelper.getTextFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_flavorRow']/div[@class='value']"));
		String powerAndToughnessStr = SeleniumHelper.getTextFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_ptRow']/div[@class='value']"));
		int[] powerAndToughness = getPowerAndToughness(powerAndToughnessStr);
		
		int power = powerAndToughness[0];
		int toughness = powerAndToughness[1];
		String artistName = SeleniumHelper.getTextFromElement(driver, By.xpath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_artistRow']/div[@class='value']"));
		
		return new MTGCard(cardName, convertedManaCost, effectText, flavourText, power, toughness, artistName);
	}
	
	private int[] getPowerAndToughness(String powerAndToughnessStr) {
		int[] powerAndToughness = new int[2];
		
		if (!powerAndToughnessStr.equals("")) {
			String[] tokens = powerAndToughnessStr.split(" / ");
			powerAndToughness[0] = Integer.parseInt(tokens[0]);
			powerAndToughness[1] = Integer.parseInt(tokens[1]);
		}
		
		return powerAndToughness;
	}
	
	public void completeScraping() {
		driver.quit();
	}
	
	public static List<String> addNotDuplicateElementsToList(List<String> existingList, List<String> newList) {
		for (String currString: newList) {
			if (!existingList.contains(currString)) {
				existingList.add(currString);
			}
		}
		
		return existingList;
	}
}
