package test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.controller.DTO.ArticleDTO;
import test.domain.Article;
import test.domain.LikeArticle;
import test.repository.ArticleRepository;
import test.repository.LikeArticleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeArticleService {

    //service에는 repository만 있는게 좋은것인가?
    private final LikeArticleRepository likeArticleRepository;
    private final ArticleRepository articleRepository;

    public Long countLikeArticle(Long articleId) {
        return likeArticleRepository.countLikeArticle(articleId);
    }

    public List<Long> countLikeArticleAll(List<ArticleDTO> articleDTOS) {
        List<Long> countLikeArticles = new ArrayList<>();

        for (ArticleDTO articleDTO : articleDTOS) {
            Long count = countLikeArticle(articleDTO.getId());
            countLikeArticles.add(count);
        }

        return countLikeArticles;
    }

    //언제 오버라이딩을 하는것이 좋은가?
    //게시글에 좋아요 추가 시 사용
    @Transactional(readOnly = false)
    public void saveLikeArticleByArticleId(Long articleId) {

        Article article = articleRepository.findOne(articleId);

        LikeArticle likeArticle = LikeArticle.builder()
                .article(article)
                .build();

        likeArticleRepository.save(likeArticle);
    }

    //게시글 좋아요 일괄삭제할 때 사용
    @Transactional(readOnly = false)
    public void deleteLikeArticleByArticleId(Long articleId) {
        likeArticleRepository.delete(articleId);
    }



}
