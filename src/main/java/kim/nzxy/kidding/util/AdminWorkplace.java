package kim.nzxy.kidding.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 超管专用工具类
 *
 * @author ly-chn
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminWorkplace {
    private static final List<String> yesList = Arrays.asList("yes", "是", "确认", "1");
    private final ThreadPoolTaskExecutor executor;


    /**
     * 读取一行输入, 会在超时后自动关闭输入流
     *
     * @param tips    提示信息, 不能为空
     * @param timeout 超时时间
     * @return 读取到的一行输入
     */
    public String readLine(String tips, Duration timeout) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(tips + " (超时时间: " + timeout.getSeconds() + "s)");
            Future<String> future = executor.submit(scanner::nextLine);
            return future.get(timeout.getSeconds(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("录入失败, return null");
            return null;
        }
    }

    public boolean readBool(String tips, Duration timeout) {
        return yesList.contains(readLine(tips, timeout));
    }
}
