import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hjy on 16-3-23.
 */
public class Redis {
    //Redis服务器IP
    private static String ADDR = "10.137.2.102";

    //Redis的端口号
    private static int PORT = 6379;
    //访问密码
    private static String AUTH = "admin";

    public static void main(String[] args) throws Exception{
        int has = 0 ;

            //TimeUnit.MINUTES.sleep(20);

            String[] suid = {"C4999D7B66CA0D0A0000000056F26871", "C4999D7B2624930A0000000056F268D4", "C4999D7B2624930A0000000056F268F5"
                    , "C4999D7B2624930A0000000056F2690D", "C4999D7B260C930A0000000056F2692B", "C4999D7B260C930A0000000056F26946"
                    , "C4999D7B2708930A0000000056F26962", "C4999D7B66CA0D0A0000000056F2697C", "C4999D7B2708930A0000000056F269A2"
            };

            String[] snuid = {"C89491770D0822B856ED68FD0DA84C01", "F4A9B24B302A1E9B5035F79C3096DA3E", "F9A7A3463D38108A5C2E09CF3E8B6363"
                    , "A5F9FD1B60644FD40219995F61EC86F0", "C19F9B7E05002BB1603AA574062D602F", "F1ADA84F34311B8152D44473351AA330"
                    , "89D5D0364D4862F925DCC1804DD9BD7A", "93CECB2D565279E23D73F8AD57E9C64B", "DF8287601B1E34AE77432AFB1BC4DCC1"

            };
            String[] suv = {"1458727281695837", "00BD32EB7B9D99C456F268D49ADE0163", "00FD70C77B9D99C456F268F484D0A174"
                    , "000010047B9D99C456F2690CBB273363", "00FD70C77B9D99C456F2692986A58560", "000010047B9D99C456F26945BCC81091"
                    , "003864057B9D99C456F26961588C4404", "008864047B9D99C456F2697CD95F6064", "0092783B7B9D99C456F269A1CF0EE962"
            };


            String[] s1 = {"C4999D7B6B20900A0000000056F26A94", "C4999D7B2624930A0000000056F26ADE", "C4999D7B2624930A0000000056F26AF6"
                    , "C4999D7B6A20900A0000000056F26B0E", "C4999D7B2624930A0000000056F26B25", "C4999D7B2624930A0000000056F26B45"
                    , "C4999D7B6A20900A0000000056F26B5B", "C4999D7B6B20900A0000000056F26B72", "C4999D7B6B20900A0000000056F26B8E"


            };

            String[] s2 = {"F2AEAA4D373319824CF81CDE37486A8F", "03515AB3C8CDE673B7EBA737C8A65E14", "BAE4E0057D7B50C9FEE6F8767EB796F6"
                    , "124E4BACD7D3F96254DC9B91D7515AF2", "ADF0F7126A6C44DEEA0551EB6A462B70", "095453B5CEC8E07948818828CE5B3AAF"
                    , "461B1EF88287AC370428680C831F31AF", "D18F886E151338A192E8E41F165C46D8", "39656187FDF9D348754C09F1FD498AD0"


            };

            String[] s3 = {"0019783D7B9D99C456F26A93B3519011", "00A010017B9D99C456F26ADC5F1D8521", "000F10037B9D99C456F26AF540726711"
                    , "00A010017B9D99C456F26B0C5FB00735", "007670CC7B9D99C456F26B248C244298", "004B1B3E7B9D99C456F26B454BE4F936"
                    , "007670CC7B9D99C456F26B5A8E249070", "00AA64067B9D99C456F26B70E969F540", "C4999D7B66CA0D0A0000000056F26B8E"


            };

                suid = s1;
                snuid = s2;
                suv = s3;


            //连接本地的 Redis 服务
            Jedis jedis = new Jedis(ADDR);
            System.out.println("Connection to server sucessfully");
            //查看服务是否运行
            System.out.println("Server is running: " + jedis.ping());


            for (int i = 0; i < 9; i++) {
                //存储数据到列表中
                jedis.lset("gzhcookie" + i, 0, suid[i]);
                jedis.lset("gzhcookie" + i, 1, snuid[i]);
                jedis.lset("gzhcookie" + i,2, suv[i]);

                // 获取存储的数据并输出
                List<String> list = jedis.lrange("cookie" + i, 0, 2);
                for (int j = 0; j < list.size(); j++) {
                    System.out.println("Stored string in redis:: " + j + "---" + list.get(j));
                }

            }


        }

}
