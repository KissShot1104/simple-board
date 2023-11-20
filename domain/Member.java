package test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

//멤버 객체(로그인때 사용할 계획)
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;     //로그인 아이디
    private String password;    //비밀번호
    private String nickname;    //닉네임
    private String email;       //이메일

    protected Member() {}

}
