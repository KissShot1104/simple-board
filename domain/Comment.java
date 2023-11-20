package test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import test.domain.embedded.TimeStamps;

import javax.persistence.*;
import java.util.Date;

//댓글 객체
@Getter
@Entity
@AllArgsConstructor
@Builder

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;      //멤버 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;    //게시글 아이디

    private String content;    //댓글 내용

    private Date createDate;    //생성 일자
    private Date modifyDate;    //수정 일자

    protected Comment(){}
}
