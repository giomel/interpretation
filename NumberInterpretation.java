package omilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class NumberInterpretation {
	
	public static boolean isValidGreekNumber(String number) {
		
		if(number.length()!=10 && number.length()!=14) {
			return false;
		}else {
			
			switch(number.length()) {
			
			case 10: if(!number.startsWith("2") && !number.startsWith("69")) {
						return false;
					 }else break;
					
					
			case 14:if(!number.startsWith("00302") && !number.startsWith("003069")) {
						return false;
					}else break;
			}
			
			return true;
		}
		
	}
	
	//Accepts as an argument the users' input as a List<String>(ex.[00, 30, 69, 345, 345, 23] ), searches for
	//ambiguities and returns a List of List (a List of possible interpretations)
	
	public static List<List<String>> possibleInterpretations(List<String> number) {
		
		List<List<String>> list= new ArrayList<>();
		list.add(number);
		
		int first=0;
		int second=0;
		
		for(int i=0; i<number.size(); i++) {
			first=Integer.parseInt(number.get(i));
			//System.out.println(first%100);
			
			if(i<number.size()-1) {
			second=Integer.parseInt(number.get(i+1));}
			
			List<String> num= new ArrayList<>();
			
			//possible ambiguities: i) 10, 20...,90 followed by 1,2...,9
			// ii) 100, 200,...900 followed by 1,2,...99
			// iii) 110, 120..., 210 followed by 1,2,...9
			// iv) 13, 14,...19, 21, 22,..29, 91,...99
			// v) 101, 102,...999
			
			if((((first%10)==0 && first>10 && first<100 && first!=0) && (second<10 && second>0)) || 
					
			  (first==10 && second>2 && second<10) || ((((first%100)==10) && first>100) && (second<10 && second>2)) ||
			  
			  (((first%100)==0 && first>=100) && (second<100 && second>0)) ||
					
			  (((first%10)==0 && first>=100) && (second<10 && second>0))){
				
				for (int j=0;j<i;j++) {
					
					num.add(number.get(j));
				}
				
				num.add(first+second+"");
				
				for (int k=i+2;k<number.size();k++) {
					num.add(number.get(k));
					
				}
				
				list.add(num);
				
			}else if((first>12 && first<99) && !((first%10)==0) && (i!=0)){
				
				for (int j=0;j<i;j++) {
					
					num.add(number.get(j));
				}
				
				num.add(""+(first/10)*10);
				num.add(""+first%10);
				
				if(i<number.size()-1) {
				for (int k=i+1;k<number.size();k++) {
					
					num.add(number.get(k));
				}
				}
				
				list.add(num);
				
			} else if((first>100 && first<999) && !((first%100)==0)) {
				
				for (int j=0;j<i;j++) {
					
					num.add(number.get(j));
				}
				
				num.add(""+(first/100)*100);
				num.add(""+first%100);
				
				if(i<number.size()-1) {
				for (int k=i+1;k<number.size();k++) {
					
					num.add(number.get(k));
				}
				}
				
				list.add(num);
				
			}
			
		}
		
		return list;
		
	}
	
	public static void numberInterpretationApplication() {
		
		Scanner scanner= new Scanner(System.in);
		
		boolean containsSpace=false;
		boolean isNumeric=false;
		boolean isUpToThreeDigit=false;
		String[] array=null;
		
		
		// forces user to give valid format: up to three digit numbers, separated by space
		while(!(containsSpace && isNumeric && isUpToThreeDigit)) {
			
			System.out.println("Please give a sequence of up to three digit numbers, separated by space:");
			String input = scanner.nextLine();
			
			 containsSpace=input.contains(" ");
			 String temp=input.replaceAll("\\s", "");
			 isNumeric=temp.matches("\\d+");
			
			  array=input.split(" ");
			 
				for(int i=0; i<array.length; i++) {
					
					if(array[i].length()<=3) {
						isUpToThreeDigit=true;
					}else { isUpToThreeDigit=false;
						break;
					}
				}
		}
		
		List<List<String>> list= possibleInterpretations(Arrays.asList(array));
		Set<String> set= new HashSet<>();
		
		//Every List from the List of possible interpretations from users' input 
		//is passed to possibleIntepretations(), so as to find all possible combinations.
		//We store the possible numbers to a Set<String> to avoid duplicated values.
		for(List<String> temp:list) {
			
			List<List<String>> newList= possibleInterpretations(temp);
			
			for(List<String> l:newList) {
				String n="";
				for(String s:l) {
					n+=s;
				}
				set.add(n);
			}
		}
		
		//Every element of our Set of numbers is passed to isValidGreekNumber() for validation.
		int interpretation=0;
		for(String number:set) {
			interpretation++;
			if(isValidGreekNumber(number)) {
				System.out.println("Intepretation "+ interpretation +":"+number +" [phone number: VALID]");
			}else System.out.println("Intepretation "+ interpretation +":"+number +" [phone number: INVALID]");
			
		}
		
		scanner.close();
		
		
		
	}
	
	public static void main(String[] args) {
		
		numberInterpretationApplication();
	}

}
