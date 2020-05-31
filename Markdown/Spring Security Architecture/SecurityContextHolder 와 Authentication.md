# SpingSecurityContextHolder 와 Authentication

- `SecurityContextHolder`
  - SecurityContext 제공, 기본적으로 ThreadLocal 사용
- `SecurityContext`
  - Authentication 제공
  
> SecurityContextHolder > SecurityContext > Authentication 

- `Authentication`
  - Principal 과 GrantAuthority 제공
- `Principal`
  - "누구" 에 해당하는 정보
  - UserDetailsService 에서 리턴한 그 객체. 그 객체는 UserDetails 타입
- `GrantAuthority`
  - "ROLE_USER", "ROLE_ADMIN" 등 Principal 이 가지고 있는 "권한"을 나타낸다.
  - 인증 이후, 인가 및 권한 확인할 때 이 정보를 참조한다.

> Principal 객체는 Authentication 으로 감싸져 있고 Authentication 은 SecurityContext 로 감싸져 있으며 SecurityContext 는 SecurityContextHolder 로 감싸져 있다. 

```java
@Service
public class SampleService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void dashboard() {
        /**
         * SecurityContextHolder 에 있는 SecurityContext 를 가져와서 그 안에 있는 Authentication 을 꺼내오고
         * Authentication 에 있는 Principal 객체를 가져온다. 그 객체의 타입은 UserDetails 이다.
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        /*
         * Object principal = authentication.getPrincipal();
         * Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
         */
         
        System.out.println("===============");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called.");
    }
}
```
