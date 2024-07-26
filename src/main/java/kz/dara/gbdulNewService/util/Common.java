package kz.dara.gbdulNewService.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Common {
    public static XMLGregorianCalendar getCurrentDate(){
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    public static XMLGregorianCalendar parseDate(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertToDate(XMLGregorianCalendar xmlGregCal) {
        if (xmlGregCal != null) {
            return xmlGregCal.toGregorianCalendar().getTime();
        }
        return null;
    }
    public static XMLGregorianCalendar parseDateFromString(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        GregorianCalendar gc = new GregorianCalendar();

        try {
            gc.setTime(formatter.parse(date));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException | ParseException e) {
            return null;
        }
    }

    public static XMLGregorianCalendar convertIinToDate(String iin){
        // get year
        String year = iin.substring(0, 2);
        if (year.charAt(0) == '0') {
            year = "20" + year;
        } else {
            year = "19" + year;
        }
        String month = iin.substring(2, 4);
        String day = iin.substring(4, 6);

        String s = day + "/" + month + "/" + year;
        try {
            Date birthday = new SimpleDateFormat("dd/MM/yyyy").parse(s);
            return parseDate(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String trimXml(String xml) {
        BufferedReader reader = new BufferedReader(new StringReader(xml));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ( (line = reader.readLine() ) != null) {
                result.append(line.trim());
            }
            return String.valueOf(result);
        } catch (IOException e) {
            throw new RuntimeException("Error occured while trimming xml", e);
        }
    }
}
