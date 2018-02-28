package cn.hitdb.enrich.f2k.operator;

import cn.hitdb.enrich.f2k.model.CsvFileConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CsvSourceFunction extends RichSourceFunction<byte[]> implements CheckpointedFunction, Serializable{
    private          CsvFileConfig    csvFileConfig    = null;
    private volatile boolean          isRunning        = false;
    private          int              sumRevCount      = 0;
    private          ByteBuffer       lineBuffer       = null;
    private          ByteBuffer       readBuffer       = null;
    private          FileChannel      fileChannel      = null;
    private          RandomAccessFile file             = null;
    private final    int              LINE_BUFFER_SIZE = 1024;
    private final    int              READ_BUFFER_SIZE = 5;

    public CsvSourceFunction(CsvFileConfig csvFileConfig) throws FileNotFoundException {
        this.csvFileConfig = csvFileConfig;
        file = new RandomAccessFile(csvFileConfig.getResourcePath().getPath(), "r");
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        fileChannel = file.getChannel();


    }

    private static void byteArrayCopy(byte[] src, int srcPos, byte[] dst, int dstPos, int length) {
        for (int i = 0; i < length; i++) {
            dst[dstPos + i] = src[srcPos + i];
        }
    }

    public void run(SourceContext<byte[]> ctx) throws Exception {
        byte[] readBufferArray = readBuffer.array();
        byte[] lineBufferArray = lineBuffer.array();

        while (isRunning) {
            readBuffer.position(0);
            int readSize = fileChannel.read(readBuffer);
            if (readSize > 0) {
                int lastReadBufferLineEndingPos = -1;
                for (int i = 0; i < readSize; i++) {
                    if (readBufferArray[i] == '\n')
                    {
                        int lineBufferSize = lineBuffer.position();
                        int lineSize       = lineBufferSize + i - lastReadBufferLineEndingPos - 1;
                        if (lineSize > 0) {
                            byte[] line = new byte[lineSize];
                            byteArrayCopy(lineBufferArray, 0, line, 0, lineBufferSize);
                            byteArrayCopy(readBufferArray, lastReadBufferLineEndingPos + 1,
                                    line, lineBufferSize, i - lastReadBufferLineEndingPos - 1);

                            synchronized (ctx.getCheckpointLock()) {

                                ctx.collect(line);
                            }
                            lineBuffer.position(0);

                        }
                        lastReadBufferLineEndingPos = i;
                    }
                }
                if (lastReadBufferLineEndingPos < readSize - 1) {
                    int lineBufferPos = lineBuffer.position();
                    int length        = readSize - lastReadBufferLineEndingPos - 1;
                    byteArrayCopy(readBufferArray, lastReadBufferLineEndingPos + 1, lineBufferArray, lineBufferPos, length);
                    lineBuffer.position(lineBufferPos + length);
                }

            }

        }
    }

    public void cancel() {
        isRunning = false;
    }

    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    public void initializeState(FunctionInitializationContext context) throws Exception {

    }
}
