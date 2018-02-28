package cn.hitdb.enrich.datastore;

import java.io.File;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午3:36
 */
public interface BaseDataStore {
    BaseDataStore create(File dataStoreFile, int keySize, int valueSize, long entries);

    BaseDataStore open(File dataStoreFile);

    void close();

    BaseDataStore put(byte[] key, byte[] value);

    byte[] get(byte[] key);

}
