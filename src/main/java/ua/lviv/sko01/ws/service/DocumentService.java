package ua.lviv.sko01.ws.service;

import java.util.List;

import ua.lviv.sko01.dto.DocumentDTO;

public interface DocumentService {
	public void storeDocument(byte[] data);
	public List<String> getDocuments();
	public DocumentDTO getDocument(int id);
}
