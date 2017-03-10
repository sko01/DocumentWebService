package ua.lviv.sko01.file.service;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.file.util.DocumentProcessingUtils;
import ua.lviv.sko01.hibernate.model.Document;

public class ProcessingServiceImpl implements ProcessingService{
	private static final Logger LOG = Logger.getLogger(ProcessingServiceImpl.class);
	private static final String xmlExtention = ".xml";
	private static final String pdfExtention = ".pdf";
	private String formName;
	private String filePath;
	
	public ProcessingServiceImpl() {
		loadProjectProperties();
	}

	public DocumentDTO createPDFRepresentation(Document document, DocumentDTO documentDTO){
		documentDTO.setId(document.getId());
		String fileName = saveXmlDataToFolder(document);
		
		if(fileName != null && !fileName.isEmpty()){
			String fullDataFilePath = filePath + "\\" + fileName + xmlExtention;
			String fullOutputFilePath = filePath + "\\" + fileName + pdfExtention;
			String fullFormPath = filePath + "\\" + formName;
			LOG.info("Form path:" + fullFormPath);
			LOG.info("Data path:" + fullDataFilePath);
			LOG.info("Output path:" + fullOutputFilePath);
			
			if(DocumentProcessingUtils.generatePdfFromXml(fullFormPath, fullDataFilePath, fullOutputFilePath)){
				DocumentProcessingUtils.loadFileAsBytesArray(fullOutputFilePath, documentDTO);
				DocumentProcessingUtils.deleteTemporaryFiles(fullOutputFilePath);
			}
			else {
				documentDTO.getErrors().add("Failed to create PDF representation");
			}
			DocumentProcessingUtils.deleteTemporaryFiles(fullDataFilePath);
		}
		return documentDTO;		
	}
	
	private String saveXmlDataToFolder(Document document) {
		String fileName = null;
		Date date = new Date();
		if(document != null){
			fileName = document.getId() + "_" + date.getTime();
			byte[] content = document.getDocData();
			DocumentProcessingUtils.createFolder(filePath);
			DocumentProcessingUtils.writeByteArraysToFile(filePath + "\\" + fileName + xmlExtention, content);
		}
		return fileName;
	}
	
	private void loadProjectProperties(){
		Properties prop = new Properties();
		try{
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("project.properties");
			prop.load(inputStream);
			filePath = prop.getProperty("processing.folder");
			formName = prop.getProperty("processing.form.name");
		} catch (Exception ex) {
			LOG.warn("Failed to load property file", ex);
			filePath = "C:\\processing_folder";
			formName = "Sample_Invoice.xatw";
		}
		
	}
}
