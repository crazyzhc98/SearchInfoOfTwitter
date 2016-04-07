package smallWorld;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReadFileThread extends Thread{
	
	private ConcurrentLinkedQueue<In> inList;
	private ArrayBlockingQueue<String> outList;
	
	ReadFileThread(ConcurrentLinkedQueue<In> inList,ArrayBlockingQueue<String> outList){
		this.inList=inList;
		this.outList=outList;
	}
	
	public void run(){
		while(!inList.isEmpty()){
			
			In in = inList.poll();
			while (in.hasNextLine()) {
				outList.offer(in.readLine());
			}
		}
	}

}
