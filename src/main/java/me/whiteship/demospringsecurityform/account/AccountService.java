package me.whiteship.demospringsecurityform.account;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 는 우리가 흔히 알고 있는 DAO 라는 Data Access Object 를 통해서, 등록된 유저 정보를 가져온다.
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired AccountRepository accountRepository;

    @Autowired PasswordEncoder passwordEncoder;

    /**
     * 이 메서드가 해야할 일은 userName 을 받아서 해당 유저 이름에 대한 정보를 가져와서
     * UserDetails type 으로 return 해줘야 한다.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        // 계정 정보가 없는 경우 에러 메시지를 보낸다.
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        /**
         *  UserDetails 로 쉽게 변환이 가능한 User 라는 클래스를 제공한다.
         *         return User.builder()
         *                     .username(account.getUsername())
         *                     .password(account.getPassword())
         *                     .roles(account.getRole())
         *                     .build();
         */

        return new UserAccount(account);
    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }
}
