import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyFile{

	public static void main(String[] args) throws IOException {
		try(FileReader in = new FileReader("test.txt");
			 FileWriter out = new FileWriter("out.txt")){
			int c;
			while((c = in.read()) != -1){
				out.write(c);
			}
		}
				

	}

}
