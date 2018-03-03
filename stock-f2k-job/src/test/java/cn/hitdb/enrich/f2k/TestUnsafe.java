package cn.hitdb.enrich.f2k;

import cn.hitdb.forecast.core.model.message.StockTickMessage;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Date;

public class TestUnsafe {
    private byte[] sourceByteArray=new byte[10];
    private byte[] targetByteArray=new byte[10];

    private Unsafe unsafe;
    @Before
    public void init() throws Exception{
        for(int i=0;i<sourceByteArray.length;i++){
            sourceByteArray[i]=(byte) (48+i);
        }

        Field field=Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe=(Unsafe)field.get(null);
    }
    @Test
    public void unsafeCopy() throws Exception{

        for(byte item:sourceByteArray){
            System.out.println((char) (item));
        }

        int baseOffset=unsafe.arrayBaseOffset(byte[].class);
        System.out.println(baseOffset);
        unsafe.copyMemory(sourceByteArray, baseOffset,targetByteArray,baseOffset,10);
        for(byte item:targetByteArray){
            System.out.println((char) (item));
        }
    }

    @Test
    public void testPutValue() throws Exception{

        StockTickMessage message =new StockTickMessage();
        message.setMarketCode("111");
        Field[] fields=StockTickMessage.class.getDeclaredFields();

        Field field= null;
        field=StockTickMessage.class.getSuperclass().getDeclaredField("marketCode");
        field.setAccessible(true);
        long marketOffset=unsafe.objectFieldOffset(field);
        System.out.println(marketOffset);
    }

    @Test
    public void testReflect(){
        Field[] fields=StockTickMessage.class.getFields();
        for(Field item:fields){
            System.out.println(item.getName());
        }
    }

    class Stub{
        public int    age;
        public String name;
        public  double salary;
        public  Date   date;
    }
    @Test
    public void testFieldType(){
        try {
            Class<?> class1=Stub.class.getField("age").getType();
            if(class1.equals(int.class)){
                System.out.println("equals");
            }
            if(class1.equals(double.class)){
                System.out.println("not equals");
            }
            System.out.println(class1);
            System.out.println(Stub.class.getField("name").getType());
            System.out.println(Stub.class.getField("salary").getType());
            System.out.println(Stub.class.getField("date").getType());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
