package ua.lviv.sko01.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ua.lviv.sko01.file.service.DocumentProcessingService;
import ua.lviv.sko01.hibernate.dao.DocumentDAO;
import ua.lviv.sko01.hibernate.dao.DocumentDAOImpl;
import ua.lviv.sko01.hibernate.model.Document;

@WebService(endpointInterface = "ua.lviv.sko01.ws.DocumentWS")
public class DocumentWSImpl implements DocumentWS{
	
	@WebMethod
	public String getDocument(@WebParam(name = "documentId") int id){
		DocumentDAO documentDao = new DocumentDAOImpl();
		Document document = documentDao.getDocument(id);
		String fileName = DocumentProcessingService.saveXmlDataToFolder(document);
		return new String(DocumentProcessingService.getPdfData(fileName));
	}
	
	@WebMethod
	public List<String> getDocuments() {
		DocumentDAO documentDao = new DocumentDAOImpl();
		List<Document> storedDocuments = documentDao.getDocuments();
		List<String> documents = new ArrayList<String>();
		for (Document storedDocument : storedDocuments) {
			documents.add(storedDocument.toString());
		}
		return documents;
	}

	@WebMethod
	public void storeDocument(byte[] data) {
		DocumentDAO documentDao = new DocumentDAOImpl();
		Document document = new Document();
		document.setDocData(data);
		documentDao.storeDocument(document);
	}

}
