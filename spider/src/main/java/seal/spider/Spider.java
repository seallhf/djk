package seal.spider;

public abstract class Spider {

	
	/**
	 * 
	 * @param keywords
	 * @param name
	 * @param college
	 * @param achievements
	 * @param page
	 */
	public abstract String getSeach(String keywords, String name, String college, Integer achievements, Integer page);
	
	/**
	 * 
	 */
	public abstract String getPage();
}
