import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;//ingilizce i harfi
import java.util.Scanner;
import java.io.InputStreamReader;

public class MainTest {

	public static void main(String[] args) throws IOException {
		
		String DELIMITERS = "[-+=" +" " +"\r\n " +"1234567890" +"’'\"" +"(){}<>\\[\\]" +":" +","+
		"‒–—―" +"…" +"!" +"." +"«»" +"-‐" +"?" +"‘’“”" +";" +"/" +"⁄" +"␠" +"·" +"&" +"@" +"*" +
		"\\" +"•" +"^" +"¤¢$€£¥₩₪" +"†‡" +"°" +"¡" +"¿" +"¬" +"#" +"№" +"%‰‱" +"¶" +"′" +"§"+
		"~" +"¨" +"_" +"|¦" +"⁂" +"☞" +"∴" +"‽" +"※" +"]";
		
		SingleLinkedList stopWords = new SingleLinkedList();
		fileReading("D:/stop_words_en.txt", stopWords);
		
		String[] splitted = null;
		HashTable HT = new HashTable();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please select Hash Operation(1 or 2)...");
		System.out.println("  1 - Simple Summation Function(SSF)");
		System.out.print("  2 - Polynomial Accumulation Function(PAF)\n>>");
		int hashOption = sc.nextInt();
		System.out.println("Please select Collision Handling(1 or 2)...");
		System.out.println("  1 - Linear Probing(LP)");
		System.out.print("  2 - Double Hashing(DH)\n>>");
		int collisionOption = sc.nextInt();
		
		HT.setHashOption(hashOption);
		HT.setCollisionOption(collisionOption);
			
		System.out.println("Please wait...");
		//To access the names of the files in the BBC file//1
		File folder = new File("D:/bbc");
		String txt = "";
        int counter;
        boolean flag;       
        long startTime = System.currentTimeMillis();
        
		for(File sourceFile: folder.listFiles()) {
			System.out.println("..............");
			String fileName = sourceFile.getName();//BBC dosyasının içindeki dosyaların isimlerine ulaşmak için 
			txt = fileName.substring(fileName.lastIndexOf(",") + 1);
					
	        File path = new File("D:/bbc/" + txt);
	        File[] files = path.listFiles();
	        String line;
	        File f = null;
	        counter = 0;
	        
	        while(true) {	        	
	        	f = files[counter];
	            if(f.isFile()) {
	                BufferedReader inputStream = null;
	                try {
	                    inputStream = new BufferedReader(new FileReader(f));
	                    while ((line = inputStream.readLine()) != null) {//reaches the lines in txt
	                        
	                        splitted = line.split(DELIMITERS);
	                                           
	                        for(int i = 0; i < splitted.length; i++) {//loops the words in the line
	                        	splitted[i] = splitted[i].toLowerCase(Locale.ENGLISH);//for ı to become i
	                        	flag = stopWords.search(splitted[i]);
	                        	
	                        	if(flag == true && splitted[i] != "") {	                        	       	
	                        		String name = txt +"-"+ f.getName();	 	                        	
	 	                        	HT.put(splitted[i], name);//key = word, value = filename
	 	                        }
	                        }	                        	               			
	                    }
	                }
	                finally {
	                    if (inputStream != null) {inputStream.close();}
	                }
	                counter++;
	            }
	            if(files.length == counter) {break;}
	        }			
		}//File operations end
		
		long endTime = System.currentTimeMillis();
		long Time = endTime - startTime;
		int collision = HT.getCollision();
		System.out.println("The reading of the files is complete.");
		System.out.println("Indexing Time: " + (double)Time/1000);
		System.out.println("Collision Count: " + collision);
		
				
		//searchTest("D:/hw1/search.txt", HT);//searching test can be done if desired
		
		
		//MENU
		System.out.println("\n---Menu---");
		System.out.println(" 1.Remove \n 2.Get \n 3.Exit");
		while(true) {	
			
			System.out.print("\n>>");
			int number = sc.nextInt();
			String word;
			if(number == 1) {
				System.out.print(">Remove: ");
				word = sc.next();
				HT.remove(word);
			}
			else if(number == 2) {
				System.out.print(">Search: ");
				word = sc.next();
				HT.ValueGet(word);
			}
			else if(number == 3){
				System.out.print("Goodbye...");
				break;
			}
			else {
				System.out.println("Please enter a number(1, 2, 3)");
			}			
		}
		
	}

	// file reading for stop words
	public static void fileReading(String str, SingleLinkedList sll) throws IOException {			
		File file = new File(str);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		String line = null;		
		line = reader.readLine();
			
		while(line != null) {	
			sll.add(line);
			line = reader.readLine();	
			line = reader.readLine();
		}
	}
	
	
	
	public static void searchTest(String str, HashTable ht) throws IOException {
		File file = new File(str);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		String line = null;		
		line = reader.readLine();
		long startTime = 0, endTime = 0, estimadeTime;
		long[] arr = new long[1000];
		int count = 0;
		
		while(line != null) {	
			startTime = System.nanoTime(); 
			ht.search(line);
			endTime = System.nanoTime(); 						
			line = reader.readLine();	
			estimadeTime = (endTime - startTime);
			arr[count] = estimadeTime;
			count++;
		}
		Arrays.sort(arr);
		System.out.println("min: " + (double)arr[0]);
		System.out.println("max: " + (double)arr[999]);
	}
	
	
}
