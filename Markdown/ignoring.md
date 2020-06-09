# 스프링 시큐리티 ignoring()

WebSecurity 의 ignoring() 을 사용해서 시큐리티 필터 적용을 제외할 요청을 설정할 수 있다.

```java
@Override
public void configure(WebSecurity web) throws Exception {
  // 파비콘 요청 무시
  // web.ignoring().mvcMatchers("/favicon.ico");
  web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
}
```

스프링 부트가 제공하는 PathRequest 를 사용해서 정적 자원 요청을 스프링 시큐리티 필터를 적용하지 않도록 설정

localhost:8080/ 으로 URL 요청을 보내면 localhost:8080/ 에 대한 요청만 있을 것 같지만 총 3개의 요청이 온다.

- localhost:8080/
- favicon.ico
- login 

따라서 favicon.ico 같은 정적 자원 요청을 시큐리티 필터를 타지 않게 설정할 수 있는데 위 소스가 바로 그것이다.

- FilterChainProxy.class

```java
    private void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FirewalledRequest fwRequest = this.firewall.getFirewalledRequest((HttpServletRequest)request);
        HttpServletResponse fwResponse = this.firewall.getFirewalledResponse((HttpServletResponse)response);
        List<Filter> filters = this.getFilters((HttpServletRequest)fwRequest);
        
        // 정적 자원에 대해서 시큐리티 필터를 적용하지 않는다는 것을 SecurityConfig 에서 설정하면 
        // favicon.ico 같은 정적 자원들이 요청이 들어오면 filters 의 size 가 0 이 된다.
        if (filters != null && filters.size() != 0) {
            FilterChainProxy.VirtualFilterChain vfc = new FilterChainProxy.VirtualFilterChain(fwRequest, chain, filters);
            vfc.doFilter(fwRequest, fwResponse);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug(UrlUtils.buildRequestUrl(fwRequest) + (filters == null ? " has no matching filters" : " has an empty filter list"));
            }

            fwRequest.reset();
            chain.doFilter(fwRequest, fwResponse);
        }
    }
```

