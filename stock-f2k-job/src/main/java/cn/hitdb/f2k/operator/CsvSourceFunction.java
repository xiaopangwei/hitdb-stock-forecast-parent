package cn.hitdb.f2k.operator;

import cn.hitdb.f2k.model.CsvFileConfig;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class CsvSourceFunction implements SourceFunction<byte[]>, CheckpointedFunction {
    private          long          bytePos       = 0;
    private          CsvFileConfig csvFileConfig = null;
    private volatile boolean       isRunning     = false;

    private RandomAccessFile file;

    private CsvSourceFunction(CsvFileConfig csvFileConfig) throws FileNotFoundException {
        this.csvFileConfig = csvFileConfig;
        file = new RandomAccessFile(csvFileConfig.getResourcePath().getPath(), "r");
        bytePos =0;
    }

    public void run(SourceContext<byte[]> ctx) throws Exception {

        FileChannel fileChannel = file.getChannel();
        fileChannel.position(0);
        while (isRunning) {
            synchronized (ctx.getCheckpointLock()) {
                fileChannel.position(bytePos);

            }
        }
    }

    public void cancel() {

    }

    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    public void initializeState(FunctionInitializationContext context) throws Exception {

    }
}
