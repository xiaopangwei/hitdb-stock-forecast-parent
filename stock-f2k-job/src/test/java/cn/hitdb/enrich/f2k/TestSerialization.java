package cn.hitdb.enrich.f2k;

import cn.hitdb.enrich.f2k.common.ConvertUtils;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/24
 * @time 上午2:57
 */
public class TestSerialization {
    private Unsafe unsafe;

    class DemoBean {
        int    age;
        String name;
        double income;

        public DemoBean() {
        }

        public DemoBean(int age, String name, double income) {
            this.age = age;
            this.name = name;
            this.income = income;


        }

        @Override
        public String toString() {
            return "DemoBean{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", income=" + income +
                    '}';
        }


        public byte[] toBytes() throws Exception {
            byte[] result = catArray(ConvertUtils.int2Bytes(age)
                    , convertChar2Byte(this.name.toCharArray())
                    , ConvertUtils.double2Bytes(11.24));
            for (int i = 0; i < result.length; i++) {
                System.err.println("index:" + i + " " + result[i]);
            }
            return result;
        }

    }

    @Before
    public void init() throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe = (Unsafe) field.get(null);
    }

    @Test
    public void testGetFieldOffset() throws Exception {
        DemoBean bean1  = new DemoBean();
        DemoBean bean2  = new DemoBean();
        Field    age    = DemoBean.class.getDeclaredField("age");
        Field    name   = DemoBean.class.getDeclaredField("name");
        Field    income = DemoBean.class.getDeclaredField("income");

        //12
        System.out.println("age:" + unsafe.objectFieldOffset(age));
        //24
        System.out.println("name:" + unsafe.objectFieldOffset(name));
        //16
        System.out.println("income:" + unsafe.objectFieldOffset(income));

        unsafe.putInt(bean1, 16, 18);
        unsafe.putObject(bean1, 24, "HW");
        unsafe.putDouble(bean1, 16, 8001.23);
        System.out.println(bean1);


    }


    public byte[] catArray(byte[]... array) {
        int length = 0;
        for (byte[] item : array) {
            length += item.length;
        }
        byte[] base   = new byte[length];
        int    offset = 0;
        for (int i = 0; i < array.length; i++) {
            byte[] temp = array[i];
            System.arraycopy(temp, 0, base, offset, temp.length);
            offset += temp.length;
        }
        return base;
    }

    @Test
    public void testCatArray() {
        byte[] result = catArray(new byte[]{1, 2, 3}, new byte[]{4, 5, 6}, new byte[]{7, 8});
        for (byte item : result) {
            System.out.println(item);
        }
    }

    @Test
    public void testCopyByUnsafe() throws Exception {
        DemoBean bean = new DemoBean(18, "HW", 11.2345);
        bean.toBytes();


    }

    public byte[] convertChar2Byte(char[] in) {
        byte[] output = new byte[in.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = (byte) in[i];
        }
        return output;
    }

    @Test
    public void compare() throws Exception {
        String name = "HW1234567899";
        char[] aa   = name.toCharArray();
        for (int i = 0; i < aa.length; i++) {
            System.err.println("1:" + (byte)aa[i]);
        }

        byte[] bytes = name.getBytes("utf-8");
        for (int i = 0; i < bytes.length; i++) {
            System.err.println("2:" + bytes[i]);
        }
    }
    @Test
    public void testCopyMem(){
        byte[] bytes1=new byte[]{1,2,3,4,5};
        byte[] bytes2=new byte[5];
        
//        unsafe.copyMemory(bytes1,);
    }
}
