package test.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.domain.Image;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    @PersistenceContext
    private EntityManager em;

    //이미지 저장
    public void save(Image image) {
        em.persist(image);
    }

    public List<Image> print(Long articleId) {

        return em.createQuery("select i from Image i where i.article.id = :articleId", Image.class)
                .setParameter("articleId", articleId)
                .getResultList();
    }

    public Image findOne(Long imageId) {
        return em.find(Image.class, imageId);
    }

    public void deleteImageByArticleId(Long articleId) {

        List<Image> images = em.createQuery("select i from Image i where i.article.id = :articleId", Image.class)
                .setParameter("articleId", articleId)
                .getResultList();

        //게시글에 이미지 넣지 않았을때 Null값이므로 이렇게 해줘야 한다.
        if (images == null) {
            return ;
        }

        for (Image image : images) {
            em.remove(image);
        }

    }
}
