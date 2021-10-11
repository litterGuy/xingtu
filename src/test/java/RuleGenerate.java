import com.qihoo.wzws.rzb.util.security.SignatureFactory;
import com.qihoo.wzws.rzb.util.security.SignatureManager;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * rule文件的补充，需要公私钥队生成。故，需要重新打成exe包文件
 * 其中，公私钥队的密钥长度为1024，格式PKCS#8
 */
public class RuleGenerate {
    private static String prikey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMeZQ2gGFEYVm/Xyp4aJb9stzT2DiE4Air2V50lnrOx5CNp1WllA5+Orf1DznLvMDAOK6qS0GaUVfDqeZlAjxMDF/APXj547HNNVI9Lg+8q8nS2bXgLpetQM9DmxNCtx2iSvykDhAcIWQZNclP2bIdrP0H+P65jrFRtQ+Qynk/K1AgMBAAECgYEAiy7wtiUnFggTjVn8P/Cus2Qo7nA+KEZweOuDMMi+6NctuUiEDCEaksQQL97wuHP9HKtOHDQKffeRfT7fkZqfo5/VZ7ZcfjOBpqVkq34RNtACdIrBRXNaHAPWIlD5gauQkrQ2RNaD+cAFzZhuPZiXnDXJDE28b4yLIdOsWu6owAECQQD/P/L6ONr+mtAFCGteE2esNi0iylKxRl+fID+oFZfurtpY1r7mCMlDakO/SMNpiyuEvYvp62Lqt1gKAYT/uQwBAkEAyC9xK9RZyfyLdpVti8Erdydc5KK+pWtJzMydei+obzyZKcA2VHYojO8H7AO099yF2tpLjtPtcSulwGDUBjZ2tQJAZBJQUqXDxhowACki3wlAlhXPcFpePT5X8u0Tx/RfUqae2EGpKkq7jYC1+uKuKkzzzOD7X8R3TYqAK7wYxqFoAQJBAIAwmSj293R36xrJv3eCAIJxy3OBn9Gv7XdfA+zNfe+Vf4MT2famH3t4Sbth+E3Mgk7OARp6LY+N4rtZhgxgbg0CQBmllGiO+FRLskyXV27yEevGssUV2Xvf7MLB4Md6ifKL4g6Um7+/DAx7LxccB8jJgBDbHUz6EXbzuU++QKV5Nys=";
    private static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHmUNoBhRGFZv18qeGiW/bLc09g4hOAIq9ledJZ6zseQjadVpZQOfjq39Q85y7zAwDiuqktBmlFXw6nmZQI8TAxfwD14+eOxzTVSPS4PvKvJ0tm14C6XrUDPQ5sTQrcdokr8pA4QHCFkGTXJT9myHaz9B/j+uY6xUbUPkMp5PytQIDAQAB";

