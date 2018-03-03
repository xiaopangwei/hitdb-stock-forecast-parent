package cn.hitdb.forecast.core;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/26
 * @time 下午8:28
 */
public abstract class AbstractBootstrap {
    public void startup(String[] args) {

    }

    public abstract void execute();

    public abstract void shutdownHook();
}
