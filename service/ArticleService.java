package test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.controller.DTO.ArticleDTO;
import test.controller.DTO.ArticlesDTO;
import test.domain.Article;
import test.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    //게시판 전체 목록 조회
    public List<ArticleDTO> printArticles() {

        List<Article> articles = articleRepository.findAll();
        List<ArticleDTO> articleDTOS = new ArrayList<>();

        for (Article article : articles) {
            ArticleDTO articleDTO = ArticleDTO.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .content(article.getContent())
                    .createDate(article.getCreateDate())
                    .modifyDate(article.getModifyDate())
                    .viewCount(article.getViewCount())
                    .category(article.getCategory())
                    .build();

            articleDTOS.add(articleDTO);
        }

        return articleDTOS;
    }

    //게시판 전체 목록 조회 v2
    public List<ArticlesDTO> view() {

        List<Object[]> results = articleRepository.view();
        return getArticlesDTOS(results);
    }


    //게시글 아이디로 게시글 찾기
    public ArticleDTO findArticleByArticleId(Long articleId) {
        Article article = articleRepository.findOne(articleId);

        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createDate(article.getCreateDate())
                .modifyDate(article.getModifyDate())
                .viewCount(article.getViewCount())
                .category(article.getCategory())
                .build();
    }

    //게시글 수정
    @Transactional(readOnly = false)
    public void updateArticle(Long articleId, ArticleDTO articleDTO) {
        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();
        String category = articleDTO.getCategory();

        articleRepository.update(articleId, title, content, category);
    }

    //이렇게 이름이 같아도 되나?
    @Transactional(readOnly = false)
    public Long saveArticle(String title, String content, String category) {

        Article article = Article.builder()
                .title(title)
                .content(content)
                .createDate(new Date())
                .modifyDate(new Date())
                .viewCount(0L)
                .category(category)
                .build();

        return articleRepository.save(article);
    }

    //게시글 삭제
    @Transactional(readOnly = false)
    public void deleteArticleByArticleId(Long articleId) {
        articleRepository.delete(articleId);
    }


    //게시글 조회수 상승
    @Transactional(readOnly = false)
    public void incrementArticleViewCount(Long articleId) {
        Article article = articleRepository.findOne(articleId);

        article.incrementArticleViewCount();
    }

    //제목, 카테고리, 댓글로 게시글 (OR)검색
    public List<ArticlesDTO> orSearchByArticleString(String string) {
        List<Object[]> results = articleRepository.searchByStringWithCondition(string, "or");
        return getArticlesDTOS(results);
    }

    //제목, 카테고리, 댓글로 게시글 (AND)검색
    public List<ArticlesDTO> andSearchByArticleString(String string) {
        List<Object[]> results = articleRepository.searchByStringWithCondition(string, "and");
        return getArticlesDTOS(results);
    }


    //DTOS편의 초기화 객체(게시판에서 많은 글을 조회할 떄 쓰임)/article/article에서 많이 쓰임
    private List<ArticlesDTO> getArticlesDTOS(List<Object[]> results) {
        List<ArticlesDTO> articlesDTOS = new ArrayList<>();

        for (Object[] result : results) {
            ArticlesDTO articlesDTO = ArticlesDTO.builder()
                    .id((Long)result[0])
                    .title((String)result[1])
                    .viewCount((Long)result[2])
                    .likeCount((Long)result[3])
                    .build();

            articlesDTOS.add(articlesDTO);
        }

        return articlesDTOS;
    }

}
