package cn.hitdb.f2k;

import org.junit.Before;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnsafe {
    private byte[] sourceByteArray=new byte[10];
    private byte[] targetByteArray=new byte[10];

    @Before
    public void init(){
        for(int i=0;i<sourceByteArray.length;i++){
            sourceByteArray[i]=(byte) (48+i);
        }

    }
    @Test
    public void unsafeCopy() throws Exception{
        Field field=Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe=(Unsafe)field.get(null);
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
}
