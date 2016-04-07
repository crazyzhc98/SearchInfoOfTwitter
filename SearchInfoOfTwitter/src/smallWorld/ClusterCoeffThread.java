package smallWorld;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClusterCoeffThread extends Thread{
	private Graph G;
	
	private ConcurrentLinkedQueue<String> queue;
	private ConcurrentLinkedQueue<Float> clusterCoeff;
	
	ClusterCoeffThread(Graph G,ConcurrentLinkedQueue<String> que,ConcurrentLinkedQueue<Float> clusterCoeff){
		this.G=G;
		this.queue=que;
		this.clusterCoeff=clusterCoeff;
	}
	
	public void run(){
		String v;
		while(!queue.isEmpty()){
			float i=0;
			v=queue.poll();
			int num=G.degree(v);
			if(num<2)
				continue;
			for(String n:G.adjacentTo(v)){
				for(String e:G.adjacentTo(v)){
					if(e.equals(n))
						continue;
					if(G.hasEdge(n, e))
						i++;
//					if(G.hasEdge(e, v))
//						i++;
				}
			}
			clusterCoeff.offer(i/(num*(num-1)));
		}
		
	}

}
