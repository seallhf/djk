package seal.spider.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import seal.Utils.GZipUtils;
import seal.Utils.IOUtil;
import seal.spider.Spider;

public class CNKIImpl extends Spider {

	private static Logger log = Logger.getLogger(CNKIImpl.class);

	private String OutputPath = "D:\\知网数据\\用户.dat";

	/**
	 * 
	 * @param keywords
	 * @param name
	 * @param college
	 * @param achievements
	 * @param page
	 */
	public String getSeach(String keywords, String name, String college,
			Integer achievements, Integer page) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("nmt", "nm");
		// 学者名字
		if (name != null)
			params.put("nmv", name);
		// 学校
		if (college != null)
			params.put("ut", college);
		// 专业领域关键词
		if (keywords != null)
			params.put("dn", keywords);
		// 学者成果数
		if (achievements != null)
			params.put("nv", achievements.toString());
		params.put("id", "SC");
		params.put("db", "0");
		params.put("cp", page.toString());
		params.put("ck", "7828416f-a443-40f2-90f0-a5c805e8b3d3");
		params.put("p", "1");
		params.put("uid", "-1");
		Map<String, String> header = new HashMap<String, String>();
		header.put(
				"cookie",
				"ASP.NET_SessionId=c2hhgmlpvmo1b4m1ewktk0oc; m_MyCnkiServer=my.cnki.net; m_MyCnkiRegisterUrl=http%3A//my.cnki.net/elibRegister/; m_MyCurrentServer=xuezhe.cnki.net; m_ThirdServer=my.cnki.net; LID=; CNZZDATA5614751=cnzz_eid%3D964671622-1402913954-%26ntime%3D1403255586");
		header.put("Host", "papers.cnki.net");
		header.put("Connection", "keep-alive");
		header.put("Accept-Encoding", "gzip,deflate,sdch");
		header.put("Origin", "http://papers.cnki.net");
		header.put(
				"Referer",
				"http://papers.cnki.net/Search/Search.aspx?ac=result&nmv=%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
		String html = IOUtil.post(
				"http://papers.cnki.net/View/DataCenter/Scholar.ashx", params,
				header);
		return html;
	}

	public String getAcmSearch(Integer page) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "pagination search");
		params.put("page", page.toString());
		params.put("dbcode", "SJCM");
		params.put("wheretoken", "");
		params.put(
				"sqltoken",
				"nGqk4k3Y3Lg6IzYbIrQl6XUzroGDMugYFim4B1sVH7/fx7TJTZ3Hb/TOSr16TAKEqjZKq9sLVsEGArKcNUZCy4t6Ha8ltSwo5jVT+7M/sBk8QhO0m6cB3AUGU+MyMA8sYJ/zfO5/4F5PyDyOgtjYNZgA2tksr8z6GFZUDhe4VWdXDQ6w7oXM7xysBoqmifKXX0dovcjQ8lw5ql2cieheJ2US6kuxHwwAlgJ9JEgOdLzRpRfh9gXL/w38UZrTgMyjH8TxcvmXeNHM5fdM3QQNhgUCo5nNpn9bEalFZeBe8Aoqhzh21s0649LkLPNtzz0NBVXZCiwismMnCdaQOQigZ+Ob2PBlJWrt6WcMuLkV3qsXvz3PxVDDO37lFsRvl1xSUoDi6F8Rx771mYRyUM0jGdnuOzF5nxfXrvcJq89p0fWRvNxUSNCgj9xczJv0puJVdMJleOx8EM1ojmh+LU6H+v0k9mTm8HYYtQxlFvHfG0NMbEFds6glYVbSh/vq0nG4e0pAHYypalQzcc6pixBI1lNAh2tE8hQGC4hr81h9ob++8ecvirVs43zu9HV/hBNDk78m0CkPmVs=");
		params.put("t", String.valueOf(System.currentTimeMillis()));
		Map<String, String> header = new HashMap<String, String>();
		header.put(
				"cookie",
				"ASP.NET_SessionId=kfdnrr1rk1lzgts4y3dyjaa2; SID=201136; LID=; CNZZDATA4113093=cnzz_eid%3D801380270-1403505598-http%253A%252F%252Felib.cnki.net%252F%26ntime%3D1403505598");
		header.put("Host", "scholar.cnki.net");
		header.put("Connection", "keep-alive");
		header.put("Accept-Encoding", "gzip,deflate,sdch");
		header.put("Origin", "http://scholar.cnki.net");
		header.put("Referer",
				"http://scholar.cnki.net/webpress/brief.aspx?dbcode=SJCM");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
		String html = IOUtil.post(
				"http://scholar.cnki.net/webpress/async/search.ashx", params,
				header);
		
		return html;
	}

	@Override
	public String getPage() {
		// TODO Auto-generated method stub
		try {
			Writer wr = new OutputStreamWriter(
					new FileOutputStream(OutputPath), "utf-8");
			List<String> colleges = IOUtil.read2List(
					"property/speciality.properties", "GBK");
			for (String college : colleges) {
				for (int i = 1; i < 10000; i++) {
					String html = getSeach(college, null, null, 1, i);
					if (html.equals("")) {
						break;
					}
					Document page = Jsoup.parse(html);
					Elements authors = page.getElementsByAttributeValue(
							"class", "listBox wauto clearfix");
					for (Element author : authors) {
						Element e = author.getElementsByAttributeValue("class",
								"xuezheName").get(0);
						Element a = e.getElementsByAttributeValue("target",
								"_blank").first();
						String name = a.text();
						String school = author.getElementsByAttributeValue(
								"class", "f14").text();
						String id = a.attr("href");
						String speciallity = author
								.getElementsByAttributeValue("class",
										"xuezheDW").first().text();
						wr.write(school + "\t" + id + "\t" + speciallity
								+ "\r\n");
						System.out.println(i + ":" + school);
					}
				}
			}
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
