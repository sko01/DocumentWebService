package ua.lviv.sko01.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
public class DocumentDTO {
	@XmlElement(name = "documentId")
	private Integer id;
	
	@XmlElement(name = "pdfOutput")
	private byte[] data;
	
	@XmlElement(name = "errorMsg")
	private List<String> errors;

	public DocumentDTO() {
		this.errors = new ArrayList<String>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
