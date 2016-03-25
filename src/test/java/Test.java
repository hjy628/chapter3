import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 16-3-23.
 */
public class Test {
    public static void main(String[] args) throws Exception{
         	System.out.println("start...........");


        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("10.137.2.102");




        while (true) {

            boolean observer = false;

            TimeUnit.MINUTES.sleep(15);


            Map<String, String> cookies = new HashMap<String, String>();
            cookies.put("SNUID","633F3ADDA6A3890BE504B017A73F22AF");
            cookies.put("SUID","C4999D7B7F40900A557A97A20000B8C5");
            cookies.put("SUV","006D64077B9D99C55615CF84C33F8320");


            for (int i = 0; i < 9; i++) {

                TimeUnit.SECONDS.sleep(30);

                Connection.Response res = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query=gh_3a438fa6c795")
                        //.cookies(cookies)
                        .timeout(30000).execute();

                cookies = res.cookies();

                String SUID = "C4999D7B7F40900A557A97A20000B8C5";
                String SNUID = "633F3ADDA6A3890BE504B017A73F22AF";
                String IPLOC = "CN3300";
                String ABTEST = "0|1458782962|v1";

                if (cookies.containsKey("SUID")){
                    SUID=cookies.get("SUID");
                }
                if (cookies.containsKey("SNUID")){
                    SNUID=cookies.get("SNUID");
                }

                if (cookies.containsKey("IPLOC")){
                    IPLOC=cookies.get("IPLOC");
                }
                if (cookies.containsKey("ABTEST")){
                    ABTEST=cookies.get("ABTEST");
                }

                String SUV =String.valueOf(System.currentTimeMillis() * 1000 + Math.round(Math.random() * 1000));

                //存储数据到列表中
   /*     jedis.lset("gzhcookie",0, SUID);
        jedis.lset("gzhcookie",1, SNUID);
        jedis.lset("gzhcookie",2, SUV);

        jedis.lset("gzhcookie",3, IPLOC);
        jedis.lset("gzhcookie",4, ABTEST);*/

                jedis.lset("gzhcookie"+i,0, SUID);
                jedis.lset("gzhcookie"+i,1, SNUID);
                jedis.lset("gzhcookie"+i,2, SUV);

                jedis.lset("gzhcookie"+i,3, IPLOC);
                jedis.lset("gzhcookie"+i,4, ABTEST);



                List<String> list = jedis.lrange("gzhcookie"+i, 0 ,4);


                System.out.println( list.get(0));
                System.out.println( list.get(1));
                System.out.println( list.get(2));
                System.out.println( list.get(3));
                System.out.println( list.get(4));



            }

        }
    }
}
