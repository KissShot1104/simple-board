package test.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.controller.DTO.ArticlesDTO;
import test.domain.Article;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    @PersistenceContext
    private final EntityManager em;

    public Long save(Article article) {
        em.persist(article);
        return article.getId();
    }

    public Article findOne(Long articleId) {
        return em.find(Article.class, articleId);
    }

    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }



    //업데이트
    public void update(Long articleId, String title, String content, String category) {
        em.createQuery("update Article a " +
                        "set a.title = :title, a.content = :content, category = :category " +
                        "where a.id = :articleId")
                .setParameter("title", title)
                .setParameter("content", content)
                .setParameter("category", category)
                .setParameter("articleId", articleId)
                .executeUpdate();
    }

    //게시글 삭제
    public void delete(Long articleId) {
        Article article = findOne(articleId);
        em.remove(article);
    }

    //게시글 전체 조회
    public List<Object[]> view() {

        return em.createQuery("select a.id, a.title, a.viewCount, count(la.article.id) " +
                                "from Article a " +
                                "left outer join LikeArticle la " +
                                "on a.id = la.article.id " +
                                "group by a.id")
                .getResultList();
    }




    //검색 쿼리
    public List<Object[]> searchByStringWithCondition(String searchValue, String searchType) {

        if (searchType.equals("or")) {
            searchValue = "%" + searchType + "%";
        }

        return em.createQuery("select a.id, a.title, a.viewCount, count(la.article.id) " +
                        "from Article a " +
                        "left outer join LikeArticle la " +
                        "on a.id = la.article.id " +
                        "where a.title like :searchValue " +
                        "group by a.id")
                .setParameter("searchValue", searchValue)
                .getResultList();
    }


}
