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
	
	/** ��������� �������� URL */
	private URLContainer current;
	
	/** �������� ��� ����� ��� ����������� */
	private String domain;
	
	/** �������� �� ����� ��� ������ ������ */
	private String location;
	
	/** ������� ������� ������ */
	private int currentDepth;
	
	/** ������ ��������� �� �������� */
	private LinkedList<URLContainer> foundURLs;
	
	/** ����� ��� ����������� � ����� */
	private Socket io;
	
	/** ���� URLManager'�, � �������� �������� Crawler */
	private URLManager manager;
	
	/**
	 * ����������� ������ - �������������� ����������� ���� � �����������
	 * Crawler � URLManager'�
	 * @param mng - URLManager ��� ��������
	 */
	public Crawler(URLManager mng) {
		this.manager = mng;
		this.foundURLs = new LinkedList<URLContainer>();
		}
	
	/**
	 * �������������� Crawler � ������ ��� URL.
	 * @param URL - ��������� � ����������� � URL.
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
	 * ������������ � URL, ���������� � ���� current, �������� � ���� ������ � ����������� ��� ����.
	 */
	private void grabAndParse() {
		try {
			// ���������� ���������� � domainName �� http
			this.io = new Socket(this.domain,CrawlerWatcher.PORT);
			this.io.setSoTimeout(CrawlerWatcher.TIMEOUT_MS);
			BufferedReader input = new BufferedReader(new InputStreamReader(io.getInputStream()));
			PrintWriter output = new PrintWriter(io.getOutputStream(),true);
			// ��������� � domainName �������� URL
			output.println("GET " + this.location + " HTTP/1.1");;
			output.println("Host: " + this.domain);
			output.println("");
			StringBuffer html = new StringBuffer();
			String ln;
			// ���������� ������ � ������, ���� ����������� �������
			if (input.readLine().endsWith("OK")) {
				// ������� ���� �������� � ������
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
					// ���� ���������� ������ � ���� ������
					boolean flag = false;
					// ������� �� ���������� ������ � �������
					String link = tag.group(URLContainer.DOMAIN_NAME_GROUP);
					// ��������� ������������� ������
					if (link == null) {
						link = this.domain;
					}
					// �������� ������ � ���������� ������
					flag = (link.endsWith(".html")|link.endsWith(".php")|link.endsWith(".htm"));
					if (!flag) {
						// ������� �� ���������� ������ � url
						String link2 = tag.group(URLContainer.LOCATION_GROUP);
						// ������ ������
						if (!link2.startsWith("/")) {
							if (!this.location.endsWith("/")) {
								link2 = this.location + "/" + link2;
							}
							link2 = this.location + link2;
						}
						link = link + link2;
					} else {
						// ������ ������
						if (!link.startsWith("/")) {
							if (!this.location.endsWith("/")) {
								link = this.location + "/" + link;
							}
							link = this.location + link;
						}
						link = this.domain + link;
					}
					try {
						// �������� ������ � ��������� � ���������� � ����� ��������� ������
						found = new URLContainer(link);
						found.setDepth(currentDepth + 1);
						this.foundURLs.addLast(found);
					} catch (MalformedURLException e) {}
				}
				// ������� �������� ��� ����������� ����� ��������
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
	 * ������� ��� ��������� ����� ��������
	 * @param size - ������ ��������
	 * @param param - ���������� ��������� �� ��������
	 * @param baseline - ������ ���-�� �������� �������� �� ���� �������
	 * @return ����������� ���������� �������, �� 1 �� -0.5
	 */
	private double coeficent(double size, double param, double baseline) {
		double result = -Math.pow(param/(size/baseline), 2) + param/(size/(2*baseline));
		if (result < -0.5) {
			result = -0.5;
		}
		return result;
	}

}
