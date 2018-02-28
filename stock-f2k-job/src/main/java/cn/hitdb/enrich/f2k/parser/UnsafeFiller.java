package cn.hitdb.enrich.f2k.parser;

import cn.hitdb.enrich.core.model.message.BaseMessage;
import cn.hitdb.enrich.core.model.message.TickMessage;
import cn.hitdb.enrich.f2k.common.ConvertUtils;
import cn.hitdb.enrich.f2k.model.Constant;

import org.apache.commons.collections.map.HashedMap;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/23
 * @time 上午12:04
 */
public class UnsafeFiller {
    private static Unsafe            unsafe             = null;
    private static Map<String, Long> fieldNameOffsetMap = new TreeMap<>();
    private static List<Long> offsets;

    static {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fieldNameOffsetMap = new HashedMap();
        getMessageOffset();
        offsets=new ArrayList<>();

    }


    public static void getMessageOffset() {
        List<String> fieldNameList = SerializerHelper.getAllFieldName();
        for (String item : fieldNameList) {
            Field[] fields = TickMessage.class.getFields();
            for (Field field : fields) {
                if (item.equals(field.getName())) {
                    long offset = unsafe.objectFieldOffset(field);
                    fieldNameOffsetMap.put(item, offset);
                }
            }
        }
    }

    public static void putIntValue(TickMessage message, long offset, int value) {
        unsafe.putInt(message, offset, value);
    }

    public static void putIntValue(TickMessage message, String fieldName, int value) {
        unsafe.putInt(message, fieldNameOffsetMap.get(fieldName), value);
    }

    public static void putRealValue(TickMessage message, long offset, double value) {
        unsafe.putDouble(message, offset, value);
    }

    public static void putRealValue(TickMessage message, String fieldName, double value) {
        unsafe.putDouble(message, fieldNameOffsetMap.get(fieldName), value);
    }

    public static void putObjValue(TickMessage message, long offset, Object value) {
        unsafe.putObject(message, offset, value);
    }

    public static void putObjValue(TickMessage message, String fieldName, Object value) {
        unsafe.putObject(message, fieldNameOffsetMap.get(fieldName), value);
    }

    public static byte[] toBytes(Class<? extends BaseMessage> msgClass, BaseMessage msg) {
        int          offset        = 0;
        byte[]       result        = new byte[1024];
        List<String> fieldNameList = SerializerHelper.getAllFieldName();
        for (String item : fieldNameList) {
            Field[] fields = msgClass.getFields();
            for (Field field : fields) {
                if (item.equals(field.getName())) {
                    Class  classType  = field.getType();
                    Object fieldValue = null;
                    try {
                        fieldValue = field.get(msg);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (Constant.INT_CLASS.equals(classType)) {
                        byte[] intArray = ConvertUtils.int2Bytes((Integer) fieldValue);
                        System.arraycopy(intArray, 0, result, offset, Constant.INT_SIZE_IN_BYTE);
                        offset += Constant.INT_SIZE_IN_BYTE;
                    } else if (Constant.DOUBLE_CLASS.equals(classType)) {
                        byte[] doubleArray = ConvertUtils.double2Bytes((Double) fieldValue);
                        System.arraycopy(doubleArray, 0, result, offset, Constant.DOUBLE_SIZE_IN_BYTE);
                        offset += Constant.DOUBLE_SIZE_IN_BYTE;
                    } else {
                        try {
                            byte[] objArray = ((String) fieldValue).getBytes(Constant.DEFAULT_CHARSET);
                            System.arraycopy(objArray, 0, result, offset, objArray.length);
                            offset += objArray.length;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                }
            }
        }
        return null;

    }


    public static byte[] buildMessage(Class<? extends BaseMessage> msgClass,byte[] bytes) {

        return null;
    }

}
