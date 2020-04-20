package pojos;

import java.io.Serializable;

public class Treatment  implements Serializable  {
	private static final long serialVersionUID = 1L;
    private Integer id ;
    private String type ;
    private Integer lenght ; 
    
    public Treatment() {
    	super();
    }
  public Treatment(Integer  id, String type, Integer lenght) {
	  this.id=id ;
	  this.type=type;
	  this.lenght=lenght;
	  
  }
  public Treatment(String type, Integer lenght) {
	  this.type = type;
	  this.lenght = lenght;
  }
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public Integer getLenght() {
	return lenght;
}
public void setLenght(Integer lenght) {
	this.lenght = lenght;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((lenght == null) ? 0 : lenght.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
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
	Treatment other = (Treatment) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (lenght == null) {
		if (other.lenght != null)
			return false;
	} else if (!lenght.equals(other.lenght))
		return false;
	if (type == null) {
		if (other.type != null)
			return false;
	} else if (!type.equals(other.type))
		return false;
	return true;
}
  
  
}
