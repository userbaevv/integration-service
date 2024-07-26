package kz.dara.gbdulNewService.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        return xmlCalendar;
    }
}
