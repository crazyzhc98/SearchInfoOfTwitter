package read;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

public class ReadFile {
	static String outPut = "/home/chi/Data/";

	public static void main(String[] args) throws IOException {
		FileReader reader = new FileReader("/home/chi/Downloads/twitter_combined.txt");
		BufferedReader br = new BufferedReader(reader);
		String s1 = null;
		HashMap<String, Integer> inMap = new HashMap<>();
		HashMap<String, Integer> outMap = new HashMap<>();
		HashSet<String> set = new HashSet<String>();
		int i = 0, j = 0;

		while ((s1 = br.readLine()) != null) {
			String[] s2 = s1.split(" ");
			set.add(s2[0]);
			set.add(s2[1]);
			if (!inMap.containsKey(s2[1]))
				inMap.put(s2[1], 0);
			inMap.put(s2[1], inMap.get(s2[1]) + 1);

			if (!outMap.containsKey(s2[0]))
				outMap.put(s2[0], 0);
			outMap.put(s2[0], outMap.get(s2[0]) + 1);
		}
		System.out.println(set.size());
		br.close();
		reader.close();

		System.out.println("in");
		String fileName = outPut+"all.in";
		File file = new File(fileName);
		if (!file.exists() != false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int ii=0;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Entry<String, Integer> entry : inMap.entrySet()) {

			bw.write(entry.getKey() + "," + entry.getValue());// 输出字符串
			bw.newLine();// 换行
			i++;
			if(i%100==0)
			bw.flush();
		}
		bw.flush();
		bw.close();

		System.out.println("out");
		fileName =  outPut+"all.out";
		file = new File(fileName);
		bw = new BufferedWriter(new FileWriter(file));
		if (!file.exists() != false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (Entry<String, Integer> entry : outMap.entrySet()) {

			bw.write(entry.getKey() + "," + entry.getValue());// 输出字符串
			bw.newLine();// 换行
			i++;
			if(i%100==0)
				bw.flush();
		}
		bw.flush();
		bw.close();

		// for(Entry<String,List<String>> entry:inMap.entrySet()){
		// String fileName=outPut+entry.getKey()+".in";
		// File file = new File(fileName);
		// if (!file.exists() != false) {
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// for(String s:entry.getValue()){
		// bw.write(s);//输出字符串
		// bw.newLine();//换行
		// bw.flush();
		// }
		// bw.close();
		// }
		//
		// for(Entry<String,List<String>> entry:outMap.entrySet()){
		// String fileName=outPut+entry.getKey()+".out";
		// File file = new File(fileName);
		// if (!file.exists() != false) {
		// try {
		// file.createNewFile();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// for(String s:entry.getValue()){
		// bw.write(s);//输出字符串
		// bw.newLine();//换行
		// bw.flush();
		// }
		// bw.close();
		// }

	}

}
