package cn.hitdb.enrich.datastore;

import net.openhft.chronicle.map.ChronicleMap;

import java.io.File;
import java.io.IOException;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午3:34
 */
public class ChronicleDataStore implements BaseDataStore {
    private ChronicleMap<byte[], byte[]> map;

    @Override
    public BaseDataStore create(File dataStoreFile, int keySize, int valueSize, long entries) {
        dataStoreFile.delete();
        try {
            map = ChronicleMap.of(byte[].class, byte[].class)
                    .averageKeySize(keySize)
                    .averageValueSize(valueSize)
                    .entries(entries)
                    .createPersistedTo(dataStoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public BaseDataStore open(File dataStoreFile) {
        try {
            map = ChronicleMap.of(byte[].class, byte[].class).createPersistedTo(dataStoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void close() {
        map.close();
    }

    @Override
    public BaseDataStore put(byte[] key, byte[] value) {
        map.put(key, value);
        return this;
    }

    @Override
    public byte[] get(byte[] key) {
        return map.get(key);
    }
}
