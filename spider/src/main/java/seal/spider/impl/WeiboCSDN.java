package seal.spider.impl;

import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import seal.Utils.IOUtil;

public class WeiboCSDN {

	private HashMap<String, String> getIDList(String url) {
		HashMap<String, String> map = new HashMap<String, String>();
		String page = IOUtil.getPages(url);
		Document html = Jsoup.parse(page);
		Elements div = html.getElementById("div").getElementsByAttributeValue(
				"class", "list_3");
		Elements nodes = div.tagName("a");
		for (Element e : nodes) {
			String name = e.child(0).getElementById("img").attr("alt");
			String csdn = e.attr("href");
			map.put(name, csdn);
			System.out.println(name+"\t"+csdn+"\r\n");
		}
		
		return map;
	}

	private String getWeiboUrl(String url) {
		String csdn = "";
		return csdn;
	}

	public void writer(String location) {
		String result = "";
		HashMap<String,String> map = getIDList("http://blog.csdn.net/mobile/experts.html");
		for (Entry<String, String> e : map.entrySet()) {
			result += e.getKey()+"\t"+e.getValue()+"\r\n";
		}
		IOUtil.write2File(location, result);
	}
}
