package discussion.controller;

import discussion.dto.RefreshTokenDto;
import discussion.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import discussion.dto.AuthenticationResponse;
import discussion.dto.LoginRequest;
import discussion.dto.SignupRequest;
import discussion.service.AuthService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;

	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
		authService.signup(signupRequest);
		return new ResponseEntity<>("signup successful",HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{verificationToken}")
	public ResponseEntity<String> accountVerification(@PathVariable String verificationToken){
		authService.accountVerification(verificationToken);
		return new ResponseEntity<>("Account activated successfully",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse userLogin(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/refreshToken")
	public AuthenticationResponse refreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
		return authService.validateRefreshTokenAndReGenerateJwtToken(refreshTokenDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody RefreshTokenDto refreshTokenDto){
		refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("refresh token deleted");
	}

}
