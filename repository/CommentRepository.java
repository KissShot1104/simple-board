package test.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    public List<Comment> findByArticleId(Long articleId) {
        return em.createQuery("select c from Comment c where c.article.id = :articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    public void delete(Long commentId) {
        em.createQuery("delete from Comment c where c.id = :commentId")
                .setParameter("commentId", commentId)
                .executeUpdate();
    }

    public void deleteByArticleId(Long articleId) {
        em.createQuery("delete from Comment c where c.article.id = :articleId")
                .setParameter("articleId", articleId)
                .executeUpdate();
    }




}
