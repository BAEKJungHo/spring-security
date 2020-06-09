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

## inMemoryAuthentication 사용하여 여러명의 User 추가하기

스프링 부트에서 시큐리티를 사용할 때 기본적으로 1명의 user 만 사용 가능하도록 되어있다.  

시큐리티에서 제공하는 user 의 id 는 user 이고 password 는 콘솔에 찍혀있다. 콘솔에 비밀번호가 노출되는것은 보안상 위험하기 때문에 실무에서는 
다른 방식으로 사용한다. 

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { 
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .mvcMatchers("/", "/info").permitAll()
      .mvcMatchers("/admin").hasRole("ADMIN")
      .anyRequest().authenticated();
    http.formLogin();
    http.httpBasice();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("keesun").password("{noop}123").roles("USER").and()
      .withUser("admin").password("{noop}!@#").roles("ADMIN");
  }
  
}
```
