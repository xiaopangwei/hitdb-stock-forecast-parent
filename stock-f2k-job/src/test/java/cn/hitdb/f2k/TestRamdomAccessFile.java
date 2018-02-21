package cn.hitdb.f2k;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestRamdomAccessFile {
    RandomAccessFile file        = null;
    FileChannel      fileChannel = null;
    ByteBuffer       byteBuffer  = ByteBuffer.allocate(20);


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
        int    count                 = 10;
        long   currentPos            = 0;
        byte[] targetArray           = new byte[50];
        int    targetArrayActualSize = 0;
        while ((count--) > 0) {
            int ret = fileChannel.read(byteBuffer);
            currentPos += ret;
            byte[] bytesArray = byteBuffer.array();

            if (targetArrayActualSize + ret <= targetArray.length) {
                copyArray(bytesArray, 0, targetArray, targetArrayActualSize, ret);
                targetArrayActualSize += ret;



                int start = 0;
                int end   = 0;
                for (int i = 0; i < targetArray.length; i++) {
                    if (targetArray[i] == '\n') {
                        end = i;
                        System.out.println("send1 "+start + "  " + end);
                        //发送[start,end]
//                            for (int j = start; j <= end; j++) {
//                                System.out.print("send: " + (int) targetArray[j] + " ");
//                            }
                        start = end + 1;
                    }
                }
                targetArrayActualSize = targetArray.length - end - 1;
                copyArray(targetArray, end + 1, targetArray, 0, targetArrayActualSize);

                for (int i = targetArrayActualSize; i < targetArray.length; i++) {
                    targetArray[i] = 0;
                }

            } else {
                int remained = targetArray.length - targetArrayActualSize;
                copyArray(bytesArray, 0, targetArray, targetArrayActualSize, remained);
                targetArrayActualSize += remained;

//                if (targetArrayActualSize >= targetArray.length) {


                    int start = 0;
                    int end   = 0;
                    for (int i = 0; i < targetArray.length; i++) {
                        if (targetArray[i] == '\n') {
                            end = i;
                            System.out.println("send2 "+start + "  " + end);
                            //发送[start,end]
//                            for (int j = start; j <= end; j++) {
//                                System.out.print("send: " + (int) targetArray[j] + " ");
//                            }
                            start = end + 1;
                        }
                    }
                    targetArrayActualSize = targetArray.length - end - 1;
                    copyArray(targetArray, end + 1, targetArray, 0, targetArrayActualSize);

                    for (int i = targetArrayActualSize; i < targetArray.length; i++) {
                        targetArray[i] = 0;
                    }

                //补全剩下的
                if (remained > 0)
                {

                    int len=bytesArray.length;
                    for (int i = 0; i < remained; i++) {
                       targetArray[targetArrayActualSize+i]=bytesArray[len-i-1];
                    }
                    targetArrayActualSize+=remained;
                    remained=0;

                }


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

}
