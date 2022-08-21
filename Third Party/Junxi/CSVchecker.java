package esc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVchecker {
	public static String csv1 = "";
	public static String csv2 = "";

	public static int store(String[]a, FileWriter W)
	{
		
		try {
			for (String strings : a) {
				W.write(strings+"\n");
			}
			return 0;
		}

		catch (IOException e) {
			System.out.println("Write file failed.");
			return -1;
		}
	}

	public static int checker(String csv1,String csv2)
	{
		ArrayList<String> file1 = new ArrayList<>();
		ArrayList<String> file2 = new ArrayList<>();
		 
		if(csv1.split("\\.").length!=2 || !csv1.split("\\.")[1].equals("csv")|| csv2.split("\\.").length!=2 || !csv2.split("\\.")[1].equals("csv")) {
			System.out.println("invalid filename! Please input .csv file!");
			return 0;
		}
		else {
			FileWriter writer;
			Scanner scanner,scanner2;
			File W = new File(System.getProperty("user.dir")+"\\file\\" + "compare.csv");
			//File W = new File(System.getProperty("user.dir")+"/file/" + "compare.csv");
			try { writer = new FileWriter(W); }
			catch (IOException e) {
				System.out.println("Error : " + e.getMessage());
				return -1;
			}

			try {
			    scanner = new Scanner( new File(System.getProperty("user.dir")+"\\file\\" + csv1));
				//scanner = new Scanner( new File(System.getProperty("user.dir")+"/file/" + csv1));
				scanner2 = new Scanner(new File(System.getProperty("user.dir")+"\\file\\"+csv2));
				//scanner2 = new Scanner(new File(System.getProperty("user.dir")+"/file/"+csv2));
			}
			catch (FileNotFoundException e) {
			    System.out.println("Error : " + e.getMessage());
			    try { writer.close(); }
				catch (IOException e1) { return -3; }
			    return -2;
			}

			try {
				while(scanner.hasNextLine()&&scanner2.hasNextLine()) {
					String Ol_1 = scanner.nextLine();
					String Ol_2 = scanner2.nextLine();
					file1.add(Ol_1);
					file2.add(Ol_2);
				}
				while(scanner.hasNextLine()) {
					String[] a = {scanner.nextLine()};
					return store(a,writer);
					//store(a, writer);
				}
				while(scanner2.hasNextLine()) {
					String[] a = {scanner2.nextLine()};
					store(a,writer);
				}

				int j = 0;
				while(j!=file1.size()) {
			    	String a = file1.get(j);
			    	for(String b : file2) {
						String[] line1 = a.split(",");
						String[] line2 = b.split(",");
						if(line1.length==line2.length) {
							boolean stat = true;
							for(int i =0;i<line1.length;i++) {	
								if(line1[i].compareTo(line2[i])!=0) {
									stat = false;
									break;
								}
							}
							if(stat) {
								file1.remove(a);
								file2.remove(b);
								j-=1;
								break;
							}
						}
			    	}
					j+=1;
				}

				for(String a : file1) {
					if(!file2.isEmpty()) {
						String[] c = {a,file2.get(0)};
						file2.remove(0);
						store(c,writer);
					}
				}
				scanner.close();
				scanner2.close();
				writer.close();
			}
			catch (Exception e) { return -3; }

			System.out.println("Comparison finished.");
			File file = new File(System.getProperty("user.dir")+"\\file\\" + "compare.csv");
			//File file = new File(System.getProperty("user.dir")+"/file/" + "compare.csv");
			if(file.length()==0) {
				file.delete();
				return 0;
			}
			return 1;
		}	
	}

	public static void main(String[] args) {
		boolean loop = true;
		Scanner inputScanner = new Scanner(System.in);
		while(loop) {
		    loop = false;
		    System.out.print("Enter csv file 1 : ");
			csv1 = inputScanner.nextLine();
			System.out.print("Enter csv file 2 : ");
			csv2 = inputScanner.nextLine();
			System.out.println("Please wait...");
		   	int stat = checker( csv1,csv2) ;
		   	if(stat<=0 &&stat>-3) { loop = true; }
		}
		inputScanner.close();
	}
}
