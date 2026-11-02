package model;

import java.io.Serializable;
import java.util.Date;

public class AbstractEntity implements Serializable  {
	
	private int id;
	protected Date dateCreation;
	protected Date dateModification;
	
	public AbstractEntity(int id, Date dateCreation, Date dateModification) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}
	public AbstractEntity(Date dateCreation, Date dateModification) {
		super();
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}
	public AbstractEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateModification() {
		return dateModification;
	}
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}
	
	@Override
	public String toString() {
		return "           Date Creation : "+dateCreation
				+",\r\n           Date Modification : "+dateModification;
	}

}
