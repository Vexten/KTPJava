package webScanner;

import java.util.Scanner;

public class webScannerMain {
	
	private int maxDepth;
	
	private URLContainer start;
	
	webScannerMain(String URL, int MAX_DEPTH) throws Exception {
		this.start = new URLContainer(URL);
		this.start.setDepth(0);
		this.maxDepth = MAX_DEPTH;
		if (this.maxDepth < 1) { throw new IllegalArgumentException("Глубина поиска дожна быть больше 0"); }
	}
	
	public static void main(String[] args) {
		try {
			webScannerMain scanner = new webScannerMain(args[0],Integer.valueOf(args[1]));
			CrawlerWatcher watcher = new CrawlerWatcher(scanner.start, scanner.maxDepth, Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			Close close = scanner.new Close(watcher);
			Thread closeThread = new Thread(close,"closeThread");
			closeThread.start();
			watcher.start();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.print("Входные параметры: <URL> <максимальная глубина> <кол-во потоков> <время ожидания ответа от страницы>");
		} catch (NumberFormatException e) {
			System.out.print("Входные параметры: <URL> <максимальная глубина> <кол-во потоков> <время ожидания ответа от страницы>");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public class Close implements Runnable {
		
		CrawlerWatcher watcher;
		
		public Close(CrawlerWatcher watcher) {
			this.watcher = watcher;
		}
		
		@Override
		public void run() {
			Scanner in = new Scanner(System.in);
			while(true) {
				String cmd = in.next();
				if (cmd.equals("close")) {
					watcher.stop();
					in.close();
					System.exit(0);
				}
			}
		}
	}
}
