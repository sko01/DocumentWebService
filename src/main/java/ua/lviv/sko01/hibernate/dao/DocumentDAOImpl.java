package ua.lviv.sko01.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ua.lviv.sko01.hibernate.model.Document;
import ua.lviv.sko01.hibernate.util.HibernateUtil;

public class DocumentDAOImpl implements DocumentDAO {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@SuppressWarnings("unchecked")
	public List<Document> getDocuments(){
		List<Document> documents = null;
		Session session = null;
		try{
			session = sessionFactory.openSession();
			String hql = "FROM Document";
			Query query = session.createQuery(hql);
			documents = query.list();
		}catch (Exception ex){
			System.err.println(ex.getStackTrace());
		}finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return documents;
	}

	public Document getDocument(int id) {
		Session session = null;
		Document document = null;
		try{
			session = sessionFactory.openSession();
			document = (Document)session.get(Document.class, id);
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return document;
	}

	public void storeDocument(Document document) {
		Session session = null;
		try{
			session = sessionFactory.openSession();
			session.save(document);
		} catch(Exception ex){
			ex.printStackTrace();
		} finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

}
