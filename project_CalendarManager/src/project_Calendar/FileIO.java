package project_Calendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class FileIO{
	static Object load(String id) throws  ClassNotFoundException, IOException {
		try {
			FileInputStream fis = new FileInputStream("src/project_Calendar/"+id+".txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			ois.close();
			return obj;
		} catch(FileNotFoundException e) {
			return new Object();
		}
		
		
	}
	static void save(String id, Map<String, List<Event>> map) throws IOException{
		FileOutputStream fos = new FileOutputStream("src/project_Calendar/"+id+".txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(map);
		oos.flush();
		oos.close();
		System.out.println(id + " 데이터 저장 완료");
	}
}
