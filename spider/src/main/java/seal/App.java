package seal;

import seal.spider.Spider;
import seal.spider.impl.CNKIImpl;

public class App {

	public static void main(String[] args) {
		CNKIImpl cnki = new CNKIImpl();
		cnki.getAcmSearch(1);
	}
}
