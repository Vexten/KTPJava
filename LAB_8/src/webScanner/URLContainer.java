package webScanner;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLContainer {
	
	/** Строка для сборки Pattern'а поиска ссылки */
	public static final String LOCATION_CHECK_STRING = "((?:\\/?[\\w-?=&%]+)*(?:(?:\\.html?)|(?:\\.php))?)";
	
	/** Строка для сборки Pattern'a поиска доменного имени */
	public static final String DOMAIN_NAME_CHECK_STRING = "(?:(?:http:\\/\\/)?((?:[A-Za-zА-Яа-я0-9-.]+)(?:\\.[a-zа-я]{2,14})))";
	
	/** Pattern поиска ссылки */
	public static final Pattern DOMAIN_CHECK_PATTERN = Pattern.compile("^(?!https:\\/\\/)" + DOMAIN_NAME_CHECK_STRING + LOCATION_CHECK_STRING);
	
	/** Pattern поиска доменного имени */
	public static final Pattern LOCATION_CHECK_PATTERN = Pattern.compile(LOCATION_CHECK_STRING);
	
	/** */
	public static final int DOMAIN_NAME_GROUP = 1;
	
	public static final int LOCATION_GROUP = 2;
	
	private String domain;
	
	private String location;
	
	private int depth;
	
	private int score;
	
	public URLContainer(String URL) throws MalformedURLException {
		Matcher matchD = DOMAIN_CHECK_PATTERN.matcher(URL);
		if (!matchD.find()) throw new MalformedURLException("Неправильный формат URL");
		this.domain = matchD.group(DOMAIN_NAME_GROUP);
		if (matchD.group(LOCATION_GROUP).length() == 0) {
			this.location = "/";
		} else {
			this.location = matchD.group(LOCATION_GROUP);
		}	
	}
	
	public String toString() {
		return this.domain + this.location;
	}
	
	public int getDepth() {
		return this.depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public String getLocation() {
		return this.location;
	}

}
