package test.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import test.domain.embedded.TimeStamps;

import javax.persistence.*;
import java.util.Date;

//게시글 객체
@Getter
@Entity
@Builder
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;    //아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  //작성자
    private String title;   //게시글 제목
    private String content;  //내용
    private Date createDate;    //생성 일자
    private Date modifyDate;    //수정 일자
    private Long viewCount;  //게시글 조회수
    private String category; //카테고리

    protected Article() {}

    public void incrementArticleViewCount() {
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }

        this.viewCount += 1;
    }

}
