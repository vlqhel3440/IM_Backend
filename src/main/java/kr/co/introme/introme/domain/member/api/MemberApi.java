package kr.co.introme.introme.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.introme.introme.domain.member.application.MemberSigninService;
import kr.co.introme.introme.domain.member.application.MemberSignupService;
import kr.co.introme.introme.domain.member.dto.MemberSignInRequest;
import kr.co.introme.introme.domain.member.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "회원 API", description = "회원 가입, 로그인 API")
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberSignupService memberSignupService;
    private final MemberSigninService memberSigninService;

    @Operation(summary = "회원가입", description = "dto로 받은 회원가입 정보를 저장합니다.")

    @PostMapping("/signup")
    public ResponseEntity<String> save(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        System.out.println("memberSignUpRequest = " + memberSignUpRequest.toString());
        memberSignupService.signUp(memberSignUpRequest);
        return ResponseEntity.ok("회원가입 완료!");
    }

    @Operation(summary = "로그인", description = "dto로 받은 로그인 정보를 검증합니다.")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody MemberSignInRequest memberSignInRequest) {
        try {
            String jwtToken = memberSigninService.signIn(memberSignInRequest);
            return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).body("로그인 성공!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
        }
    }
}
