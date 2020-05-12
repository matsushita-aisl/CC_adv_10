/*************************
 * Eclipseで実行時，エラー表示（数値範囲外）時に入力用テキストが割り込むバグ有り
 * ターミナルでコンパイル/実行すればちゃんとできます 
 ************************/


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;



public class PropertyTest{
	
	private static final String FILE = "./data/sentence.properties";
	private static final String REGEX = "^-?[0-9]*$";
	private static final int MIN = 1, MAX = 10;
	
	
	public static void main(String[] args){
		
		String inputText = null, correctText = null, sumText = null, errNumText = null, errExitText = null;
		String str;
		List<Integer> nums = new ArrayList<Integer>();
		
		try(Reader reader = new	FileReader(FILE)){
			Properties properties = new Properties();
			properties.load(reader);
			
			inputText = properties.getProperty("inputText");
			correctText = properties.getProperty("correctText");
			sumText = properties.getProperty("sumText");
			errNumText = properties.getProperty("errNumText");
			errExitText = properties.getProperty("errExitText");
			
		}catch(IOException e){
			System.err.println("failed to load the properties");
			e.printStackTrace();
		}
		

		try(Scanner scanner = new Scanner(System.in)){
			while(true){
				System.out.print(inputText);
				
				if((str = scanner.next()).matches(REGEX)){
					int i = Integer.parseInt(str);
					
					if(MIN <= i && i <= MAX){
						nums.add(i);
						System.out.printf(correctText + "\n", i);
					}else{
						System.err.printf(errNumText + "\n", MIN, MAX);
					}
				}else if(str.equals("q")){
					System.out.printf(sumText + "\n", nums.stream().mapToInt(n -> n).sum());
					break;
				}else{
					System.err.print(errExitText);
					break;
				}
			}		
		}
	}
}
