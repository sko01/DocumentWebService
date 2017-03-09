package ua.lviv.sko01.file.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import ua.lviv.sko01.hibernate.model.Document;

public class DocumentProcessingService{
	private static final Logger LOG = Logger.getLogger(DocumentProcessingService.class); 
	private static final String filePath = "C:\\processing_folder";
	private static final String xmlExtention = ".xml";
	private static final String pdfExtention = ".pdf";

	public static String saveXmlDataToFolder(Document document) {
		Date date = new Date();
		String fileName = document.getId() + "_" + date.getTime();
		byte[] content = document.getDocData();
		writeByteArraysToFile(fileName + xmlExtention, content);
		return fileName;
	}
	
	public static byte[] getPdfData(String fileName) {
		if(generatePdfFromXml(fileName)) {
			deleteTemporaryFiles(fileName + xmlExtention);
			return loadFileAsBytesArray(fileName + pdfExtention);
		}
		return "Failed to create PDF representation".getBytes();
	}

	public static boolean generatePdfFromXml(String fileName) {
		Process process;
		String command = String.format("%1s\\process.bat %2s %3s", filePath, filePath + "\\" + fileName + xmlExtention, filePath + "\\" + fileName + pdfExtention);
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
	
	private static void writeByteArraysToFile(String fileName, byte[] content){
		File dir = new File(filePath);
		if(!dir.exists()){
			if(dir.mkdir()){
				LOG.info("Processing folder " + filePath + " created");
			}else {
				LOG.warn("Failed to create " + filePath + " folder");
			}
		}
		File file = new File(filePath + "\\" + fileName);
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
	
	private static byte[] loadFileAsBytesArray(String fileName){
		File file = new File(filePath + "\\" + fileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
        	BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
			reader.read(bytes, 0, length);
			reader.close();
			deleteTemporaryFiles(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}        
        return bytes;
	}
	
	private static void deleteTemporaryFiles(String fileName){
		File file = new File(filePath + "\\" + fileName);
		if(file.delete()){
			LOG.info("Deleted " + fileName + " file");
		} else {
			LOG.warn("Failed to delete " + fileName + " file");
		}
	}

}
