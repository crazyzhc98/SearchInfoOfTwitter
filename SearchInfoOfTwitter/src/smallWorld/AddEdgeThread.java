package smallWorld;

import java.util.concurrent.ArrayBlockingQueue;

public class AddEdgeThread extends Thread {
	
	private ArrayBlockingQueue<String> inList;
	private Graph G;
	
	AddEdgeThread(ArrayBlockingQueue<String> inList,Graph G){
		this.inList=inList;
		this.G=G;
	}
	
	public void run(){
		while(G.V()<81306){
			String line = inList.poll();
			if(line==null)
				continue;
			String[] names = line.split(" ");
			G.addEdge(names[0], names[1]);
		}
	}

}
