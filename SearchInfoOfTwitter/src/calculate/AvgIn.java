package calculate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AvgIn {
	static String filePath = "/home/chi/Data/";
	// in:29.77
	// out:34.53
	static HashMap<Integer, Integer> numMap = new HashMap<Integer, Integer>();

	public static void main(String[] args) throws IOException {
		File dir = new File(filePath);
		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if (name.endsWith("in"))
					return true;
				return false;
			}

		};
		File[] files = dir.listFiles(fileNameFilter);
		List<File> fileList = new ArrayList<File>();
		for(int i=0;i<files.length;i++){
			if(files[i].getName().equals("all.in"))
				continue;
			fileList.add(files[i]);
		}

		wholeFile();
		
		simpleRandomWalk(fileList);
	}

	public static void wholeFile() throws IOException {
		File file = new File(filePath + "all.in");
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String s1 = null;
		double sum = 0;
		while ((s1 = br.readLine()) != null) {
			String[] s2 = s1.split(",");
			numMap.put(Integer.valueOf(s2[0]), Integer.valueOf(s2[1]));
			sum += Integer.valueOf(s2[1]);
		}
		System.out.println(String.format("%.2f", sum / numMap.size()));
		
		
	}
	
	public static void simpleWholeFile(List<File> fileList) {
		Random random = new Random();
        HashMap<Integer, Object> hashMap = new HashMap<Integer, Object>();
        
        // 生成随机数字并存入HashMap
        while(hashMap.size()<50000){
            int number = random.nextInt(fileList.size()) ;
            hashMap.put(number, 0);
        }
        
        // 从HashMap导入数组
        Object[] values = hashMap.keySet().toArray();
        
        double sum=0;
        // 遍历数组并打印数据
        for(int i = 0;i < values.length;i++){
        	int ii = (int) values[i];
        	
            sum+= numMap.get(Integer.valueOf(fileList.get(ii).getName().substring(0, fileList.get(ii).getName().indexOf("."))));
            
        }
        System.out.println(String.format("%.2f", sum / values.length));
	}

	public static void simpleRandomWalk(List<File> fileList) throws IOException {

		int next = 0;
		double sum = 0;
		int i = 1;
		ArrayList<String> list = new ArrayList<String>();
		String nextFile = getNext(fileList);
		HashSet<String> set = new HashSet<String>();
		set.add(nextFile.substring(0, nextFile.indexOf(".")));
		while (i < 80000) {
			File f = new File(filePath + nextFile);
			if (!f.exists()) {
				nextFile = getNext(fileList);
				while (set.contains(nextFile.substring(0, nextFile.indexOf(".")))) {
					nextFile = getNext(fileList);
				}
			}
			FileReader reader = new FileReader(f);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;

			int count = numMap.get(Integer.valueOf(nextFile.substring(0, nextFile.indexOf("."))));
			while ((s1 = br.readLine()) != null) {
				list.add(s1);
			}
			sum += count;
			// System.out.println(i+":"+count);
			while (nextFile==null||set.contains(nextFile.substring(0, nextFile.indexOf(".")))) {
				Random random = new Random();
				if(list.size()>0){
					next = Math.abs(random.nextInt()) % list.size();
					nextFile = list.get(next) + ".in";
					File nFile = new File(filePath + nextFile);
					if (!nFile.exists()) {
						list.remove(next);
						count--;
						if(count>0){
							nextFile=null;
							continue;
						}
					}
					
				}
				if (set.containsAll(list)||count==0) {
					nextFile = getNext(fileList);
					while (set.contains(nextFile.substring(0, nextFile.indexOf(".")))) {
						nextFile = getNext(fileList);
					}
					break;
				}
			}
			set.add(nextFile.substring(0, nextFile.indexOf(".")));
			if (i % 1000 == 0)
				System.out.println(i + ":" + String.format("%.2f", sum / i));
			i++;
			br.close();
			reader.close();
			list.clear();
		}
		System.out.println(String.format("%.2f", sum / i));
	}

	public static String getNext(List<File> fileList) {
//		System.out.println("getNextFile");
		Random random = new Random();
		int next = Math.abs(random.nextInt()) % fileList.size();
		File file = new File(filePath + fileList.get(next).getName());
		if (file.exists()){
			fileList.remove(next);
			return file.getName();
		}
		return getNext(fileList);

	}

	/**
	 * 采用BufferedInputStream方式读取文件行数
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static int count(File file) throws IOException {
		// InputStream is = new BufferedInputStream(new FileInputStream(file));
		// byte[] c = new byte[1024];
		// int count = 0;
		// int readChars = 0;
		// while ((readChars = is.read(c)) != -1) {
		// for (int i = 0; i < readChars; ++i) {
		// if (c[i] == '\n')
		// ++count;
		// }
		// }
		// is.close();
		// return count;
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String s1 = null;
		int i = 0;
		while ((s1 = br.readLine()) != null) {
			i++;
		}
		br.close();
		reader.close();
		return i;
	}

}
