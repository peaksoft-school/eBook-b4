package kg.peaksoft.ebookb4.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

@Getter @Setter
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String email;

  @JsonIgnore
  private String password;

  private String number;

  private String firstName;

  private String lastName;

  private List<? extends GrantedAuthority> authorities;

  private boolean isAccountNonExpired = true;
  private boolean isAccountNonLocked = true;
  private boolean isCredentialsNonExpired = true;
  private boolean isEnabled = true;

  public UserDetailsImpl(Long id, String email, String password,
                         List<? extends GrantedAuthority> authorities, String number,
                         String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.number = number;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static UserDetailsImpl build(User user) {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());

    return new UserDetailsImpl(
        user.getId(), 
        user.getEmail(),
        user.getPassword(),
            Collections.singletonList(authority), user.getNumber(),
            user.getFirstName(),
            user.getLastName()
            );
  }


  @Override
  public String getUsername() {
    return email;
  }
}
