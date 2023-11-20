package test.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


//모든 게시글 조회 시 사용되는 DTO객체
@AllArgsConstructor
@Builder
@Getter
public class ArticlesDTO {
    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount;
}
