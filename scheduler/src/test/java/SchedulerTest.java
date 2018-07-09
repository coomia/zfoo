import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * cron（译为克龙）代表100万年，是英文单词中最大的时间单位。
 * google（译为古戈尔）代表10的100次方，足够穷尽宇宙万物
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-21 16:18
 */

@Ignore
public class SchedulerTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test_scheduler.xml");
        while (true) {

        }
    }

}
