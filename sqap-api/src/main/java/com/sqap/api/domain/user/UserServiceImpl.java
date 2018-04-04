package com.sqap.api.domain.user;


import com.google.common.collect.Sets;
import com.sqap.api.base.AuthenticationFacade;
import com.sqap.api.domain.user.role.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationFacade authenticationFacade, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserEntity save(UserDto userDto) {
        UserEntity user = new UserEntity(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), true, Sets.newHashSet(RoleType.ROLE_USER), userDto.getRegisterStatementConfirmed(), new PersonalData(userDto.getSex(), userDto.getAge(), userDto.getIsHearingDefect()));
        return userRepository.save(user);
    }


    @Override
    public UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public UserEntity findRequester() {
//        return getRepository().findByUsername(authenticationFacade.getAuthentication().getName()).orElseThrow(() -> new UsernameNotFoundException("Cannot find requester user"));
        return getRepository().findByUsername(authenticationFacade.getAuthentication().getName()).orElse(null);
    }

}