    @Test
    public void generate() throws Exception {
        String rule = "WebCruiser扫描:WCRTESTINPUT000000\n" +
                "Unknown扫描:z9v8|this_is_exist_on_this_server|XbzkCZSQcvPAHxIiqBno|/aaaa/bbbb/ccccc/${@phpinfo()}|\n" +
                "w3af扫描:ping+\n" +
                "WVS扫描:vulnweb.com|acunetix\n" +
                "360Webscan扫描:vul_webscan\n" +
                "安恒Web扫描:dbappsecurity|dbappsec|dbapp|\"%d5'|%21(()%26%26%21%7c*%7c*%7c|(()))******\n" +
                "BashShellShock漏洞:() {|true <<EOF|decode_base64()|\n" +
                "SQL盲注攻击探测:and%20'1'='1|and%20'%25'='|%25'%20and%201=1|%20and%201=2|88888b'|88888a'|%20and%201=1|%20and%201=1%20and%20'%25'='|%20and%201=2%20and%20'%25'='|pg_sleep|benchmark(|sleep(|if(|shutdown|\n" +
                "敏感文件探测:access.log|install.php|phpinfo.php|/info.php|aaa.php|fckeditor/editor/filemanager/browser/default/browser.html|data/dvbbs8.mdb|extras/ipn_test_return.php|.svn/entries|extras/curltest.php?url=http://baike.baidu.com/robots.txt|pass.txt|password.txt|passwords.txt|users.txt|users.ini|admin.cfg|install.log|database.inc|common.inc|db.inc|connect.inc|conn.inc|sql.inc|.bash_history|.bashrc|Web.config|Global.asax|Global.asa|Global.asax.cs|test.asp|test.php|test.jsp|test.aspx|admin.asp|data.mdb|domcfg.nsf|names.nsf|log.nsf|domlog.nsf|\n" +
                "Struts2远程代码执行攻击:\\u0023_memberAccess|xwork.MethodAccessor.denyMethodExecution|java.lang.Runtime|applicationScope\n" +
                "远程代码执行漏洞攻击:shell|%26dir%26|%7Cdir|%26dir|%3Bdir|%3Cdir|%00dir%00|allow_url_include|auto_prepend_file|php://input|%29%3B|cat%20|print%208|%5B%5D|\n" +
                "CSRF漏洞攻击探测:%0d%0a%20SomeCustomInjectedHeader%3Ainjected_by_wvs|%0a%20SomeCustomInjectedHeader%3Ainjected_by_wvs|\n" +
                "可疑文件访问:.asa|.asax|.bak|.BAK|.zip|.ZIP|.tar|.backup|.tmp|.temp|.save|.orig|.php~|.php~1|.java|.class|.vimrc|web.xml|hack%2Ephp|\n" +
                "文件包含漏洞攻击:http://some-inexistent-website.com|some_inexistent_file_with_long_name|../|cmd.exe|..\\|.\\|/etc|.../|boot.ini|%00|/etc/passwd|win.ini|%2e%2F|/environ|/proc|/hosts|bash_history|bashrc|config[root_dir]=|appserv_root=|path[docroot]=|GALLERY_BASEDIR=|_SERVER[DOCUMENT_ROOT]|_CONF[path]|mosConfig_absolute_path=|\n" +
                "LDAP漏洞攻击:!(%28%29&%26%21%7C*%7C%2A%7C|!(%28%29&%26%21%7C*|%2A%7C|\n" +
                "SQL注入攻击:%2527|%bf%27|%20and%201=1|%20and%201=2|'%20and%20'1'='1|%25'%20and%201=1%20and%20'%25'=|\\x5C\\x22|JyI%3D|%20anD%20|information_schema|%20from%20|SeLect*\n" +
                "异常HTTP请求探测:jsky_test.txt|TRACE_test|Jsky_test_no_exists_file.txt|\n" +
                "Xpath注入:position()|\n" +
                "跨站脚本攻击(XSS):%3Cscript%3Ealert|%3Cscript%3Ealert(42873).do|<script>alert(|%3Cscript%3Ealert(42873)%3C/script%3E|alert(|>|\">|prompt(|Response%2EWrite|Response.Write|</ifr%00ame>|</sc%00ript>|</script>|ex/*d*/pres|<OBJECT|\n" +
                "可疑HTTP请求访问:hacker@hacker.org|www.hacker.org|never_could_exist_file_nosec|\n" +
                "拒绝服务恶意脚本:rat=|Are+You+Rat|\n" +
                "IIS服务器攻击:1.asp/1.txt|1.asp/1.html|1.asp/1.jpg|1.asp/1.png|1.asp/1.jpeg|1.asp/1.bmp|.asp;.jpg|.asp;.txt|.asp;.png|.asp;.jpeg|.asp;.bmp|.asp;.html|\n" +
                "Nginx文件解析漏洞:.jpg%00.php|.jpg/1.php|\n" +
                "超长字符串:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa|1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111|)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))|\n" +
                "swfupload跨站:swfupload.swf?buttonText=<|movieName=%22])}\n" +
                "特殊字符URL访问:'|\"|'%22|%00'|%5c'|%5c%22|'\\x22|`|<|\n" +
                "敏感目录访问:/WEB-INF/|/htdocs/|/conf/|/config/|/private/|/administrator/|webadmin/|cgi-bin/|/www/|/root|";

        String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHmUNoBhRGFZv18qeGiW/bLc09g4hOAIq9ledJZ6zseQjadVpZQOfjq39Q85y7zAwDiuqktBmlFXw6nmZQI8TAxfwD14+eOxzTVSPS4PvKvJ0tm14C6XrUDPQ5sTQrcdokr8pA4QHCFkGTXJT9myHaz9B/j+uY6xUbUPkMp5PytQIDAQAB";
        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        String[] rules = rule.split("\n");
        for (int i = 0; i < rules.length; i++) {
            String base = signatureManager.encrypt(rules[i].getBytes("utf-8"), pubkey);
            System.out.println(base);
        }
    }

