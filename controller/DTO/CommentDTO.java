package test.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import test.domain.Article;
import test.domain.Member;

import java.util.Date;


//게시글 댓글 DTO객체
@Getter
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;

    private Member member;
    private Article article;
    private String content;
    private Date createDate;
    private Date modifyDate;

    protected CommentDTO(){}
}
