package webScanner;

public class webScannerMain {
	
	private int maxDepth;
	
	private URLContainer start;
	
	webScannerMain(String URL, int MAX_DEPTH) {
		try {
			this.start = new URLContainer(URL);
			this.start.setDepth(0);
			this.maxDepth = MAX_DEPTH;
			if (this.maxDepth < 0) throw new Exception("Глубина поиска дожна быть больше 0");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		webScannerMain scanner = new webScannerMain(args[0],Integer.valueOf(args[1]));
		CrawlerWatcher watcher = new CrawlerWatcher(scanner.start,scanner.maxDepth,Integer.valueOf(args[2]));
		watcher.start();
	}
}
