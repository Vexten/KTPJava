package webScanner;

public class webScannerMain {
	
	private int maxDepth;
	
	private URLContainer start;
	
	webScannerMain(String URL, int MAX_DEPTH) throws Exception {
		this.start = new URLContainer(URL);
		this.start.setDepth(0);
		this.maxDepth = MAX_DEPTH;
		if (this.maxDepth < 0) { throw new IllegalArgumentException("Глубина поиска дожна быть больше 0"); }
	}
	
	public static void main(String[] args) {
		try {
			webScannerMain scanner = new webScannerMain(args[0],Integer.valueOf(args[1]));
			CrawlerWatcher watcher = new CrawlerWatcher(scanner.start, scanner.maxDepth, Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			watcher.start();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.print("Входные параметры: <URL> <максимальная глубина> <кол-во потоков> <время ожидания ответа от страницы>");
		} catch (NumberFormatException e) {
			System.out.print("Входные параметры: <URL> <максимальная глубина> <кол-во потоков> <время ожидания ответа от страницы>");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
