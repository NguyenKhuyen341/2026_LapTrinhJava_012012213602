package com.planbookai.backend.presentation.controller.auth;

import com.planbookai.backend.domain.model.usermanagement.NguoiDung;
import com.planbookai.backend.domain.model.usermanagement.NguoiDungRepository;
import com.planbookai.backend.domain.model.usermanagement.VaiTro;
import com.planbookai.backend.infrastructure.security.JwtTokenProvider;
import com.planbookai.backend.infrastructure.security.UserPrincipal;
import com.planbookai.backend.presentation.dto.auth.AuthResponse;
import com.planbookai.backend.presentation.dto.auth.LoginRequest;
import com.planbookai.backend.presentation.dto.auth.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMatKhau()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        NguoiDung user = userPrincipal.getNguoiDung();

        return ResponseEntity.ok(new AuthResponse(
                jwt,
                user.getEmail(),
                user.getHoTen(),
                user.getVaiTro().name()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (nguoiDungRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: Email đã được sử dụng bởi tài khoản khác!");
        }

        // Tạo tài khoản người dùng mới (Mặc định là TEACHER theo đúng logic nghiệp vụ an toàn)
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setEmail(registerRequest.getEmail());
        nguoiDung.setMatKhauMaHoa(passwordEncoder.encode(registerRequest.getMatKhau()));
        nguoiDung.setHoTen(registerRequest.getHoTen());
        nguoiDung.setVaiTro(VaiTro.TEACHER); // Ép gán vai trò TEACHER công khai
        nguoiDung.setTrangThaiHoatDong(true);

        nguoiDungRepository.save(nguoiDung);

        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký tài khoản Giáo viên thành công!");
    }
}
