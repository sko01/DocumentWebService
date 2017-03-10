package ua.lviv.sko01.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.ws.service.DocumentService;
import ua.lviv.sko01.ws.service.DocumentServiceImpl;

@WebService(endpointInterface = "ua.lviv.sko01.ws.DocumentWS")
public class DocumentWSImpl implements DocumentWS{
	
	@WebMethod
	public DocumentDTO getDocument(@WebParam(name = "documentId") int id){
		DocumentService documentService = DocumentServiceImpl.getInstance();
		return documentService.getDocument(id);
	}
	
	@WebMethod
	public List<String> getDocuments() {
		DocumentService documentService = DocumentServiceImpl.getInstance();
		return documentService.getDocuments();
	}

	@WebMethod
	public void storeDocument(byte[] data) {
		DocumentService documentService = DocumentServiceImpl.getInstance();
		documentService.storeDocument(data);
	}

}
