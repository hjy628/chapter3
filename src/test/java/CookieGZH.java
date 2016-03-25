import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.hjy.chapter3.helper.DatabaseHelper;
import org.hjy.chapter3.util.ExcelUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 16-3-24.
 */
public class CookieGZH {

    public static void main(String[] args) throws Exception{

        long lastTime = System.currentTimeMillis();

        System.out.println(DateFormatUtils.format(lastTime, "yyyy-mm-dd hh:mm:ss"));

        int wxcount = 24589;
        int orcount = 4695;
        String wxsql = "select * from amd_pub_service_num  where weixin_id is not null and weixin_id != ' ' ";

        String orisql = "select * from amd_pub_service_num  where weixin_id is  null or weixin_id = ' '  ORDER BY service_num_id LIMIT 3736,959 ";
        List<Map<String,Object>> orresults = DatabaseHelper.executeQuery(orisql, null);



        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("10.137.2.102");

            int mm = 0;

        long lTime = System.currentTimeMillis();

            for (Map<String,Object> map : orresults){

                System.out.println(++mm+"---------------------------------------------------------------");

                Map<String, String> cookies = new HashMap<String, String>();


                Random random = new Random();

                int index = random.nextInt(8);


                // 获取存储的数据并输出
                List<String> list = jedis.lrange("gzhcookie"+index, 0 ,4);
                for(int i=0; i<list.size(); i++) {
                    System.out.println("Stored string in redis:: "+list.get(i));
                }

                cookies.put("SUID",list.get(0));
                cookies.put("SNUID",list.get(1));
                cookies.put("SUV",list.get(2));
                cookies.put("IPLOC",list.get(3));
                cookies.put("ABTEST",list.get(4));



                boolean observer = false;
                List<String> record = new ArrayList<String>();
                String oriId= (String)map.get("common_user_name");
                String id= (String)map.get("service_num_id");
                System.out.println(oriId);



                TimeUnit.SECONDS.sleep(random.nextInt(3)*10);

               /* if (lTime+9000<=System.currentTimeMillis()){

                    Connection.Response res = Jsoup.connect("http://news.sogou.com/").timeout(30000).execute();


                    jedis.lset("gzhcookie",2, String.valueOf(System.currentTimeMillis() * 1000 + Math.round(Math.random() * 1000)));

                    lTime=System.currentTimeMillis();
                }
*/

            /*    //取cookie
                if (lastTime+600000<=System.currentTimeMillis()){
                    //取cookie存

                    for (int i = 0; i < 9; i++) {

                        TimeUnit.SECONDS.sleep(1);

                        Map<String, String> getcookies = new HashMap<String, String>();
                        Connection.Response res = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query="+oriId).timeout(30000).execute();
                        getcookies = res.cookies();

                        String SUID = "C4999D7B7F40900A557A97A20000B8C5";
                        String SNUID = "633F3ADDA6A3890BE504B017A73F22AF";
                        String IPLOC = "CN3300";
                        String ABTEST = "0|1458782962|v1";



                        if (getcookies.containsKey("SUID")){
                            SUID=getcookies.get("SUID");
                        }
                        if (getcookies.containsKey("SNUID")){
                            SNUID=getcookies.get("SNUID");
                        }
                        if (cookies.containsKey("IPLOC")){
                            IPLOC=cookies.get("IPLOC");
                        }
                        if (cookies.containsKey("ABTEST")){
                            ABTEST=cookies.get("ABTEST");
                        }


                        String SUV =String.valueOf(System.currentTimeMillis() * 1000 + Math.round(Math.random() * 1000));
                        //存储数据到列表中
                        //   jedis.lpush("gzhcookie", SUID,SNUID,SUV);


                        jedis.lset("gzhcookie"+i,0, SUID);
                        jedis.lset("gzhcookie"+i,1, SNUID);
                        jedis.lset("gzhcookie"+i,2, SUV);
                        jedis.lset("gzhcookie"+i,3, IPLOC);
                        jedis.lset("gzhcookie"+i,4, ABTEST);

                    }

                    lastTime=System.currentTimeMillis();

                    System.out.println("更新cookie时间"+DateFormatUtils.format(lastTime, "yyyy-mm-dd hh:mm:ss"));

                    Connection.Response res = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query="+oriId).timeout(30000).execute();
                    Document docd = res.parse();
                    observer= hasGZH(docd);
                }else {*/

                    Document doc = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query=" + oriId)
                            .cookies(cookies)
                            .timeout(60000).get();

                    observer= hasGZH(doc);

             //   }

                System.out.println("=======");
                System.out.println(observer);
                System.out.println("==========================================================================================");

                record.add(id);
                record.add(oriId);
                record.add(String.valueOf(observer));
                Workbook resultFile = ExcelUtil.openExcel(new File("/home/hjy/桌面/sql/ori02.xls"));
                HSSFSheet sheet = (HSSFSheet)resultFile.getSheet("Sheet1");
                sheet=ExcelUtil.insertRow(sheet,record);
                resultFile.write(new FileOutputStream(new File("/home/hjy/桌面/sql/ori02.xls")));

            }

    }


    private static boolean hasGZH(Document doc){
        boolean has = false;

        Elements ees = doc.select("div.txt-box");
        System.out.println(doc.html());
        if (null==ees||ees.isEmpty()){
            ees=doc.select(".other");
            if (null!=ees) {
                ees=doc.select("#seccode");
                if (null!=ees){
                    if (!ees.isEmpty() || ees.size() != 0) {
                        System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-mm-dd hh:mm:ss"));
                        System.exit(1);
                    }
                }
            }
            ees = doc.select("div.no-sosuo");
            if(null==ees||ees.isEmpty()){

            }else {

            }
        }else {
            System.out.println(ees.get(0).html());
            has=true;
        }

        return  has;
    }

}
