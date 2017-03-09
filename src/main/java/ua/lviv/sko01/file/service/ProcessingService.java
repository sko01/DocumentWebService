package ua.lviv.sko01.file.service;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.hibernate.model.Document;

public interface ProcessingService {
	public DocumentDTO createPDFRepresentation(Document document, DocumentDTO documentDTO);
}
