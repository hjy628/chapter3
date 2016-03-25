import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by hjy on 16-3-24.
 */
public class TTT {
    public static void main(String[] args) throws Exception{

        System.setProperty("http.maxRedirects", "50");
        System.getProperties().setProperty("proxySet", "true");

        System.getProperties().setProperty("http.proxyHost", "115.223.248.98");
        System.getProperties().setProperty("http.proxyPort", "9000");

        Connection.Response response = Jsoup.connect("http://httpbin.org/ip").timeout(10000).execute();

        System.out.println(response.body());

    }
}
