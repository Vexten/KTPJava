package webScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class Crawler implements Runnable {
	
	/** Контейнер текущего URL */
	private URLContainer current;
	
	/** Доменное имя сайта для подключения */
	private String domain;
	
	/** Страница на сайте для забора данных */
	private String location;
	
	/** Текущая глубина поиска */
	private int currentDepth;
	
	/** Ссылки найденные на странице */
	private LinkedList<URLContainer> foundURLs;
	
	/** Сокет для подключения к сайту */
	private Socket io;
	
	/** Поле URLManager'а, к которому привязан Crawler */
	private URLManager manager;
	
	/**
	 * Конструктор класса - инициализирует необходимые поля и привязывает
	 * Crawler к URLManager'у
	 * @param mng - URLManager для привязки
	 */
	public Crawler(URLManager mng) {
		this.manager = mng;
		this.foundURLs = new LinkedList<URLContainer>();
		}
	
	/**
	 * Подготавливает Crawler к работе над URL.
	 * @param URL - Контейнер с информацией о URL.
	 */
	public void set(URLContainer URL) {
		this.current = URL;
		this.domain = URL.getDomain();
		this.location = URL.getLocation();
		this.currentDepth = URL.getDepth();
		this.foundURLs.clear();
	}
	
	@Override
	public void run() {
		this.set(manager.getUnvisited());
		grabAndParse();
		manager.addVisited(current);
		manager.addLinks(foundURLs);
		while (!manager.isEmpty()) {
			this.set(manager.getUnvisited());
			grabAndParse();
			manager.addVisited(current);
			manager.addLinks(foundURLs);
		}
	}
	
	/**
	 * Подключается к URL, указанному в поле current, собирает с него ссылки и присваевает ему счет.
	 */
	private void grabAndParse() {
		try {
			// Установить соединение с domainName по http
			this.io = new Socket(this.domain,CrawlerWatcher.PORT);
			this.io.setSoTimeout(CrawlerWatcher.TIMEOUT_MS);
			BufferedReader input = new BufferedReader(new InputStreamReader(io.getInputStream()));
			PrintWriter output = new PrintWriter(io.getOutputStream(),true);
			// Запросить с domainName страницу URL
			output.println("GET " + this.location + " HTTP/1.1");;
			output.println("Host: " + this.domain);
			output.println("");
			StringBuffer html = new StringBuffer();
			String ln;
			// Продолжить только в случае, если подключение успешно
			if (input.readLine().endsWith("OK")) {
				// Собрать весь документ в строку
				while (!(ln = input.readLine()).equals("</body>")) {
					html.append(ln + "\n");
				}
				int links = 0;
				int images = 0;
				Matcher tag = CrawlerWatcher.A_TAG.matcher(html);
				Matcher img = CrawlerWatcher.IMAGE_TAG.matcher(html);
				URLContainer found;
				while (tag.find()) {
					links += 1;
					// Флаг нахождения ссылки в поле домена
					boolean flag = false;
					// Выбрать из совпадения группу с доменом
					String link = tag.group(URLContainer.DOMAIN_NAME_GROUP);
					// Обработка относительных ссылок
					if (link == null) {
						link = this.domain;
					}
					// Проверка ошибки в нахождении домена
					flag = (link.endsWith(".html")|link.endsWith(".php")|link.endsWith(".htm"));
					if (!flag) {
						// Выбрать из совпадения группу с url
						String link2 = tag.group(URLContainer.LOCATION_GROUP);
						// Сборка ссылки
						if (!link2.startsWith("/")) {
							if (!this.location.endsWith("/")) {
								link2 = this.location + "/" + link2;
							}
							link2 = this.location + link2;
						}
						link = link + link2;
					} else {
						// Сборка ссылки
						if (!link.startsWith("/")) {
							if (!this.location.endsWith("/")) {
								link = this.location + "/" + link;
							}
							link = this.location + link;
						}
						link = this.domain + link;
					}
					try {
						// Упаковка ссылки в контейнер и добавление к листу найденных ссылок
						found = new URLContainer(link);
						found.setDepth(currentDepth + 1);
						this.foundURLs.addLast(found);
					} catch (MalformedURLException e) {}
				}
				// Подсчет картинок для определения счета страницы
				while (img.find()) {
					images += 1;
				}
				int pagesize = html.length();
				int imgpart = (int) (((double)pagesize/4)*coeficent((double)pagesize,(double)images,(double)CrawlerWatcher.CHAR_PER_IMG));
				int lnkpart = (int) (((double)pagesize/4)*coeficent((double)pagesize,(double)links,(double)CrawlerWatcher.CHAR_PER_LINK));
				pagesize = pagesize/2 + imgpart + lnkpart;
				this.current.setScore(pagesize);
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * Функция для получения счета страницы
	 * @param size - размер страницы
	 * @param param - количество элементов на странице
	 * @param baseline - нужное кол-во символов страницы на один элемент
	 * @return Коэффициент выполнения условия, от 1 до -0.5
	 */
	private double coeficent(double size, double param, double baseline) {
		double result = -Math.pow(param/(size/baseline), 2) + param/(size/(2*baseline));
		if (result < -0.5) {
			result = -0.5;
		}
		return result;
	}

}
