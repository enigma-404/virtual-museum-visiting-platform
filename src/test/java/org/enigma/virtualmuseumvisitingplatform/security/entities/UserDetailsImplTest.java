package org.enigma.virtualmuseumvisitingplatform.security.entities;

import org.enigma.virtualmuseumvisitingplatform.entity.role.ERole;
import org.enigma.virtualmuseumvisitingplatform.entity.role.Role;
import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Setting up a mock user with roles
        Set<Role> roles = new HashSet<>();

        Role userRole = new Role();
        userRole.setName(ERole.USER);
        roles.add(userRole);

        Role adminRole = new Role();
        adminRole.setName(ERole.ADMIN);
        roles.add(adminRole);

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRoles(roles);
    }

    @Test
    void testBuild() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        assertNotNull(userDetails);
        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(ERole.USER.name())));
        assertTrue(authorities.contains(new SimpleGrantedAuthority(ERole.ADMIN.name())));
    }

    @Test
    void testIsAccountNonExpired() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals_SameObject() {
        UserDetailsImpl userDetails1 = UserDetailsImpl.build(user);
        UserDetailsImpl userDetails2 = UserDetailsImpl.build(user);
        assertEquals(userDetails1, userDetails2);
    }

    @Test
    void testEquals_DifferentObject() {
        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setUsername("otherUser");
        otherUser.setPassword("otherPassword");

        UserDetailsImpl userDetails1 = UserDetailsImpl.build(user);
        UserDetailsImpl userDetails2 = UserDetailsImpl.build(otherUser);

        assertNotEquals(userDetails1, userDetails2);
    }

    @Test
    void testEquals_NullObject() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertNotEquals(userDetails, null);
    }

    @Test
    void testEquals_DifferentClass() {
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        assertNotEquals(userDetails, new Object());
    }
}
