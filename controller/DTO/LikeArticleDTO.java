package test.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import test.domain.Article;
import test.domain.Member;


//게시글 좋아요 DTO 객체
@Getter
@Builder
@AllArgsConstructor
public class LikeArticleDTO {

    private Long id;
    private Member member;
    private Article article;

    protected LikeArticleDTO(){}

}
