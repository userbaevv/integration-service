package kz.dara.gbdulNewService.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * Date Util class
 */
public final class DateUtil {

    /**
     * Get current xml gregorian calendar
     *
     * @return current gregorian calendar
     * @throws DatatypeConfigurationException exception datatype
     */
    public static XMLGregorianCalendar getCurrentXMLGregorianCalendar() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(gregorianCalendar);
    }
}
