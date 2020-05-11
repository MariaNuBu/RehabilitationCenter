package pojos;
import java.sql.Date;
import java.sql.Time;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import xml.utils.SQLDateAdapter;

import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"date","time","pat","doc","pt"})
public class Appointment implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlTransient
	private Integer id;
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date;
	@XmlElement
	private Time time;
	@XmlTransient
	private Patient pat;
	@XmlElement
	private Doctor doc;
	@XmlElement
	private PhysicalTherapist pt;

	public Appointment() {
		super();
	}

	public Appointment(Integer id, Date date, Time time) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
	}

	public Appointment(Date date, Time time) {
		super();
		this.date = date;
		this.time = time;
	}

	public Appointment(Integer id, Date date, Time time, Patient pat, Doctor doc, PhysicalTherapist pt){
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.pat = pat;
		this.doc = doc;
		this.pt = pt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}

	public Patient getPat() {
		return pat;
	}

	public void setPat(Patient pat) {
		this.pat = pat;
	}

	public Doctor getDoc() {
		return doc;
	}

	public void setDoc(Doctor doc) {
		this.doc = doc;
	}

	public PhysicalTherapist getPt() {
		return pt;
	}

	public void setPt(PhysicalTherapist pt) {
		this.pt = pt;
	}

	@Override
	public String toString() {
		return "Appointment [ID=" + id + ", Date=" + date + ", Time=" + time + ", Patient=" + pat.getName()+ ", "+ pat.geteMail() + ", PhysicalTherapist ="
				+ pt.getName() + ", "+ pt.geteMail()+"]";
	}







}