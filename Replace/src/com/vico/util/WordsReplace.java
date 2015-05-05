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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsReplace {

	public static void replace(File file, String regex, String replacement, String exceptant){
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			FileBackup.backup(file); //先尝试备份文件
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			int line = 0; //统计行数
			while ((temp=br.readLine()) != null) {
				if (line > 0) {
					sb.append("\n");
				}
				String result = null;
				if ((exceptant != null) && temp.contains(exceptant)) {
					result = temp;
					sb.append(result);
					continue;
				}else{
					result = temp.replaceAll(regex, replacement);
				}
				sb.append(result);
				line ++;
			}
			pw = new PrintWriter(file);
			String finalResult = sb.toString();
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

	public static void replace(File file, String regex, String replacement){
		replace(file, regex, replacement, null);
	}

	public static void replace(List<File> list, String regex, String replacement){
		Iterator<File> it = list.iterator();
		while(it.hasNext()){
			replace(it.next(), regex, replacement,null);
		}
	}

	public static void replace(List<File> list, String regex, String replacement,String exceptant){
		Iterator<File> it = list.iterator();
		while(it.hasNext()){
			replace(it.next(), regex, replacement,exceptant);
		}
	}

	public static void printFindStrings(File file, String regex, String exceptant){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			Pattern pattern = null;
			Matcher matcher = null;
			while ((temp=br.readLine()) != null) {
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(temp);
				while(matcher.find()){
					System.out.println("\t"+matcher.group());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br != null){
					br.close();					
				}
			} catch (Exception e) {
				//do nothing...
			}
		}
	}

	public static void printFindStrings(List<File> list, String regex, String exceptant){
		Iterator<File> it = list.iterator();
		while(it.hasNext()){
			File next = it.next();
			System.out.println("In file \""+next.getName()+"\" they are:");
			printFindStrings(next, regex ,exceptant);
		}
	}

	private static void replaceForShopxx(File file, String regex, String exceptant){
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			//			FileBackup.backup(file); //先尝试备份文件
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			int line = 0; //统计行数

			Pattern pattern = null;
			Matcher matcher = null;
			String matched = null;
			while ((temp=br.readLine()) != null) {
				if (line > 0) {
					sb.append("\n");
				}
				String result = "";
				boolean isSetResult = false;
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(temp);
				
				if (matcher.find()) { //每行只查找一次
					isSetResult = true;
					if (temp.contains(exceptant)) {
						String[] strs = temp.split("message\\(\\\"admin"); //将字符串切断成几个部分，分别替换
						for (int i = 0; i < strs.length; i++) {
							String replaced = strs[i].replaceAll("message\\(\\\"", "message(\"admin.");
							String join =  i < strs.length -1 ? "message(\"admin" : "";
							result += (replaced + join);
						}
					}else{
						result = temp.replaceAll("message\\(\\\"", "message(\"admin.");
					}
				}
				//如果没有被正则替换过
				if (! isSetResult) {
					result = temp;
				}
				sb.append(result);
				line ++;
			}
			pw = new PrintWriter(file);
			String finalResult = sb.toString();
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

	public static void replaceForShopxx(List<File> list, String regex,String exceptant){
		Iterator<File> it = list.iterator();
		File next = null;
		while(it.hasNext()){
			next = it.next();
			System.out.println("File:\""+next.getName()+"\" in processing...");
			replaceForShopxx(next, regex ,exceptant);
		}
	}

	public static void main(String[] args) {

		List<File> list = FileList.getFiles("C:", "exe");

	}
}
