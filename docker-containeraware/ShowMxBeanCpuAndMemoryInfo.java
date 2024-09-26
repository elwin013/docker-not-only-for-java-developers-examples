import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class ShowMxBeanCpuAndMemoryInfo {
    public static void main(String[] args) {
        var osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.printf("Runtime.availableProcessors: %d%n", Runtime.getRuntime().availableProcessors());
        System.out.printf("OperatingSystemMXBean.getAvailableProcessors: %d%n", osBean.getAvailableProcessors());
        System.out.printf("OperatingSystemMXBean.getFreeMemorySize: %d%n", osBean.getFreeMemorySize());
        System.out.printf("OperatingSystemMXBean.getTotalPhysicalMemorySize: %d%n", osBean.getTotalMemorySize());
        System.out.printf("OperatingSystemMXBean.getFreeSwapSpaceSize: %d%n", osBean.getFreeSwapSpaceSize());
        System.out.printf("OperatingSystemMXBean.getTotalSwapSpaceSize: %d%n", osBean.getTotalSwapSpaceSize());
        System.out.printf("OperatingSystemMXBean.getCpuLoad: %f%n", osBean.getCpuLoad());
    }
}
