package pojos;
import java.sql.Date;
import java.sql.Time;
import java.io.Serializable;

public class Appointment implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date date;
	private Time time;

	public Appointment() {
		super();
	}

	public Appointment(Integer id, Date date, Time time) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
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



}