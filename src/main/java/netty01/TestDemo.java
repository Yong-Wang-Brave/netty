package netty01;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.logging.log4j.core.util.JsonUtils;

public class TestDemo {
    public static void main(String[] args) {
        int max = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(max);

    }
}
