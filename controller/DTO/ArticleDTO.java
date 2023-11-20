package test.controller.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Date;

//게시글 DTO객체
@Getter
@Builder
@AllArgsConstructor
public class ArticleDTO {
    private Long id;    //아이디
    private String title;   //게시글 제목
    private String content;  //내용
    private Date createDate;    //생성 일자
    private Date modifyDate;    //수정 일자
    private Long viewCount;  //게시글 조회수
    private String category; //카테고리
}
