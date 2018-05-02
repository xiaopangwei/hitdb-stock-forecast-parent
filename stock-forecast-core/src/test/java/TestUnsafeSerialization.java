import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/3/4
 * @time 上午1:11
 */
public class TestUnsafeSerialization {
    private Unsafe unsafe;

    @Before
    public void getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Demo {
        private String name;
        private int    age;
        private long   id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", id=" + id +
                    '}';
        }
    }

    @Test
    public void testRead() throws Exception {
        long nameOffset = unsafe.objectFieldOffset(Demo.class.getDeclaredField("name"));
        long ageOffset  = unsafe.objectFieldOffset(Demo.class.getDeclaredField("age"));
        long idOffset   = unsafe.objectFieldOffset(Demo.class.getDeclaredField("id"));
        System.out.println("name:" + nameOffset + " age:" + ageOffset + " id:" + idOffset);
        Demo demo = new Demo();
        demo.setAge(10);
        demo.setName("HW");
        demo.setId(1L);

        System.out.println(unsafe.getObject(demo, nameOffset));
        System.out.println(unsafe.getInt(demo, ageOffset));
        System.out.println(unsafe.getLong(demo, idOffset));


    }

    @Test
    public void writeRead() throws Exception {
        Demo obj = (Demo) unsafe.allocateInstance(Demo.class);
        System.out.println(obj.getAge());
        System.out.println(obj.getId());
        System.out.println(obj.getName());
        obj.setId(1);
        obj.setName("HW");
        obj.setAge(18);
        System.out.println(obj);
    }

}
