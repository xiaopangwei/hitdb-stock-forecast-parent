package cn.hitdb.f2k;

import cn.hitdb.f2k.common.ConvertUtils;
import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.hash.serialization.BytesReader;
import net.openhft.chronicle.hash.serialization.BytesWriter;
import net.openhft.chronicle.hash.serialization.impl.SerializableReader;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import net.openhft.chronicle.values.Values;
import net.openhft.chronicle.wire.Marshallable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/24
 * @time 上午12:24
 */
public class TestChronicleMap {

    private static final ThreadLocal<LongValue> THREAD_LOCAL_LONGVALUE = ThreadLocal
            .withInitial(() -> Values.newHeapInstance(LongValue.class));

    @Test
    public void testCreate() throws Exception {

        int  maxEntry    = 10000;
        File persistFile = new File("/Users/weihuang/Desktop");
        File f           = File.createTempFile("persist", ".dat", persistFile);

        // create map
        // exceed max entry will increase to 3.0 * max entry
        ChronicleMapBuilder<String, LongValue> mapBuilder =
                ChronicleMapBuilder.of(String.class, LongValue.class)
                        .entries(maxEntry)
                        .maxBloatFactor(3.0)
                        .averageKeySize(10);

        ChronicleMap<String, LongValue> map = mapBuilder.createPersistedTo(f);
        for (int i = 0; i < 20 * maxEntry; i++) {
            LongValue v = THREAD_LOCAL_LONGVALUE.get();
            v.setValue(i);
            map.put(String.valueOf(i), v);
            if (i % 1000 == 1) {
                System.out.format("count[%d], max entry[%d],current size[%d]\n", i, maxEntry, map.longSize());
            }
            // comment following if, will throw overflow exception
            if (i >= maxEntry - 1) {
//                System.out.println("overflow");
                while (true) {
                    String removeKey = String.valueOf(ThreadLocalRandom.current().nextInt(i));
                    if (null != map.remove(removeKey) || map.size() == 0) {
                        break;
                    }
                }
            }

        }
        map.close();
    }

    @Test
    public void testGet() throws Exception {

        File persistFile = new File("/Users/weihuang/Desktop");
        File f           = File.createTempFile("persist", ".dat", persistFile);

        // create map
        // exceed max entry will increase to 3.0 * max entry
        ChronicleMapBuilder<byte[], byte[]> mapBuilder =
                ChronicleMapBuilder.of(byte[].class, byte[].class)
                        .averageKeySize(4)
                        .averageValueSize(4)
                        .maxBloatFactor(3)
                        .entries(1000)
//                        .keyMarshallers(new BytesReader<byte[]>() {
//                            @NotNull
//                            @Override
//                            public byte[] read(Bytes bytes, @Nullable byte[] bytes2) {
//                                System.err.println("read1 " + ConvertUtils.byte2Int(bytes2));
//                                return new byte[0];
//                            }
//                        }, new BytesWriter<byte[]>() {
//                            @Override
//                            public void write(Bytes bytes, @NotNull byte[] bytes2) {
//                                bytes2[0] = 0;
//                                System.err.println("write1 " + ConvertUtils.byte2Int(bytes2));
//                            }
//                        });
                        .valueMarshallers(new BytesReader<byte[]>() {
                            @NotNull
                            @Override
                            public byte[] read(Bytes bytes, @Nullable byte[] bytes2) {
                                System.err.println("read2 " + ConvertUtils.byte2Int(bytes2));
                                return new byte[0];
                            }
                        }, new BytesWriter<byte[]>() {
                            @Override
                            public void write(Bytes bytes, @NotNull byte[] bytes2) {
                                System.err.println("write2 " + ConvertUtils.byte2Int(bytes2));
                            }
                        });

        ChronicleMap<byte[], byte[]> map = mapBuilder.createPersistedTo(f);
        for (int i = 0; i < 3; i++) {

            byte[] byteArray1 = ConvertUtils.int2Bytes(i);
            byte[] byteArray2 = ConvertUtils.int2Bytes(i + 1);
            map.put(byteArray1, byteArray2);
//            map.get(byteArray1);
        }
//        byte[] key=ConvertUtils.int2Bytes(300);
//        System.out.println(ConvertUtils.byte2Int(map.get(key)));
        map.close();
    }

    @Test
    public void testBigData() {

    }
}
