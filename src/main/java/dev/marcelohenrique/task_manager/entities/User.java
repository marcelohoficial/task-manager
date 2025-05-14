package dev.marcelohenrique.task_manager.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.marcelohenrique.task_manager.dto.UserDTO;
import dev.marcelohenrique.task_manager.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;
  private UserRole userRole;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority(userRole.name()));
  }

  @Override
  public String getUsername() {
      return email;
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public boolean isEnabled() {
      return true;
  }
  
  public UserDTO toUserDTO() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(this.id);
    userDTO.setName(this.name);
    userDTO.setEmail(this.email);
    userDTO.setUserRole(this.userRole);
    return userDTO;
  }
}
