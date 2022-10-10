package base;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CsvDataProviders {

    /** Provides data to a test method from some csv file data provider **/
    @DataProvider(name = "csvReader")
    public static Iterator<Object[]> csvReader(Method method) {
        List<Object[]> list = new ArrayList<>();
        String pathname = "src" + File.separator
                + "test" + File.separator
                + "resources" + File.separator
                + "dataProviders" + File.separator
                + method.getName() + ".csv";
        File file = new File(pathname);
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] keys = reader.readNext();
            if (keys != null) {
                String[] dataParts;
                while ((dataParts = reader.readNext()) != null) {
                    Map<String, String> testData = new HashMap<>();
                    for (int i = 0; i < keys.length; i++) {
                        testData.put(keys[i], dataParts[i]);
                    }
                    list.add(new Object[] { testData });
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + pathname + " was not found.\n" + e.getStackTrace());
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Could not read " + pathname + " file.\n" + e.getStackTrace());
        }

        return list.iterator();
    }
}

