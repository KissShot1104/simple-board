package test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import test.domain.embedded.UploadFile;

import javax.persistence.*;
import java.util.List;

//이미지 객체
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;     //게시글 아이디



    @ElementCollection
    private List<UploadFile> imageFiles;

    protected Image(){}
}
