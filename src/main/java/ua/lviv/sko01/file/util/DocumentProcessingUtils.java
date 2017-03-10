package ua.lviv.sko01.file.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.hibernate.model.Document;

public class DocumentProcessingUtils {
	private static final Logger LOG = Logger.getLogger(DocumentProcessingUtils.class); 
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
	
	public static void getPdfData(String fileName, DocumentDTO documentDTO) {
		if(generatePdfFromXml(fileName)) {
			deleteTemporaryFiles(fileName + xmlExtention);
			loadFileAsBytesArray(fileName + pdfExtention, documentDTO);
		} else {
			deleteTemporaryFiles(fileName + xmlExtention);
			documentDTO.getErrors().add("Failed to create PDF representation");
		}
	}

	public static boolean generatePdfFromXml(String fileName) {
		Process process;
		String fullDataFilePath = filePath + "\\" + fileName + xmlExtention;
		String fullOutputFilePath = filePath + "\\" + fileName + pdfExtention;
		String fullFormPath = filePath + "\\Sample_Invoice.xatw";
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
	
	private static void loadFileAsBytesArray(String fileName, DocumentDTO documentDTO){
		File file = new File(filePath + "\\" + fileName);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
        	BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
			reader.read(bytes, 0, length);
			reader.close();
			documentDTO.setData(bytes);
			deleteTemporaryFiles(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			documentDTO.getErrors().add(e.getMessage());
		}
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
