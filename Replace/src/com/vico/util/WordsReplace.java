package com.vico.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class WordsReplace {
	
	public static void replace(File file, String regex, String replacement){
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp=br.readLine()) != null) {
				String result = temp.replaceAll(regex, replacement);
				if (result.length()>0) {
					sb.append(result+"\n");
				}
			}
			pw = new PrintWriter(file);
			String finalResult = sb.toString();
			finalResult = finalResult.substring(0,finalResult.length()-1);
			pw.write(finalResult);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (pw != null) {
					pw.flush();
					pw.close();
				}
				if(br != null){
					br.close();					
				}
			} catch (Exception e) {
				//do nothing...
			}
		}
	}
	
	public static void replace(List<File> list, String regex, String replacement){
		Iterator<File> it = list.iterator();
		while(it.hasNext()){
			replace(it.next(), regex, replacement);
		}
	}
	
	public static void main(String[] args) {
//		List<File> list = FileList.getFiles("C:\\apache-tomcat-7.0.56\\webapps\\"
//				+ "shopxx-3.0Beta\\WEB-INF\\template", "ftl");
		
		List<File> list = FileList.getFiles("C:", "exe");
		
//		replace(list, "<meta name=\"copyright\" content=\"SHOP\\+\\+\" />", "");
//		replace(list, "<meta name=\"author\" content=\"SHOP\\+\\+ Team\" />", "");
//		replace(list, "- Powered By SHOP\\+\\+", "");
//		replace(list, "2005-2013", "2014-2015");
		
	}
}
