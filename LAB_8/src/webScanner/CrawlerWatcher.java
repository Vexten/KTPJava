package webScanner;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class CrawlerWatcher {
	
	/** Строка для компиляции regex'а {@link #A_TAG} */
	public static final String A_TAG_STRING = "(?:<a.*(?:href=\"" + URLContainer.DOMAIN_NAME_CHECK_STRING + "?" + URLContainer.LOCATION_CHECK_STRING + "))";
	
	/** Для поиска в html документе тега с правильной ссылкой */
	public static final Pattern A_TAG = Pattern.compile(A_TAG_STRING);
	
	/** Для поиска картинок в документе */
	public static final Pattern IMAGE_TAG = Pattern.compile("(<img)");
	
	/** Кол-во символов на странице на одну картинку */
	public static final int CHAR_PER_IMG = 20000;
	
	/** Кол-во символов на странице на одну ссылку */
	public static final int CHAR_PER_LINK = 2000;
	
	/** Порт HTML */
	public static final int PORT = 80;
	
	/** Поле DBManager'a */
	private DBManager manager;
	
	/** Поле URLPool'a */
	private URLPool pool;
	
	/** Лист всех Crawler'ов */
	private LinkedList<Crawler> crawlers = new LinkedList<Crawler>();
	
	/** Лист всех потоков */
	private LinkedList<Thread> threads = new LinkedList<Thread>();
	
	/**
	 * Конструктор класса
	 * @param start - URL, с которого начнется поиск
	 * @param maxDepth - Максимальная глубина поиска
	 * @param threads - Кол-во потоков для поиска
	 * @param timeout - Время ожидания сокета
	 * @throws IllegalArgumentException
	 */
	public CrawlerWatcher(URLContainer start, int maxDepth, int threads, int timeout) throws IllegalArgumentException {
		if (threads < 1) {
			throw new IllegalArgumentException("Кол-во потоков должно быть больше 0.");
		}
		if (timeout < 1) {
			throw new IllegalArgumentException("Время ожидания должно быть больше 0.");
		}
		this.manager = new DBManager();
		this.pool = new URLPool(maxDepth, start);
		for (int i = 0; i < threads; i++) {
			Crawler crawler = new Crawler(this.manager, this.pool, timeout);
			this.crawlers.add(crawler);
			Thread thread = new Thread(crawler);
			this.threads.add(thread);
		}
	}
	
	/**
	 * Запуск поиска
	 */
	public void start() {
		for (Thread thread : threads) {
			thread.start();
		}
	}
	
	public void stop() {
		for (Crawler crawler : crawlers) {
			crawler.stop();
		}
		manager.close();
	}
}
