import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ScrapperApp {

    private static void go() throws Exception {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        String url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=386465";

        final WebClient webclient = new WebClient(BrowserVersion.CHROME);
        final HtmlPage page = webclient.getPage(url);

     //   List<HtmlDivision> divInside = (List<HtmlDivision>) page.getByXPath("//div[@id='ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow']/div[@class='value']");
    //    System.out.println(divInside);
    }
    
    public static void main(String[] args) throws Exception {
        go();
        System.out.println("COMPLETE");
    }
}