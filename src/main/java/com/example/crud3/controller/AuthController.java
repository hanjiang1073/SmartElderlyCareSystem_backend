package com.example.crud3.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.crud3.payload.request.LoginRequest;
import com.example.crud3.payload.request.SignupRequest;
import com.example.crud3.payload.response.JwtResponse;
import com.example.crud3.payload.response.MessageResponse;
import com.example.crud3.service.MailService;
import com.example.crud3.service.UserFileService;
import com.example.crud3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.crud3.models.ERole;
import com.example.crud3.models.Role;
import com.example.crud3.models.User;
import com.example.crud3.repository.RoleRepository;
import com.example.crud3.repository.UserRepository;
import com.example.crud3.security.jwt.JwtUtils;
import com.example.crud3.security.services.UserDetailsImpl;

import static com.example.crud3.utils.Global.CODEERROR;
import static com.example.crud3.utils.Global.REGISTER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	MailService mailService;
	@Autowired
	UserFileService fileService;
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestParam("username")String username,
											   @RequestParam("password")String password, HttpSession session, HttpServletResponse response) {

			try {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(username, password));

				//SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> roles = userDetails.getAuthorities().stream()
						.map(item -> item.getAuthority())
						.collect(Collectors.toList());
				session.setAttribute("nowUser", userDetails);
				// Week week = userService.getUserFile(user.getUserid());
				// session.setAttribute("week",week);
				Cookie cookie = new Cookie("id", String.valueOf(userDetails.getId()));
				cookie.setPath("/");
				response.addCookie(cookie);

				return ResponseEntity.ok(new JwtResponse(jwt,
														 userDetails.getId(),
														 userDetails.getUsername(),
														 roles));

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestParam("username") String username,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("code")String codeValue) {
		if (userRepository.existsByUsername(username)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		String code = mailService.getCode(email, REGISTER);
		if(codeValue.compareTo(code)!=0){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Code Error"));
		}

			mailService.sendSimpleMail(email ,"Register Notice",
					"Register Notice    "+"Register successfully! Welcome to use our products, if you have any " +
							"comments, please feel free to feedback, we will actively improve, thank you.");


		// Create new user's account
		User user = new User(username,
							 encoder.encode(password));


		Set<String> strRoles = new HashSet<String>();
		strRoles.add("ROLE_USER");
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
