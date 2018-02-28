package cn.hitdb.enrich.f2k.parser;

import cn.hitdb.enrich.core.model.message.TickMessage;
import cn.hitdb.enrich.f2k.model.CsvFileConfig;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * <p>Description: CSV转Kafka消息</p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/22
 * @time 下午9:44
 */
public class MsgBuilderFactory {

    private static MsgBuilderFactory         factory  = null;
    private static Multimap<Integer, String> fieldMap = null;

    private MsgBuilderFactory() {
        try {
            fieldMap = SerializerHelper.buildMessageConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MsgBuilderFactory getInstance() {

        if (factory == null) {
            synchronized (MsgBuilderFactory.class) {
                if (factory == null) {
                    factory = new MsgBuilderFactory();
                }
            }
        }
        return factory;
    }

    public TickMessage csv2MessageParser(String inputCSV, CsvFileConfig csvFileConfig) {
        try {
            String      delimeter = new java.lang.String(csvFileConfig.getLineDelimiter(), csvFileConfig.getEncoding());
            String[]    snippets  = inputCSV.split(delimeter);
            TickMessage msg       = new TickMessage();
            for (int i = 0; i < snippets.length; i++) {
                String fieldStrValue = snippets[i];

                String       strType   = ((ArrayList<String>) fieldMap.get(i)).get(1);
                CSVFieldType fieldType = CSVFieldType.getValueByName(strType);
                String       fieldName = ((ArrayList<String>) fieldMap.get(i)).get(0);

                if (fieldType != null) {
                    switch (fieldType) {
                        case STR:
                            UnsafeFiller.putObjValue(msg, fieldName, fieldStrValue);
                            break;
                        case REAL:
                            UnsafeFiller.putRealValue(msg, fieldName, Double.parseDouble(fieldStrValue));
                            break;
                        case INT:
                            UnsafeFiller.putIntValue(msg, fieldName, Integer.parseInt(fieldStrValue));
                            break;
                        case Date:
                            UnsafeFiller.putObjValue(msg, fieldName, fieldStrValue);
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> byte[] msg2Bytes(Class<T> msgClass){
        String clssName=msgClass.getClass().getName();
        return null;
    }
}
