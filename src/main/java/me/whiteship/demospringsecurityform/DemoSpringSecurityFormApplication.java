package me.whiteship.demospringsecurityform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class DemoSpringSecurityFormApplication {

	/**
	 * 스프링 시큐리티가 제공하는 PasswordEncoder 를 사용하면  "{noop}" + vo.getPassword(); 처럼 코딩하지 않아도 된다.
	 * 스프링이 낮은 버전은 NoOpPasswordEncoder 를 사용하여 패스워드를 저장하였는데, 스프링 버전을 올리면서 Deprecated 되었다.
	 * 따라서 NoOpPasswordEncoder 로 저장됬던 패스워드들은 평문으로 저장됬을 가능성도 있다.
	 * 바뀐 포맷 : {id}encodedPassword
	 * createDelegatingPasswordEncoder 는 상당히 많은 인코딩 방식을 지원한다.
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringSecurityFormApplication.class, args);
	}

}
