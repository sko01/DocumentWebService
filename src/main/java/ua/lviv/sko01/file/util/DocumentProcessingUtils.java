package ua.lviv.sko01.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import ua.lviv.sko01.dto.DocumentDTO;

public class DocumentProcessingUtils {
	private static final Logger LOG = Logger.getLogger(DocumentProcessingUtils.class); 
	
	public static boolean generatePdfFromXml(String fullFormPath, String fullDataFilePath, String fullOutputFilePath) {
		Process process;
		//String command = String.format("%1s\\process.bat %2s %3s", filePath, filePath + "\\" + fileName + xmlExtention, filePath + "\\" + fileName + pdfExtention);
		String command = String.format("merge.exe -form %1s -data %2s -output %3s", fullFormPath, fullDataFilePath, fullOutputFilePath);
		try{
			LOG.info("Executing command " + command);
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
			if(process.exitValue() != 0){
				return false;
			} else {
				return true;
			}
		}catch (Exception ex){
			LOG.error(ex.getMessage() + "/n" + ex.getStackTrace());
			return false;
		}
	}
	
	public static void createFolder(String filePath){
		File dir = new File(filePath);
		if(!dir.exists()){
			if(dir.mkdir()){
				LOG.info("Processing folder " + filePath + " created");
			}else {
				LOG.warn("Failed to create " + filePath + " folder");
			}
		}
	}
	
	public static void writeByteArraysToFile(String fileName, byte[] content){	
		File file = new File(fileName);
		try {
			file.createNewFile();
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
			writer.write(content);
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadFileAsBytesArray(String fileName, DocumentDTO documentDTO){
		File file = new File(fileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
        	BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
			reader.read(bytes, 0, length);
			reader.close();
			documentDTO.setData(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			documentDTO.getErrors().add(e.getMessage());
		}
	}
	
	public static void deleteTemporaryFiles(String fileName){
		File file = new File(fileName);
		if(file.delete()){
			LOG.info("Deleted " + fileName + " file");
		} else {
			LOG.warn("Failed to delete " + fileName + " file");
		}
	}
}
