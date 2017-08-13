package com.api.Chat.model.factory;

import com.api.Chat.domain.entity.User;
import com.api.Chat.model.security.CerberusUser;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class CerberusUserFactory {

  public static CerberusUser create(User user) {
    Collection<? extends GrantedAuthority> authorities;
    try {
      authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
    } catch (Exception e) {
      authorities = null;
    }
    return new CerberusUser(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getEmail(),
      user.getLastPasswordReset(),
      authorities
    );
  }

}
