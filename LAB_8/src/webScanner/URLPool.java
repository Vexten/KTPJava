package webScanner;

import java.util.HashSet;
import java.util.LinkedList;

public class URLPool {
	
	private int maxDepth;
	
	private HashSet<String> visitedURLs;
	
	private HashSet<String> visitedDomains;
	
	private LinkedList<URLContainer> unvisited;
	
	public URLPool(int maxDepth, URLContainer start) {
		this.maxDepth = maxDepth;
		this.visitedURLs = new HashSet<String>();
		this.visitedDomains = new HashSet<String>();
		this.unvisited = new LinkedList<URLContainer>();
		this.unvisited.addFirst(start);
	}
	
	public void addLinks(LinkedList<URLContainer> list) {
		for (URLContainer temp : list) {
			if (temp.getDepth() < this.maxDepth + 1) {
				if (!this.visitedURLs.contains(temp.toString())) {
					if (this.visitedDomains.contains(temp.getDomain())) {
						this.visitedURLs.add(temp.toString());
						this.unvisited.addLast(temp);
						synchronized (this.unvisited) {
							this.unvisited.notify();
						}
					} else {
						this.visitedURLs.add(temp.toString());
						this.visitedDomains.add(temp.getDomain());
						this.unvisited.addFirst(temp);
						synchronized (this.unvisited) {
							this.unvisited.notify();
						}
					}
				}
			}
		}
	}
	

	public synchronized URLContainer getUnvisited() {
		if (this.isEmpty()) {
			synchronized (this.unvisited) {
				try {
					this.unvisited.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return this.unvisited.pollFirst();
	}
	
	public synchronized boolean isEmpty() {
		return this.unvisited.isEmpty();
	}
}
