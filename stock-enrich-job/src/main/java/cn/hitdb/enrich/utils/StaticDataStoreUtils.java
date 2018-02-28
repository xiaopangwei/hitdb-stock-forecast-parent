package cn.hitdb.enrich.utils;

import cn.hitdb.enrich.datastore.BaseDataStore;
import cn.hitdb.enrich.datastore.ChronicleDataStore;
import cn.hitdb.enrich.datastore.StaticDataProxy;
import cn.hitdb.enrich.model.StaticDataField;
import cn.hitdb.enrich.model.StaticTableSchema;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午11:15
 */
public class StaticDataStoreUtils {
    private static long getCsvRowNums(URL csvURL) throws IOException {
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        MappingIterator<String[]> it  = mapper.readerFor(String[].class).readValues(csvURL);
        long                      num = 0;
        while (it.hasNext()) {
            it.next();
            num++;
        }
        return num;
    }

    public static BaseDataStore generateBin(URL csvURL, File dataStoreFile, int keySizeInByte,
                                            StaticTableSchema staticTableSchema) throws IOException {
        BaseDataStore dataStore = new ChronicleDataStore();
        dataStore.create(dataStoreFile, keySizeInByte, staticTableSchema.getTotalSize(), getCsvRowNums(csvURL));
        StaticDataProxy proxy  = new StaticDataProxy(staticTableSchema);
        CsvMapper       mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        MappingIterator<String[]> it  = mapper.readerFor(String[].class).readValues(csvURL);
        long                      num = 0;
        while (it.hasNext()) {
            proxy.newData();
            String[] row = it.next();
            String   key = row[0];
            for (int i = 0; i < staticTableSchema.getFields().length; i++) {
                StaticDataField[] fields = staticTableSchema.getFields();
                proxy.setBytes(fields[i], row[i + 1].getBytes("UTF-8"));
            }
            dataStore.put(key.getBytes("UTF-8"), proxy.getDataBuffer().array());
        }
        return dataStore;
    }
    public static BaseDataStore generateBin(String csvURL, String dataStoreFile, int keySizeInByte,
                                            StaticTableSchema staticTableSchema) throws IOException {
       return generateBin(new File(csvURL).toURI().toURL(),new File(dataStoreFile),keySizeInByte,staticTableSchema);
    }
}
