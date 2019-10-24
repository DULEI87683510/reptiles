package com.dl.reptiles.service.impl;
import com.dl.reptiles.service.LotteryService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *@ClassName LotteryServiceImpl
 *@Description TODO
 *@Author DL
 *@Date 2019/10/9 13:36    
 *@Version 1.0
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    @Override
    public String getWinningNumber() {
        //新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage page = null;
        String pageXml=null;
        try {

            webClient.waitForBackgroundJavaScriptStartingBefore(10000);

            page = webClient.getPage("https://flights.ctrip.com/itinerary/oneway/ctu-wuh?date=2019-10-24&tdsourcetag=s_pcqq_aiomsg");
            //异步JS执行需要耗时,所以这里线程要阻塞10秒,等待异步JS执行结束
            webClient.waitForBackgroundJavaScript(30000);

            pageXml = page.asXml();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }


        Document document = Jsoup.parse(pageXml);//获取html文档


            // 使用 css选择器 提取列表新闻 a 标签
            // <a href="https://voice.hupu.com/nba/2484553.html" target="_blank">霍华德：夏休期内曾节食30天，这考验了我的身心</a>
          //  Elements elements = document.select("div.news-list > ul > li > div.list-hd > h4 > a");
            Elements elements = document.select("div.search_box search_box_tag search_box_light Label_Flight > div.search_table_header");
            for (Element element:elements){
//                System.out.println(element);
                // 获取详情页链接
                //航空公司
            String pnr=    element.select("div.inb logo > div.logo-item flight_logo > div > span > span > strong").text();
                //航班号
            String flightNumber=    element.select("div.inb logo > div.logo-item flight_logo > div > span > span > span").text();
                System.out.println("航空公司:"+pnr+",航班号:"+flightNumber);
              /*  String d_url = element.attr("href");
                // 获取标题
                String title = element.ownText();

                System.out.println("详情页链接："+d_url+" ,详情页标题："+title);*/

            }

     return null;
    }
}
