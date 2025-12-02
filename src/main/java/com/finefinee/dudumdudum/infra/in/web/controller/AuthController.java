package com.finefinee.dudumdudum.infra.in.web.controller;

import com.finefinee.dudumdudum.application.port.in.SignUpUseCase;
import com.finefinee.dudumdudum.domain.exception.BusinessException;
import com.finefinee.dudumdudum.domain.exception.ErrorCode;
import com.finefinee.dudumdudum.infra.in.web.dto.common.ApiResponse;
import com.finefinee.dudumdudum.infra.in.web.dto.request.LoginRequest;
import com.finefinee.dudumdudum.infra.in.web.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<java.util.UUID> signUp(@RequestBody @Valid SignUpRequest request) {
        java.util.UUID memberId = signUpUseCase.joinMember(request.toCommand());
        return new ApiResponse<>(201, "회원가입 성공", memberId);
    }

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody @Valid LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), servletRequest, servletResponse);

            return new ApiResponse<>(200, "로그인 성공", null);
        } catch (DisabledException e) {
            throw e;
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return new ApiResponse<>(200, "로그아웃 성공", null);
    }
}
