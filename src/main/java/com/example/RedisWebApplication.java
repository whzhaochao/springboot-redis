package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@Controller
public class RedisWebApplication {


	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, User> redisTemplate;


	@Autowired
	private  RedisUtil redisUtil;


	@RequestMapping("/")
	@Cacheable(value="test")
	@ResponseBody
	public String getSessionId(HttpSession session){
		redisUtil.set("123", "测试");
		System.out.println("进入了方法");
		String string= redisUtil.get("123").toString();
		return string;
	}

	@RequestMapping("/hello")
	@ResponseBody
	  public  String hello(){
		// 保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");

		// 保存对象
		User user = new User("超人", 20);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		user = new User("蝙蝠侠", 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		user = new User("蜘蛛侠", 40);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		System.out.println(redisTemplate.opsForValue().get("超人").getAge().longValue());
		System.out.println( redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
		System.out.println(redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());





		return stringRedisTemplate.opsForValue().get("aaa");
	}


	public static void main(String[] args) {
		SpringApplication.run(RedisWebApplication.class, args);
	}
}
