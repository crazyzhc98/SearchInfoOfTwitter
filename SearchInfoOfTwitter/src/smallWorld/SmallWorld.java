package smallWorld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SmallWorld {
	public static double avgDegree(Graph G) {
		int sum = 0;
		for (String s : G.vertices()) {
			sum += G.degree(s);
		}

		return (double) sum / G.V();
	}

	public static double sumInDegree_mhrw(Graph G, double avg) {
		String now = G.getST().getRandom();
		double sum = 0;
		double avg_all = 0;
		int i = 0;
		int burn_in = 0;
		TreeSet<String> nodes = new TreeSet<String>();
		TreeSet<String> node1 = new TreeSet<String>();
		List<String> nodeList = new ArrayList<String>();
		List<Double> avgList = new ArrayList<Double>();
		HashMap<String, Integer> nodeMap = new HashMap<String, Integer>();
		Random r = new Random();
		boolean b = true;
		StdOut.printf("i:   ,i_unique , simpled_nodes,  avg    ,   %%    \n");
		double z = 0;
		while (i-burn_in < 100000) {

			if (i == 0) {
				sum += G.degreeIn(now);
				nodes.add(now);
				node1.add(now);
				nodeList.add(now);
				nodeMap.put(now, 1);
				i++;
				avgList.add(sum / i);
			} else {

				List<String> neighbors = new ArrayList<String>();
				neighbors.addAll(G.getNeighbors(now));
				String w = neighbors.get(r.nextInt(neighbors.size()));
				node1.add(w);
				if (r.nextDouble() <= (double) G.degree(now) / (double) G.degree(w)) {
					now = w;
				}
				nodeList.add(now);
				if (!nodeMap.containsKey(now))
					nodeMap.put(now, 0);
				nodeMap.put(now, nodeMap.get(now) + 1);
				nodes.add(now);
				sum += G.degreeIn(now);
				avgList.add(sum / i);
				i++;

				int simpleNode = 800;

				if (b) {
					double avg_a = 0, avg_b = 0, s_a = 0, s_b = 0;
					for (int j = 0; j < i / 10; j++)
						avg_a += avgList.get(j);
					avg_a = avg_a / (i / 10);
					for (int j = 0; j < i / 10; j++)
						s_a += (avgList.get(j) - avg_a) * (avgList.get(j) - avg_a);
					s_a = s_a / (i / 10 - 1);

					for (int j = i / 2; j < i; j++)
						avg_b += avgList.get(j);
					avg_b = avg_b / (i / 2);
					for (int j = i / 2; j < i; j++)
						s_b += (avgList.get(j) - avg_b) * (avgList.get(j) - avg_b);
					s_b = s_b / (i / 2 - 1);

					z = Math.abs((avg_a - avg_b) / Math.sqrt(s_a + s_b));
				}

				// // A/R
				// ArrayList<Integer> puList = new ArrayList<Integer>();
				// for (String node:nodes) {
				// puList.add(nodeMap.get(node));
				// }
				// Collections.sort(puList);
				// int pu=puList.get(puList.size()/10);
				//
				// HashMap<String,Double> buMap = new HashMap<String,Double>();
				// for(String node:nodes){
				// double bu=pu/nodeMap.get(node);
				// buMap.put(node, bu);
				// }
				//
				// ArrayList<String> noRList = new ArrayList<String>();
				// noRList.addAll(nodes);
				// int n=0;
				// double sum_ar=0;
				// int limit=nodes.size()/10;
				// while(n<limit){
				// String node=noRList.get(r.nextInt(noRList.size()));
				// if(Math.random()<buMap.get(node)){
				// noRList.remove(node);
				// sum_ar+=G.degreeIn(node);
				// n++;
				// }
				// }

				if (z < 0.1 && b&&i>1000) {
					avgList.clear();
					nodes.clear();
					nodeMap.clear();
					node1.clear();
					nodeList.clear();
					sum = 0;
					b = false;
					burn_in = i;
					System.out.println(i);
				}
				if ((i-burn_in) % simpleNode == 0) {

					 avg_all = avg(G, nodeList,5);
//					double avg_unique = avg(G, node1);
//					double avg_simpled = avg(G, nodes);
//					StdOut.printf("%5d,%5d,%5d,%7.3f,%5.3f%%,%7.3f,%5.3f%%,%7.3f,%5.3f%%,%7.6f\n", (i - burn_in),
//							node1.size(), nodes.size(), avg_all, Math.abs((avg_all - avg) / avg * 100), avg_unique,
//							Math.abs((avg_unique - avg) / avg * 100), avg_simpled,
//							Math.abs((avg_simpled - avg) / avg * 100), z);
					StdOut.printf("%5d,%7.3f,%5.3f%%,%7.6f\n", (i - burn_in), avg_all, Math.abs((avg_all - avg) / avg * 100), z);
					// avgList.clear();
					// nodes.clear();
					// nodeMap.clear();
					// node1.clear();
					// nodeList.clear();
					// sum=0;
					// now = G.getST().getRandom();
					// i=0;
				}
			}

		}
		return avg_all;
	}
	
	public static double sumInDegree_rw(Graph G, double avg) {
		String now = G.getST().getRandom();
		double sum = 0;
		int i = 0;
//		List<String> nodeList = new ArrayList<String>();
		HashSet<String> node = new HashSet<String>();
		Random r = new Random();
		StdOut.printf("i:   ,  avg    ,   %%    \n");
//		List<String> neighbors = new ArrayList<String>();
		while (node.size() < 50000) {
//			neighbors.clear();
//			neighbors.addAll(G.getNeighbors(now));
//			now = neighbors.get(r.nextInt(neighbors.size()));
			now = G.getRandomAt(now);
			if(G.degreeIn(now)==0)
				continue;
//			nodeList.add(now);
			
			if(!node.contains(now)){
				node.add(now);				
				sum += 1 / G.degreeIn(now);
			}
			i++;
			int simpleNode = 800;

			if (node.size() % simpleNode == 0) {

				StdOut.printf("%5d,%7.3f,%5.3f%% \n", node.size(), node.size() / sum,
						Math.abs((node.size() / sum - avg) / avg * 100));
			}

		}
//		i=0;
//		sum=0;
//		for(String n:node){
//			sum += 1 / G.degreeIn(n);
//			i++;
//			int simpleNode = 800;
//
//			if (i % simpleNode == 0) {
//
//				StdOut.printf("%5d,%7.3f,%5.3f%% \n", i, i / sum,
//						Math.abs((i / sum - avg) / avg * 100));
//			}
//		}
		
		
		return node.size() / sum;
	}

	public static double avg(Graph G, Collection<String> c,int k) {
		double sum = 0d;
		int i=0;
		for (String node:c) {
			if(i%k==0)
				sum += G.degreeIn(node);
			i++;
		}
		return sum / (c.size()/k);

	}

	public static int sumOutDegree(Graph G) {
		int sum = 0;
		for (String v : G.vertices()) {
			sum += G.degreeOut(v);
		}
		return sum;
	}

	public static double avgInDegree(Graph G) {
		double sum = 0;
		for (String v : G.vertices()) {
			sum += G.degreeIn(v);
		}
		return sum / G.V();
	}

	public static double avgOutDegree(Graph G) {
		double sum = 0;
		for (String v : G.vertices()) {
			sum += G.degreeOut(v);
		}
		return sum / G.V();
	}

	public static double avgInDegreeSRW(Graph G, double avg) {
		int i = 0;
		double sum = 0;
		while (i < 50000) {
			sum += G.degreeIn(G.getST().getRandom());
			i++;
			// if(i%1000==0)
			// StdOut.printf(i+": average InDegreeSRW = %7.3f , %7.3f \n",
			// sum/i,Math.abs(avg-sum/i)/avg*100);
		}
		return sum / i;
	}

	public static double avgOutDegreeSRW(Graph G, double avg) {
		int i = 0;
		double sum = 0;
		while (i < 50000) {
			sum += G.degreeIn(G.getSTOut().getRandom());
			i++;
			// if(i%1000==0)
			// StdOut.printf(i+": average OutDegreeSRW = %7.3f , %7.3f \n",
			// sum/i,Math.abs(avg-sum/i)/avg*100);
		}
		return sum / i;
	}

	public static double avgDistance(Graph G) {
		int totalEdges = 0;
		int totalPairs = G.V() * (G.V() - 1);

		int count = 0;
		for (String v : G.vertices()) {
			if (count > 0 && count % 100 == 0)
				StdOut.println(count);
			count++;
			PathFinder finder = new PathFinder(G, v);
			for (String w : G.vertices()) {
				if (finder.hasPathTo(v)) {
					totalEdges += finder.distanceTo(w);
				}
			}
		}
		return (double) totalEdges / totalPairs;
	}

	// return maximum degree of any vertex
	public static int maxDegree(Graph G) {
		int max = 0;
		for (String v : G.vertices()) {
			if (G.degree(v) > max)
				max = G.degree(v);
		}
		return max;
	}

	public static double clusterCoeff(Graph G) {
		long start = System.currentTimeMillis();
		ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<Float> clusterCoeff = new ConcurrentLinkedQueue<Float>();
		for (String v : G.vertices()) {
			queue.add(v);
		}
		ExecutorService executor = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 4; i++) {
			Thread thread = new ClusterCoeffThread(G, queue, clusterCoeff);
			executor.execute(thread);
		}
		executor.shutdown();

		try {
			// awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
			while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				System.out.println("now finished: " + clusterCoeff.size());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		float avg = 0;
		for (Float f : clusterCoeff) {
			avg += f;
		}
		avg = avg / G.V();
		long end = System.currentTimeMillis();

		System.out.println("Cluster Coeff Time：" + (end - start));
		return avg;
	}

	public static void main(String[] args) {

		String filename = "/home/chi/Downloads/twitter_combined.txt";
		String delimiter = " ";
//		In in = new In(filename);
		long start = System.currentTimeMillis();
		Graph G = GraphGenerator.read(filename, delimiter);
		long end = System.currentTimeMillis();
		System.out.println("read file times：" + (end - start));
		StdOut.printf("number of vertices  = %7d\n", G.V());
		StdOut.printf("number of edges     = %7d\n", G.E());
		double avg = avgInDegree(G);
		StdOut.printf("average InDegree     = %7.3f\n", avg);
		// StdOut.printf("average OutDegree = %7.3f\n", avgOutDegree(G));
		// for(int i=0;i<10;i++)
//		StdOut.printf("avg InDegree mhrw     = %7.3f\n", sumInDegree_mhrw(G, avg));
		StdOut.printf("avg InDegree rw       = %7.3f\n", sumInDegree_rw(G, avg));
		// StdOut.printf("Sum OutDegree = %7d\n", sumOutDegree(G));
		// StdOut.printf("average InDegreeSRW = %7.3f\n",
		// avgInDegreeSRW(G,avg));
		// StdOut.printf("average OutDegreeSRW = %7.3f\n",
		// avgOutDegreeSRW(G,avg));
		StdOut.printf("average degree      = %7.3f\n", avgDegree(G));
		StdOut.printf("maximum degree      = %7d\n", maxDegree(G));
		// StdOut.printf("cluster coefficient = %7.3f\n", clusterCoeff(G));
		// StdOut.printf("average distance = %7.3f\n", avgDistance(G));

	}
}
