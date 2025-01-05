//package com.egroc.model;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class CustomUserDetail extends User implements UserDetails {
//
//    //private User user;
//
//    public CustomUserDetail(Optional<User> user) {
//        super(user);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // TODO Auto-generated method stub
//        List<GrantedAuthority> authorityList =new ArrayList<>();
//        super.getRoles().forEach(role->{
//            authorityList.add(new SimpleGrantedAuthority(role.getName()));
//        });
/// /        return List.of(() -> user.getRole());
//        return  authorityList;
//    }
//
//
//    public String getUsername() {
//        return super.getEmail();
//    }
//
//    @Override
//    public String getPassword() {
//        return super.getPassword();
//    }
//
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//
//        return true;
//    }
//
//}