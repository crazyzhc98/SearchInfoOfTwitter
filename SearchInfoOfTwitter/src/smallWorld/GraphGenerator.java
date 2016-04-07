package smallWorld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/******************************************************************************
 * Compilation: javac GraphGenerator.java Execution: java GraphGenerator
 * filename delimiter Dependencies: Graph.java In.java StdOut.java
 *
 ******************************************************************************/

public class GraphGenerator {

	public static Graph read(String fileName, String delimiter) {
		
		Graph G = new Graph();
		long start = System.currentTimeMillis();
//		ArrayBlockingQueue<String> outList = new ArrayBlockingQueue<String>(81306,true);
//		ConcurrentLinkedQueue<In> inList = new ConcurrentLinkedQueue<In>();
//		ExecutorService executor = Executors.newFixedThreadPool(5);
//		inList.offer(in);
//		Thread thread = new ReadFileThread(inList, outList);
//		executor.execute(thread);
//		for(int i=0;i<4;i++){
//			Thread thread1 = new AddEdgeThread(outList, G);
//			executor.execute(thread1);
//		}
//		try {
//			// awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
//			while (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
//				System.out.println(G.E());
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(line -> add(G,line,delimiter));

		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return G;
	}
	
	public static void add(Graph G,String line,String delimiter){
		String[] names =line.split(delimiter);
		G.addEdge(names[0], names[1]);
	}

	public static void main(String[] args) {
		String filename = args[0];
		String delimiter = args[1];
		In in = new In(filename);
//		Graph G = GraphGenerator.read(in, delimiter);
//		StdOut.println(G);
	}

}
