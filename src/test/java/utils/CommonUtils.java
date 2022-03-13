package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CommonUtils {

    public static String getProjectPath(){
        return System.getProperty("user.dir");
    }
    public static String dateToString(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date currentDate(){
        return new Date();
    }

    public static String prettyPrintJson(Object json) throws JsonProcessingException {
        return  new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    public static Properties readProperties(String path) throws IOException {
        FileReader reader=new FileReader(path);
        Properties p=new Properties();
        p.load(reader);
        return p;
    }

    public static Properties getSettings() throws IOException {
        return readProperties(Constants.SETTINGS_PROPERTIES_PATH);
    }
}
