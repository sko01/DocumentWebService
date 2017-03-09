package ua.lviv.sko01.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import ua.lviv.sko01.dto.DocumentDTO;

@WebService
public interface DocumentWS {
	@WebMethod
	public DocumentDTO getDocument(int id);
	
	@WebMethod
	public List<String> getDocuments();
	
	@WebMethod
	public void storeDocument(byte[] data);
}
