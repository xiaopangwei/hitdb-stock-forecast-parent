package cn.hitdb.enrich.f2k;

import cn.hitdb.enrich.f2k.model.CsvFileConfig;
import cn.hitdb.enrich.f2k.operator.CsvSourceFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestRamdomAccessFile {
    RandomAccessFile file        = null;
    FileChannel      fileChannel = null;
    ByteBuffer       byteBuffer  = ByteBuffer.allocate(25);


    @Before
    public void init() {
        try {
            file = new RandomAccessFile("/Users/weihuang/Desktop/testFile/csv.data", "r");
            fileChannel = file.getChannel();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPos() {
        try {
            fileChannel.position(0);
            int  count      = 10;
            long currentPos = 0;
            while ((count--) > 0) {
                int ret = fileChannel.read(byteBuffer);
                currentPos += ret;
                byte[] bytesArray = byteBuffer.array();
                System.err.println("count" + count + "---->" + "size:" + ret);

                System.err.println("count" + count + "-->" + currentPos);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void autoWire() throws Exception {
        fileChannel.position(0);
        int queryCount = 10;

        long   currentPos            = 0;
        byte[] targetArray           = new byte[50];
        int    lineBufferSize        = 50;
        int    readBufferSize        = 25;
        int    targetArrayActualSize = 0;
        int    remained              = 0;
        while ((queryCount--) > 0) {
            int ret = fileChannel.read(byteBuffer);
            currentPos += ret;
            byte[] bytesArray = byteBuffer.array();
            int    start      = 0;
            int    end        = 0;
            if (targetArrayActualSize + readBufferSize < lineBufferSize) {
                copyArray(bytesArray, 0, targetArray, targetArrayActualSize, readBufferSize);
                targetArrayActualSize += readBufferSize;
            } else {
                copyArray(bytesArray, 0, targetArray, targetArrayActualSize, targetArray.length - targetArrayActualSize);
                remained = -(lineBufferSize - readBufferSize - targetArrayActualSize);
            }
            for (int i = 0; i < lineBufferSize; i++) {
                if (targetArray[i] == '\n') {
                    end = i;

                    while (targetArray[start]==0){
                        start++;
                    }
                    System.out.println("[" + start + "," + end + "]");

                    printArray(targetArray, start, end);
                    start = end + 1;

                }
                copyArray(targetArray, start, targetArray, 0, lineBufferSize - start);
                targetArrayActualSize = (lineBufferSize - start);
                for (int j = targetArrayActualSize; j < lineBufferSize; j++) {
                    targetArray[j] = 0;
                }
//                System.out.println();
            }


            if (remained > 0) {
                copyArray(bytesArray, 25 - remained, targetArray, targetArrayActualSize, remained);
                targetArrayActualSize += remained;
            }
            byteBuffer.clear();
        }

    }

    public void copyArray(byte[] source, int sourceOffset, byte[] target, int targetOffset, int length) {
        for (int i = 0; i < length; i++) {
            target[targetOffset + i] = source[sourceOffset + i];
        }
    }

    @Test
    public void testCopyArray() {
        byte[] bytes1 = new byte[]{1, 2, 3, 4, 5};
        byte[] bytes2 = new byte[10];
        copyArray(bytes1, 0, bytes2, 3, 4);
        for (int i = 0; i < bytes2.length; i++) {
            System.out.println(i + " " + bytes2[i]);
        }
    }

    public void printArray(byte[] array, int i, int j) {
        for (int k = i; k <= j; k++) {
            System.err.print((char) array[k]);
        }
    }

    @Test
    public void testCSVSource() throws Exception{
        StreamExecutionEnvironment executionEnvironment=StreamExecutionEnvironment.getExecutionEnvironment();
        CsvFileConfig csvFileConfig=new CsvFileConfig(new byte[]{'\n'},"UTF-8", new URL("file:///Users/weihuang/Desktop/csvdemo.csv"));
        executionEnvironment.addSource(new CsvSourceFunction(csvFileConfig)).print();
        executionEnvironment.execute();
    }

}
