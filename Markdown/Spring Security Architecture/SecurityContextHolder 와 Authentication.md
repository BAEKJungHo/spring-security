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