    @Test
    public void decrypt() throws Exception {
        String data = "Di4iqWp72AbfH85FBGWufhXJzIBlNVIAYnXFn_iXsisuwEzC9IQCILydIx5wxh2wAalUHbcP3mpnXG_URoyYdBvk34_earJqHfbBU9TavrGb_YjX0eKffrarMYnTD-Fic-hEhKe1WJvX1zvdcnCSF3JGnhS7npqchpUlAD7Y4V8\n" +
                "LCqSCc15NKl-TMbZ1EFsiLF4czUQwfnMTVP_TJRzDwrItN1vXB3hTVkEAEgF4mREPixQjJbQz8TR3p_83qetV6ZSOCG9AbJlNLJm22lKOdDLULN_2OlFvCZYCNa89GYu4_iYq_-WZIWSPzLkM0jYG9cAgAWrSop-TcHgoLZUyyU\n" +
                "a3NjX5CF-1Nue4URF2GG_9Zcl5V55ywJI9yOLv-iTDNx8expC1CuJBIPACeal6E5m8d35142B39N7hPxcM4GbZPPPHbF2oyWKwpVTqcT_ixvhtQG1A58enon6pevPx0InWFMdvrBKEfJMN8A7piDycr6Ek7N2brLVL5nZ76mlUc\n" +
                "kRbTS0oebzczPs4etUQ0MqTrr2dSLdAm1WCUzRMvWH6ZaBGhRu0SGPa9lfg0LcYoNo0EoP-j29mZFvy8QacZku1JT2VFxR3_b7_q8oEA-XvjY2Igx6qYLop4H3qaNCMMv0xdTap0kHZOuPBWh7jfWi_lKP3luLoO0xFy4fOuJZc\n" +
                "SRh6ZUNgYqFdzyravHKu9qZa2NUeI8oZEVEEjSsFq8gXVhfDyW5A-51LmHG5JKw2fOanOucLJ9sj-giKzG1LuKRo5uwcO_NEUNASbNjctOVvxtDs5od2ytJSyfmlpJCpDYc2LSyEOtgiGF4EfjCL8iVfyfZIa-_LNuBD-_n9MnM\n" +
                "uvgDRUhsifsW8C0bppgA2PVJOgvTUzyqS_-IoCNy2yAawQMSTMJQP8abKrE1qw5yDvsDlWlJB7ILUKvrKl_My_SNXIeXK_ra8dVX__LzLxYcO1VYeozKmZQ8ggd8HHdMW-eF8_sph3Upvm3gEhpoEVseL1O8aNDgP76jv1yQpvQ\n" +
                "qZOOOty3X9_oDtoE4Za-UoDLpFmtwojYVdZ1-VcMA2Ehr5__6C9HymGmY2Q0EkJqQXM0vhGKshswPhHL2HwJHYlAviQcHQnF059aJdrsuFsbvyyk1bBBsC0ibnYEwMrC3zOKZ8bPnb_q9LdJWYjOe_nTIYPW0XuwxLhQ0KHx5Ls\n" +
                "vroDY38JEYZTNob_mvbshse6v92-hgvEYM-nV1WBNX7gQFqqpMOdUg-luw7v1ggMMD8qlMQLMrZxI65zqigleshtvg0yNd9xu00lVc75leQEDEoTH4Z2tsejtmVm_qYmgVHaBjsBpXUl_AmcUIrbbRvI0FrOcBBRwY_TTUFsVxl4CmN8liEGBB0Zxpm4AT75hQvKMBrEUF5SG2RcfzzMH6p-DPPWQE4faJJl0brzQCfzsdruNOJpT5eExdJHXEF0txw61L8DxKFT9ALeXDGNaksFlg48nAuqTgR_y2oGoShIJLSUDvkUkggbsOE36OPBI1Fa8Q8A25S2oQkWnHwvkQ\n" +
                "WKbaQV39B68MEVkJ7DoV0-fN8mtC00NBNqJhXy4brH1n-bCuFRCBDV5bR8UrKzjl9OG3p-RNby2_f-8i2PDCCLEKepXDcq0lP0JfbMjKm5Ia5CuHUk3jSwM0TF-1Thl8fySaGAyJX-nRg3ROa_DUd6yl_NFL5T2E8_fBihreyaKn46UMYQyNagaUFvOMf1RvWiP1y02aFUQ9HNWFQJcDJV7hczl4peIWpcXvgHx0jhWazOaxJeCkKUHTjgw9aiagSefATpJA53v9NGlKMG_ixlNXxSsxtKUtvgx3nVhU7hzElfSIz6QqzsB7q6WQXz4PlQccQ2vnPuVVKtY0taSzBQfTzO3TFmzNGXjUqL_-lJUmhYDSdj5uB8jNQ0s_RugOAqF0Dzcm7JkVa_Lk7CrD5M0XsP-PIfmDzXUXTPe04g7S4cjMEnZ_TXcTwYWeu1wwvq8krsHlqp4flXgOszmD_l1bgwOyEiUrRKWBCP17-H3JazlAwd_ni73Uq-57EgmaAm1UG7WYya9Rkj9V4Z9mkDH_RF1HK-PlLmD8i7JFVFzBdJA5BOUfvz99FQktaDi_9vLoqxsD-e6lvaUZ8MfxJIDh53wENRBeIHjXrMS4ntnNxfapMLzD6cXVFRxwSDKkTfoXXUzWRRbLDqTgmA-jv7oNTNpbsZLWyZ-tQpkdEBk-HHdJgu4VraAoJUG_OeJ7_CcOvnv_pEL_RK5WTvbqyklV49NRN3uIyXTWqHsclKTQpa8l-6iXsu9_tP9RwxhCe2thRxZjVZTVpt8pewHEiXYeixuU5kRlWr6l9NVQ0v6cr7hg6fd7cZ-42AhKtQzDSa2bamohRUpmwE6chLMCgQ\n" +
                "DqpRxFJmluQwnwJz2gjNdfpqH-0M3FT9CrCph9UDSm7wsP090Yfs0rg94iTt5rcVwKKJQRQPdZ9IskTzfyzlak1ZsJMKIYZVInYIMTMWh1KLoJT75gaou5Qm5t1RAlomo6Ce-2Ltx4v72UTRoM-hJnDwmZlYueharMIjXavne_6G9AbRPsjhzQl6F_DuXu23GIQ8UifarFZjuIfqHGb5flVJFHAFky04YZVXsCwd4GrLg8XFaV6NpY7FL1vjz1eemW3XxgTAnntvCW-PGpb8dlcM4VAjJSz_0rCOtcoJMz2uHDZF8DLAIwblXDr4_YevD2ajkP-lXZ2FgZaJp4H1Ew\n" +
                "k4Piv4S2k4awUIXyWlEaB9Gf7bhseCZ8P20HbSJQdmfzOvo2Y8z61shE56MfZP5PKaT4QxLXKpzFQHxit2MohTw1IONe6DFTLvE3ItCA3LGt2JbbHH8M4fYPFKrdNAS1i1cwX368D7fAGoACEP3Ae4etGEkWbzppjwqDcjzX_AEgTTspAok9Rxe5UFC9_gJxsvqwJap6MTe1Q7_95Toylr_hx_JFXFHI-5kFmoY0bGTh0xWd5WIFyWgpeybGBpcPLQ0Nn9BNkWZFXarWDbBKbQ_cDTtuvyjzkcUiFjfYZ8sJm58fUVarXP9iaMl_Cls7hEa9PZZhLFugcLR1f5bWTQ\n" +
                "vyU4d_kcXsM7thcJAaffVT8SDmgt0yI-418hOqzTTBeyGdSCmBsaiunQ9EfHXL_igDLXgm6R2bJKXU3g5qwVphXuZi6ze4oazSWUaakHyrRarSpSgM6Dm2hiSWXnMc8fEgvUKngySPSPwd1BkcEJ1orDAgipSPiP-Z3usEHe9B3Ba_F5j9390UpoPEJ2rJ_IQwsQIel52lB5oCZ_8V9FPJ7H7_TuYXW_h4Y_hfiMbZr1S2aIU9AicVaAbvjLqn2e4NE8rk91N7XQBKyErIEhGRFvzsRus26-lGH3HPFBCGoS6FGSgn7KIL77kgew2siommqoGmNL91uVddJLufI6SA\n" +
                "Irj2V-idkJZnaTfmk8tkKR5AxzI6l6cVPuROQxU9jbU0dIm8wDYvNpLJ7Kqf-oA3ji9-L6P4Do6pfdtYpveCAIb_6wjIoxqxrZuYqlnOUTALRMvx8r9RY5E29pVqqnw_IsRizSPRv_AbAD8ulsJsJqnwEQfU0WRdcOgwPCwUCO9D3bFY46ofsvAQoxo-tg2x1NSuhXcPrUJUFoDcQ1yKr9m3XRAp1vxzmxCGm62-6S6aPqk4tk5EmYpcnrNp7fqIf2svy1DMLmz3Ous3G2RwRuyRvku887jSESQ-1Oa3elJW7_WNbUw9ETTwYBGV04m9SYmN_bGDb-gmEBKVtrOQ7Q\n" +
                "Y8fJXTdWnztauYMiVpb7Ip7CpIkH1tTsZP2QMb2N2LNNc6KLL2IHZO6oME5IiW974FLCmRT6DcAss-GUtQy85mBFUwqRgB15v6NlLddEWVKt-hvAHtFqHcMt1D1Xe6h4h389oi6wFNsPtkXZF__KWwViGbUFUvpOPVibHUtwjfV_ptBWCZbG6wDu2KgrBMVKcQ99fp4MrVRy7tIis3VwasbGQJRB5nWET_oVTGbfkXhtBkiNPZlV5G5oZ0JksZbNu8KEZkkMsmtYmTdokAM0WYiGjW2xIa6W0mfdWaG0FVDRtSkTADEzim2b4GY_rImvvLPiV7hJHleXVwd9pjCkVXflFxjUmIQuq_iDAsaETRpzVX55hGJR3ZXERmaMp1AIGAQBxzLVRoRcD4QliSxXqkD0U8IlcECcSiyOU--_wgnwlYByZMcpnFj-ckz7OxVHzCvlWt5H02L0gNNc9JO3_-4iAQkI94rtigNdPLJ6QtgPPjstGH7TRZnmUNYutU8s\n" +
                "kemYb_E-0XJkh2vtapJWD60lA6ML78oKeBC0oXiDKmFc5mKL51Bm_DL3GWiI-qq2nuIZNqIFfoPp-rbCmqEXsVMjc7F_4_CwZAubRCTxl-TDFDepAQ-9xE6pvwIBgiQdh9zZlyDpH5MasBqeTqb-Fraqdtnjb5u1UkSTBKmMjN8\n" +
                "VezRyR_YBP6lwoX1tGyS8nx8ICBhAxN1lfGZoxQ-Nv5J-fkuCI9LuHQfXgK5hiBw9-_aRc_LjSnmdSAfHPSP0R--343wd1davUE03mf38nl7E0WmcWUQqtw_um1bXFvST9ilvlfM_N9wQkRy2ONiFSb0o_YVTxkMt50o2_froQmSaz13AHRiqJf3NIuB1pedUS-6YoUNpMjzojffWOUPYYW_kSCuIKsg0J1I-RF8_27XSVfDk8at0cIgjOxSKy5E_AjGOsBpv530tlzuM59jBfJkEL969Qyaze3KEObEERPgm9FU9o4QqJGQqGnUFLHn1RvBsPNUweQuJTwSG4CGUg\n" +
                "EcSOE0yKqQoPB681Z1ZUPAbJj9qlH3jTYrCM9FROGES3-3MMIycMjm2eDuzLuYnFnG3Ky4DO7XPYq343O9QO_2unF_fJkitqb2nYNZfFMam09QdKg4-lf2JH4KJ7OLsNas6h5VszT9Pr-STuxSODM1HqtNzgden-s38o4m3PiVI\n" +
                "djju07O4NWQog5K5MyY6jrBqOVD24FeInv0CdCO9u-seOnApaQJUDmfEvOL8H__gglpD5adY_CJCxIAmhwBYopU-7x2jvji10cxtAqr7BQVPjmxH34rZNjhDssYmkXSdFSsW7GW6fn2_vv3ILgwTEGeYviRj5OInGj12p06g9j4\n" +
                "VVz6Pmldbam5Wi83N-dyyydKrqZVuVFKpEEvvJ7B2rmjYyFagDn7oiSzdmxId0ICTCRwQXgIhrj94Ij4jVOYuBgX9WpU7aQCh5rGUiJRVVQWvr3LzzlSgnh7fqvdAbGxKzwwDFKTY-BFYb9zstDBq1DtPh3yMd-wKw8-g7gkdhyEUwKX6AZ3Y8k5ldEKrKHkpEkKeJXtcLiraLkUYPNQ30PCgziwBoO22eUR5_nmdm_6Ag1lbZPn0Qi-ByupIxxixm4g2LMsz9H11BsBCyKIYeK3fHO-AlkKyfdU4XX1seioAUd2YKLvPnkJOoJ74-dckmDRmv5HGtB5EL5QvUmGyQ\n" +
                "VEKB01QAcTz-QpqzLWKTy36nrXPHun6H2hUZSIckp_bcVXGmKlT_oNKus-nru7mjLmuD-zTWBJqVclr2Gi_CjKxCX3gAj4DOtjxFtZirgnhj62yXLCqaEgpgRTZ5fEZf6db4jmeIfzV-lgefyOT1eDEaHxSDp3DF1j_BEMm9A7A\n" +
                "ecYQh-_JoRylOYZyQ7smBmXhd-RsClXpqVBDBv07k7fWS7T0FCWB3QMsSQRiaHZwWUrjrWOa-MMQrUH_XQvwquMXIqzXowej0PiJxompcBuUdn6WPXow_CRS0sGOc5rr7SBcPUo6oGMS3o1qRcx2DnfPRKoJL8iWJ3wsJnBtm_M\n" +
                "peCB7vTzerYnCLJermuV7Nje6J2DvbJ007e7aSYLVQALTAq6Gewjl7Rgp9JobxwFRTM3eUJ2RBTYZgmASu5JONvx2RHjoOpx7Ny4qywaTgqQXdxWnn_-b1Q0Z3-k8-wBlsH9dgxeYYBdjIyXRQ3wbgU5lgvqo35vfttVjN4pdxozS7WeKYwWkafv7AyGIlRZc7mjbcCrt4ReOewpu6WvWQPJcRSKc4byRUgRaOevNlGnt2NUezFAropU9dBhQPgS4-L9L28lTk-5MujMyDWXucvlOzwhW18uIUuH4ioIRxtQk7fSXd7O2FFFBC5yMeltgwpK9P4thwVjC8t-DBWR4w\n" +
                "PbZ4xZEZl6Tr_vtqmbdcenZdIfhFm6IkFImvr3doyP39hWAMKDGVy6JtqgWF5HCeckHGSGCmcwfzi96Nho2X99_bKezlT1m0rgf4uXFZN7v8sKpMu5uY4LMf8trhEz2agH_J_sE6ScNhbgxf1NRM2lBQtkx-fyiYDaPayM-JwUM\n" +
                "wRmeyQnhqzD-DbZjQe7VfYI5j6Qz6SulglsEKCBY0YNZvTE_mw9ApimSrU5ZVF-6lzFYBJ2t4fRIM_zB7nyLXyDVhz9dtVLxAboF0SBrWzdGg91BX2k2NKch77hUR1hKhH53j1AnwvBQ1VHCHFRYsSKLIUNOQrb8MK3-JN__1Kspfw2VAoQscPpYcY4XDAeW_ksVYCEkAWT_INHUp4f9evK8UhwyYdCBSce47p5lZtdeprchkx_QK-hWcH2PgwieVlmaAT-q2VTP9in07vLcOEb1Fjk-0bnm_Ip1EUxjXuQzCp73ijRLqLoO0Hf-fdPunOg4IHdZgP5nCRBvyz0HLpBJeKIqFr1O_z5Cnma08tZpPbeWPEZjAh7ZNgbYQT-C6gmo-djlQg2D3xRvKb3vzSphTQSjY5YZd4l3J6Wh81waRAF9SkwnxHElgkzMZl8JuBu0Uzh97Wgmt1V-57J7VcjvcFHpHjykhl_-Us10juutXb9cZG3KGV8hEoMMVacp\n" +
                "ttOh6DI21zfgOL_Z1GjHYwdGp-4cjrGblQqzZErub0YPvR5ewwPj6NR_gGaeY7WvnXnQtYgcTp1R2p1yBzFtOkPpXJHUaVT-0RYUmYUQ9s8dm8JIa4DpSdfP6eEWchxZ7rv1uwZHf6FLkeJbdy76X3V7R0JNvlaYM0aNju8jx8Q\n" +
                "vVWd6yDHPyWaerrgOLKCa9Vem-bZWJTtfCH07aRwuGyjb7S2lke1NvBvrZSdmdBS-TI5mYA_JmZEvwyBH2CSBhBNDn94ypjMxOa7WzzOr7Z2Idc5VJk5VpGDTIayjwmIVH6PSI_dV1YEnEjzsgHqD0XufJm1gz9Y198zChGxjZk\n" +
                "AAf85Ums4J3PMQD2KNWXGoz45KjS09vsyfadXSCcIp390gMTI-AeMaxYyASTWbH8k-FnznL4NkxkW2HfiYYr33vZ4Gpy615VGQqqFuwfw366WTyaG2MTNTRclGvOxJEk7-K6sQY1TAFNx6azd4fR61bWfljGGNolF53fmH6GiwI";
        SignatureManager signatureManager = SignatureFactory.getSignature("0001");
        String[] rule = data.split("\n");
        for (int i = 0; i < rule.length; i++) {
            byte[] decryptData = signatureManager.decrypt(Base64.decodeBase64(rule[i]));
            String source = new String(decryptData, "utf-8");
            System.out.println(source);
        }
    }
}
