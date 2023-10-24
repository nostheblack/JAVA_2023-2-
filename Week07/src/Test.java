import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File file1 = new File("input.txt"); // "input.txt" 파일을 읽기 위한 File 객체 생성
        File file2 = new File("output.txt"); // "output.txt" 파일에 결과를 저장하기 위한 File 객체 생성

        // 자원을 자동으로 닫아주는 try-with-resources 블록을 사용합니다.
        try (BufferedReader in = new BufferedReader(new FileReader(file1)); // 파일을 읽기 위한 BufferedReader
             PrintWriter out = new PrintWriter(new FileWriter(file2))) { // 파일에 쓰기 위한 PrintWriter

            int ch;
            while ((ch = in.read()) != -1) {
                char c = Character.toUpperCase((char) ch); // 소문자를 대문자로 변환
                out.print(c); // 결과를 출력 파일에 씁니다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}