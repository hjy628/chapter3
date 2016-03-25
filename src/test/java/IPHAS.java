import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by hjy on 16-3-24.
 */
public class IPHAS {
    public static void main(String[] args) throws Exception{

        for (int i = 1; i < 11; i++) {

        Document doc = Jsoup.connect("http://www.kuaidaili.com/proxylist/"+i+"/").timeout(10000).get();

            System.out.println(doc.html());

        Elements elements = doc.select("tbody");

            elements=elements.select("tr");

        for (Element element:elements){
            String ip = element.select("td").get(0).text();
            String port = element.select("td").get(1).text();
            String type = element.select("td").get(2).text();

            if (!"透明".equals(type)){
                System.out.println(ip+"-----"+port+"----"+type);

            }

        }


        }



    }
}
