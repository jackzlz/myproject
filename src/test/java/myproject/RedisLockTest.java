package myproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.myproject.Application;
import com.example.myproject.util.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisLockTest {

	@Test
	public void testRedisUtil() {
		String str = "12345678911";

		boolean isLock = RedisUtil.tryLock(str);
		if (isLock) {
			System.out.println("================获得锁.");
		} else {
			System.out.println("================获取锁失败.");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RedisUtil.unLock(str);

	}
}
