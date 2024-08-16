package org.enigma.virtualmuseumvisitingplatform.service.concretes.users;

import lombok.RequiredArgsConstructor;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.DataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.Result;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessDataResult;
import org.enigma.virtualmuseumvisitingplatform.core.utilities.result.SuccessResult;
import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserInfoResponse;
import org.enigma.virtualmuseumvisitingplatform.dto.response.user.UserSignupDto;
import org.enigma.virtualmuseumvisitingplatform.entity.artifacts.Artifact;
import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.enigma.virtualmuseumvisitingplatform.entity.role.ERole;
import org.enigma.virtualmuseumvisitingplatform.entity.role.Role;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ArtifactAlreadyExists;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ArtifactNotFoundException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.ExistsUserException;
import org.enigma.virtualmuseumvisitingplatform.exceptions.cutomExceptions.RoleNotFountException;
import org.enigma.virtualmuseumvisitingplatform.repository.artifact.ArtifactRepository;
import org.enigma.virtualmuseumvisitingplatform.repository.user.UserRepository;
import org.enigma.virtualmuseumvisitingplatform.repository.role.RoleRepository;
import org.enigma.virtualmuseumvisitingplatform.security.entities.UserDetailsImpl;
import org.enigma.virtualmuseumvisitingplatform.security.jwt.JWTUtils;
import org.enigma.virtualmuseumvisitingplatform.service.abstracts.users.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JWTUtils jwtUtils;
    private final ArtifactRepository artifactRepository;

    @Override
    public DataResult<UserInfoResponse> login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                jwt,
                roles), "successfully found user");
    }

    @Override
    public Result register(UserSignupDto userSignupDto) {
        if (userRepository.existsByUsername(userSignupDto.getUsername())) {
            throw new ExistsUserException("Error: Username is already taken!");
        }


        // Create new user's account
        User user = new User(userSignupDto.getUsername(),
                encoder.encode(userSignupDto.getPassword()));


        Set<Role> roles = new HashSet<>();


        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RoleNotFountException("Error: Role is not found."));
        roles.add(userRole);


        user.setRoles(roles);
        userRepository.save(user);


        return new SuccessResult("User successfully added");
    }

    @Override
    public Result addFavourite(long userId, long artifactId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Error: User not found."));

        user.getSavedArtifacts().forEach(s -> {
            if (s.getId() == artifactId) {
                throw new ArtifactAlreadyExists("Artifact Already Exists");
            }
        });
        List<Artifact> artifacts = this.artifactRepository.findAllById(artifactId);
        user.setSavedArtifacts(artifacts);

        this.userRepository.save(user);
        return new SuccessResult("User successfully added");
    }

    @Override
    public DataResult<List<Artifact>> getFavourites(long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Error: User not found."));
        return new SuccessDataResult<>(user.getSavedArtifacts(), "successfully found artifacts");
    }
}
