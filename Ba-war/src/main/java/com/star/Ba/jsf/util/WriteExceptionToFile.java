package com.star.Ba.jsf.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class WriteExceptionToFile {
	
public static void createExceptionTraceToFile(String directory ,String name,String descriptionException){
		BufferedWriter bw = null;
		FileWriter fw = null;
try {
			String content = descriptionException+"\n";
			DateFormat  formater = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			DateFormat  formaterName = new SimpleDateFormat("yyyy_MM_dd");

			Date aujourdhui = new Date();
			content=formater.format(aujourdhui)+": "+content;
			//f.createNewFile();
			fw = new FileWriter(new File(directory,formaterName.format(aujourdhui)+".txt"),true);
			bw = new BufferedWriter(fw);
			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
		
	
	
	
	
	public static void main(String[] args) {
	//	WriteExceptionToFile.createExceptionTraceToFile("C:/Users/user_oxia", "badii");
	}
}
