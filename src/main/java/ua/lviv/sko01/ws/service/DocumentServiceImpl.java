package ua.lviv.sko01.ws.service;

import java.util.ArrayList;
import java.util.List;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.file.service.ProcessingService;
import ua.lviv.sko01.file.service.ProcessingServiceImpl;
import ua.lviv.sko01.hibernate.dao.DocumentDAO;
import ua.lviv.sko01.hibernate.dao.DocumentDAOImpl;
import ua.lviv.sko01.hibernate.model.Document;

public class DocumentServiceImpl implements DocumentService {

	private DocumentDAO dao;
	
	private ProcessingService processingService;
	
	private DocumentServiceImpl(){
		this.dao = new DocumentDAOImpl();
		this.processingService = new ProcessingServiceImpl();
	}
	
	private static class DocumentServiceHoder{
		private final static DocumentServiceImpl instance = new DocumentServiceImpl();
	}
	
	public static DocumentService getInstance(){
		return DocumentServiceHoder.instance;
	}
	
	public void storeDocument(byte[] data) {
		Document document = new Document();
		document.setDocData(data);
		dao.storeDocument(document);
	}
	
	public List<String> getDocuments() {
		List<Document> storedDocuments = dao.getDocuments();
		List<String> documents = new ArrayList<String>();
		for (Document storedDocument : storedDocuments) {
			documents.add(storedDocument.getId().toString());
		}
		return documents;
	}

	public DocumentDTO getDocument(int id) {
		DocumentDTO documentDTO = new DocumentDTO();
		Document document = dao.getDocument(id);
		if(document != null){
			processingService.createPDFRepresentation(document, documentDTO);
		} else {
			documentDTO.getErrors().add("Document with ID = " + id + " not found");
		}
		return documentDTO;
	}
	
	public DocumentDAO getDao() {
		return dao;
	}

	public void setDao(DocumentDAO dao) {
		this.dao = dao;
	}

	public ProcessingService getProcessingService() {
		return processingService;
	}

	public void setProcessingService(ProcessingService processingService) {
		this.processingService = processingService;
	}

}
