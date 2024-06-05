package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<UserInfoEntity> user = userInfoRepository.findByUsername(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new CustomUserDetails(user.get());
    }
}
