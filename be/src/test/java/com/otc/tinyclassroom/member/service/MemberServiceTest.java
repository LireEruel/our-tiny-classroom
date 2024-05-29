package com.otc.tinyclassroom.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.otc.tinyclassroom.global.config.WebSecurityConfig;
import com.otc.tinyclassroom.member.dto.MemberDto;
import com.otc.tinyclassroom.member.dto.request.MemberJoinRequestDto;
import com.otc.tinyclassroom.member.entity.Member;
import com.otc.tinyclassroom.member.exception.MemberException;
import com.otc.tinyclassroom.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DisplayName("member service 테스트")
@Import(WebSecurityConfig.class)
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService sut;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입 성공")
    @Test
    void givenInfo_whenRequestJoin_thenReturnVoid() {
        // Given
        // TODO: fixture로 변경
        Member member = Member.of("test123",  "test1!Qa", "test", "test@gmail.com", LocalDate.now(), 0, null);

        MemberDto dto = MemberDto.from(member);
        MemberJoinRequestDto request = MemberJoinRequestDto.from(dto);
        given(memberRepository.findByMemberId("test123")).willReturn(Optional.empty());
        given(memberRepository.save(member)).willReturn(member);

        // When
        sut.join(request);

        // Then
        then(memberRepository).should().findByMemberId(request.memberId());
        then(memberRepository).should().save(member);
    }

    @DisplayName("비어있는 필드 - 회원가입 실패")
    @Test
    void givenBlankValue_whenRequestJoin_thenThrowInvalidFieldException() {
        // Given
        MemberJoinRequestDto request = MemberJoinRequestDto.of("test1", "test1!Qa", "   ", "test@gmail.com", LocalDate.now());

        // When
        Throwable t = catchThrowable(() -> sut.join(request));

        // Then
        assertThat(t)
            .isInstanceOf(MemberException.class)
            .hasMessageContaining("필드");
    }

    @DisplayName("중복된 유저아이디 - 회원가입 실패")
    @Test
    void givenDuplicatedMemberId_whenRequestJoin_thenThrowDuplicatedException() {
        // Given
        Member exist = Member.of("test1", "test1!Qa", "test", "test@gmail.com", LocalDate.now(), 0, null);
        MemberDto dto = MemberDto.from(exist);
        MemberJoinRequestDto request = MemberJoinRequestDto.from(dto);
        given(memberRepository.findByMemberId(request.memberId())).willReturn(Optional.of(exist));

        // When
        Throwable t = catchThrowable(() -> sut.join(request));

        // Then
        assertThat(t)
            .isInstanceOf(MemberException.class)
            .hasMessageContaining("중복된 아이디입니다.");
        then(memberRepository).should().findByMemberId(request.memberId());
    }

    @DisplayName("유효하지 않은 아이디 - 회원가입 실패")
    @Test
    void givenInvalidMemberId_whenRequestJoin_thenThrowInvalidMemberIdValueException() {
        // Given
        MemberJoinRequestDto request = MemberJoinRequestDto.of("test", "test1!Qa123", "test", "test@gmail.com", LocalDate.now());
        // When
        Throwable t = catchThrowable(() -> sut.join(request));
        // Then
        assertThat(t)
            .isInstanceOf(MemberException.class)
            .hasMessageContaining("아이디");
    }

    @DisplayName("유효하지 않은 비밀번호 - 회원가입 실패")
    @Test
    void givenInvalidPassword_whenRequestJoin_thenThrowInvalidPasswordValueException() {
        // Given
        MemberJoinRequestDto request = MemberJoinRequestDto.of("test1", "test1!", "test", "test@gmail.com", LocalDate.now());
        // When
        Throwable t = catchThrowable(() -> sut.join(request));
        // Then
        assertThat(t)
            .isInstanceOf(MemberException.class)
            .hasMessageContaining("비밀번호");
    }

    @DisplayName("유효하지 않은 이메일 형식 - 회원가입 실패")
    @Test
    void givenInvalidEmail_whenRequestJoin_thenThrowInvalidEmailException() {
        // Given
        MemberJoinRequestDto request = MemberJoinRequestDto.of("test2", "test1!Qae", "test", "test@gmailcom", LocalDate.now());
        // When
        Throwable t = catchThrowable(() -> sut.join(request));
        // Then
        assertThat(t)
            .isInstanceOf(MemberException.class)
            .hasMessageContaining("이메일");
    }
}