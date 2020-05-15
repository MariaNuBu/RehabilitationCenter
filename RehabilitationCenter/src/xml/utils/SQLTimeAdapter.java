package xml.utils;
import java.sql.Time;

import javax.xml.bind.annotation.adapters.XmlAdapter;
public class SQLTimeAdapter extends XmlAdapter<String, Time> {
	@Override
	public String marshal(Time sqlTime) throws Exception {
		return sqlTime.toString();
	}

	@Override
	public Time unmarshal(String timeString) throws Exception {
		return Time.valueOf(timeString);
	}

}
