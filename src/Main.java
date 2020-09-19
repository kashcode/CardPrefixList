package CardPrefixList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static List<ConfigItem> cards = new ArrayList<ConfigItem>();
	private static final int[] cardNumberDigitsLimit = new int[]{12,25};

	public static void main(String[] args) {		
		Scanner input = new Scanner(System.in);
		
		do {
			System.out.println("Enter name of configuration file:");
			String configPath = input.nextLine();			
			LoadConfigs(configPath);			
			Collections.sort(cards);			
		} while (cards.size() == 0); // kamçr saraksts tukð pieprasam konfigurâcijas failu
		
		while(true){
			System.out.println("Enter card number:");			
			
			try {
				String cardNumber = "";
				cardNumber = input.nextLine();
				if(!rangeContains(cardNumberDigitsLimit[0], cardNumberDigitsLimit[1], cardNumber.length())){
					do {
						System.out.println("Card number must be between 12-25 digits.");
						System.out.println("Enter new card number:");
						cardNumber = input.nextLine();												
					} while (!rangeContains(cardNumberDigitsLimit[0], cardNumberDigitsLimit[1], cardNumber.length()));					
				}
				
				char[] ch = new char[6];
				cardNumber.getChars(0, 6, ch, 0);
				int number = Integer.parseInt(new String(ch));
				
				boolean hasCard = false;
				for (ConfigItem configItem : cards) {
					if (rangeContains(configItem.getFrom(), configItem.getTo(), number)) {
						hasCard = true;
						System.out.println("Output: " + configItem.getCardName());
						break;
					}									
				}
				
				if(!hasCard){
					System.out.println("Card not found.");
				}	
			} catch (Exception exception) {				
				System.out.println("Error: " + exception);
				input.next();				
			}			
		}		
	}
	
	/**
	 * Skaitïa meklçðana norâdîtajâs skaitïu robeþâs
	 * 
	 * @param from Sâkuma robeþa
	 * @param to Beigu robeþa
	 * @param n Skaitlis
	 */
	public static boolean rangeContains(int from, int to, int n) {
		return n >= from && n <= to;
	}

	/**
	 * Ielâdç konfigurâcijas
	 * 
	 * @param path ceïð lîdz configurâcijas failam	 
	 */
	private static void LoadConfigs(String path) {
		BufferedReader bufferedReader = null;
		try {
			String currentLine;
			bufferedReader = new BufferedReader(new FileReader(path));

			int num = 1;
			while ((currentLine = bufferedReader.readLine()) != null) {				
				if (currentLine.contains(",")) {
					String[] parts = currentLine.split(",");
					int from = Integer.parseInt(parts[0]);
					int to = Integer.parseInt(parts[1]);
					String name = parts[2];
					
					cards.add(new ConfigItem(from,to,name));				    
				} else {
				    throw new IllegalArgumentException("Illegal config record at line: " + num);
				}
				num++;
			}

		} catch (IOException exception) {
			if (exception instanceof FileNotFoundException) {				
				System.out.println("File not found.");
			} else {
				exception.printStackTrace();
			}
		} finally {
			try {
				if (bufferedReader != null){
					bufferedReader.close();
				}					
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
