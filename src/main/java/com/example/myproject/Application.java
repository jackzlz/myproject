/**
 * 
 */
package com.example.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.myproject.util.RedisUtil;

/**
 * <p>
 * 类的描述
 * </p>
 *
 * @author zhenglz 2016年12月5日
 *
 */
@SpringBootApplication
@Import(RedisUtil.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
