package webScanner;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class CrawlerWatcher {
	
	/** ������ ��� ���������� regex'� {@link #A_TAG} */
	public static final String A_TAG_STRING = "(?:<a.*(?:href=\"" + URLContainer.DOMAIN_NAME_CHECK_STRING + "?" + URLContainer.LOCATION_CHECK_STRING + "))";
	
	/** ��� ������ � html ��������� ���� � ���������� ������� */
	public static final Pattern A_TAG = Pattern.compile(A_TAG_STRING);
	
	/** ��� ������ �������� � ��������� */
	public static final Pattern IMAGE_TAG = Pattern.compile("(<img)");
	
	/** ���-�� �������� �� �������� �� ���� �������� */
	public static final int CHAR_PER_IMG = 20000;
	
	/** ���-�� �������� �� �������� �� ���� ������ */
	public static final int CHAR_PER_LINK = 2000;
	
	/** ���� HTML */
	public static final int PORT = 80;
	
	/** ���� DBManager'a */
	private DBManager manager;
	
	/** ���� URLPool'a */
	private URLPool pool;
	
	/** ���� ���� Crawler'�� */
	private LinkedList<Crawler> crawlers = new LinkedList<Crawler>();
	
	/** ���� ���� ������� */
	private LinkedList<Thread> threads = new LinkedList<Thread>();
	
	/**
	 * ����������� ������
	 * @param start - URL, � �������� �������� �����
	 * @param maxDepth - ������������ ������� ������
	 * @param threads - ���-�� ������� ��� ������
	 * @param timeout - ����� �������� ������
	 * @throws IllegalArgumentException
	 */
	public CrawlerWatcher(URLContainer start, int maxDepth, int threads, int timeout) throws IllegalArgumentException {
		if (threads < 1) {
			throw new IllegalArgumentException("���-�� ������� ������ ���� ������ 0.");
		}
		if (timeout < 1) {
			throw new IllegalArgumentException("����� �������� ������ ���� ������ 0.");
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
	 * ������ ������
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
