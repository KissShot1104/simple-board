package test.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class UploadImagesForm {

    private List<MultipartFile> imageFiles;
    protected UploadImagesForm(){}
}
