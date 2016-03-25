import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hjy.chapter3.helper.DatabaseHelper;
import org.hjy.chapter3.util.ExcelUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 16-3-17.
 */
public class GZHType {
    public static void main(String[] args) throws Exception{





        int wxcount = 24589;
        int orcount = 4695;
        String wxsql = "select * from amd_pub_service_num  where weixin_id is not null and weixin_id != ' ' ";




        String orisql = "select * from amd_pub_service_num  where weixin_id is  null or weixin_id = ' '  ";
        List<Map<String,Object>> orresults = DatabaseHelper.executeQuery(orisql,null);

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("10.137.2.102");


        int mm = 0;
        for (Map<String,Object> map : orresults){

            System.out.println(++mm+"---------------------------------------------------------------");

            Map<String, String> cookies = new HashMap<String, String>();

            Random random = new Random();


            int index = random.nextInt(8);


            // 获取存储的数据并输出
            List<String> list = jedis.lrange("cookie"+index, 0 ,2);
            for(int i=0; i<list.size(); i++) {
                System.out.println("Stored string in redis:: "+list.get(i));
            }

            cookies.put("SUID",list.get(2));
            cookies.put("SNUID",list.get(1));
            cookies.put("SUV",list.get(0));

            boolean observer = false;
            List<String> record = new ArrayList<String>();
            String oriId= (String)map.get("common_user_name");
            String id= (String)map.get("service_num_id");
            System.out.println(oriId);
            TimeUnit.SECONDS.sleep(2);
            Document doc = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query=" + oriId)
                .cookies(cookies)
                   .timeout(40000).get();
            Elements ees = doc.select("div.txt-box");
            System.out.println(doc.html());
            if (null==ees||ees.isEmpty()){
                ees=doc.select(".other");
                if (null!=ees) {
                    ees=doc.select("#seccode");
                    if (null!=ees){
                        if (!ees.isEmpty() || ees.size() != 0) {
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
                observer=true;
            }
            System.out.println("=======");
            System.out.println(observer);
            System.out.println("==========================================================================================");


            record.add(id);
            record.add(oriId);
            record.add(String.valueOf(observer));
            Workbook resultFile = ExcelUtil.openExcel(new File("/home/hjy/桌面/sql/ori02.xls"));
            HSSFSheet sheet = (HSSFSheet)resultFile.getSheet("Sheet1");
            // HSSFSheet sheet = (HSSFSheet)resultFile.getSheet("Sheet"+sheetnum);
            sheet=ExcelUtil.insertRow(sheet,record);
            resultFile.write(new FileOutputStream(new File("/home/hjy/桌面/sql/ori02.xls")));

        }

    }
}
