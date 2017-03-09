package ua.lviv.sko01.hibernate.dao;

import java.util.List;

import ua.lviv.sko01.hibernate.model.Document;

public interface DocumentDAO {
	public List<Document> getDocuments();
	public Document getDocument(int id);
	public void storeDocument(Document document);
}
