package ua.lviv.sko01.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import ua.lviv.sko01.dto.DocumentDTO;
import ua.lviv.sko01.ws.service.DocumentService;
import ua.lviv.sko01.ws.service.DocumentServiceImpl;

@WebService(endpointInterface = "ua.lviv.sko01.ws.DocumentWS")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DocumentWSImpl implements DocumentWS{
	
	@WebMethod
	@XmlElement(name = "Document")
	public DocumentDTO getDocument(@WebParam(name = "documentId") int id){
		DocumentService documentService = new DocumentServiceImpl();
		return documentService.getDocument(id);
	}
	
	@WebMethod
	@XmlElement(name = "DocumentList")
	public List<String> getDocuments() {
		DocumentService documentService = new DocumentServiceImpl();
		return documentService.getDocuments();
	}

	@WebMethod
	public void storeDocument(byte[] data) {
		DocumentService documentService = new DocumentServiceImpl();
		documentService.storeDocument(data);
	}

}
