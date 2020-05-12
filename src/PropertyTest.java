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
	
	private static final String FILE = "./data/sentence.properties";	//プロパティのパス
	private static final String REGEX = "^-?[0-9]*$";	//正/負数字の正規表現
	private static final int MIN = 1, MAX = 10;	//入力値の範囲
	
	
	public static void main(String[] args){
		//プロパティから読み込むところがtryの中なので，初期化しないとそのあと使うときにエラーが出る
		String inputText = null, correctText = null, sumText = null, errNumText = null, errExitText = null;
		String str;	//入力文字列格納用
		List<Integer> nums = new ArrayList<Integer>();	//合計の対象となる値を格納する
		
		try(Reader reader = new	FileReader(FILE)){	//ファイルを開く
			Properties properties = new Properties();
			properties.load(reader);
			//順次格納
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
				
				if((str = scanner.next()).matches(REGEX)){	//入力文字列が数字
					int i = Integer.parseInt(str);
					
					if(MIN <= i && i <= MAX){	//範囲内の数字
						nums.add(i);
						System.out.printf(correctText + "\n", i);
					}else{	//範囲外の数字
						System.err.printf(errNumText + "\n", MIN, MAX);
					}
				}else if(str.equals("q")){	//合計
					System.out.printf(sumText + "\n", nums.stream().mapToInt(n -> n).sum());
					break;
				}else{	//強制終了
					System.err.print(errExitText);
					break;
				}
			}		
		}
	}
}
