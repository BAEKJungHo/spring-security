# SecurityConfig

## 시큐리티 설정 파일 만드는 방법

- @EnableWebSecurity 어노테이션 붙이기
- WebSecurityConfigurerAdapter 상속

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

}
```
