package cn.hitdb.enrich.f2k.parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/23
 * @time 上午12:40
 */
public class SerializerHelper {
    private static final String       recordConfigName = "stock.rdconfig";
    private static       String       path             = null;
    private static       List<String> fieldNameList    = null;

    static {
        URL    rdconfigURL = SerializerHelper.class.getClassLoader().getResource(recordConfigName);
        String path        = rdconfigURL.getPath();
        fieldNameList = new ArrayList<String>();
    }

    public static Multimap<Integer, String> buildMessageConfig() throws IOException {
        try {
            Multimap<Integer, String> multimap        = ArrayListMultimap.create();
            RandomAccessFile          file            = new RandomAccessFile(path, "r");
            String                    lineContent     = null;
            int                       currentReadLine = 0;
            while ((lineContent = file.readLine()) != null) {

                String[] fieldProps = lineContent.split("\\s+");
                String   fieldName  = fieldProps[0];
                String   fieldType  = fieldProps[1];
                multimap.put(currentReadLine, fieldName);
                multimap.put(currentReadLine, fieldType);
                fieldNameList.add(fieldName);
                currentReadLine++;
            }
            return multimap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getAllFieldName() {
        return fieldNameList;
    }



}
