package test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.domain.Article;
import test.domain.Image;
import test.domain.embedded.UploadFile;
import test.image.FileStore;
import test.image.UploadImagesForm;
import test.repository.ArticleRepository;
import test.repository.ImageRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FileStore fileStore;


    //file service에서 article service접근하는것, file service에서 article repository에 접근하는것 물업자
    @Transactional(readOnly = false)
    public void saveImage(Long articleId, UploadImagesForm uploadImagesForm) throws IOException {
        Article article = articleRepository.findOne(articleId);

        List<UploadFile> uploadFiles = fileStore.storeFiles(uploadImagesForm.getImageFiles());

        Image image = Image.builder()
                .imageFiles(uploadFiles)
                .article(article)
                .build();

        imageRepository.save(image);

    }

    public String getFullPath(String filename) {
        return fileStore.getFullPath(filename);
    }

    public List<Image> showImages(Long articleId) {
        return imageRepository.print(articleId);
    }

    public List<UploadFile> showImage(Long imageId) {
        return imageRepository.findOne(imageId).getImageFiles();
    }

    public void deleteImageByArticleId(Long articleId) {
        imageRepository.deleteImageByArticleId(articleId);
    }

}
