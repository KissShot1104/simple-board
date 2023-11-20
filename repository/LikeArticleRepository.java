package test.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.domain.LikeArticle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class LikeArticleRepository {

    @PersistenceContext
    private final EntityManager em;

    //좋아요 저장
    public void save(LikeArticle likeArticle) {
        em.persist(likeArticle);
    }

    //좋아요 찾기
    public LikeArticle findOne(Long likeArticleId) {
        return em.find(LikeArticle.class, likeArticleId);
    }

    //게시글안의 좋아요 카운팅
    public Long countLikeArticle(Long articleId) {

        return em.createQuery("select count(la) " +
                        "from LikeArticle la " +
                        "where la.article.id = :articleId", Long.class)
                .setParameter("articleId", articleId)
                .getSingleResult();
    }

    //게시글 안의 좋아요 일괄 삭제
    public void delete(Long articleId) {
        em.createQuery("delete from LikeArticle la where la.article.id = :articleId")
                .setParameter("articleId", articleId)
                .executeUpdate();
    }


}
