package ua.lviv.sko01.file.service;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.file.util.DocumentProcessingUtils;
import ua.lviv.sko01.hibernate.model.Document;

public class ProcessingServiceImpl implements ProcessingService{
	
	public DocumentDTO createPDFRepresentation(Document document, DocumentDTO documentDTO){
		documentDTO.setId(document.getId());
		String fileName = DocumentProcessingUtils.saveXmlDataToFolder(document);
		DocumentProcessingUtils.getPdfData(fileName, documentDTO);
		return documentDTO;
	}
}
