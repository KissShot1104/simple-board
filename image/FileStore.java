package test.image;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import test.domain.embedded.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class FileStore {
    private final String dirPath = "d:/images/";

    //파일 경로
    public String getFullPath(String filename) {
        return dirPath + filename;
    }

    //파일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStroeFilename(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return UploadFile.builder()
                .originalFilename(originalFilename)
                .storeFilename(storeFilename)
                .build();
    }

    //다수 파일 저장
    public List<UploadFile> storeFiles (List<MultipartFile> multipartFiles) throws IOException {
        if (multipartFiles == null) {
            return null;
        }

        List<UploadFile> uploadFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        return uploadFiles;
    }

    //파일 저장소에 저장될 이름 생성
    private String createStroeFilename(String originalFilename) {
        return UUID.randomUUID() + "." + extractExt(originalFilename);
    }

    //확장자 파싱
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if (pos == -1) {
            return "";
        }
        return originalFilename.substring(pos + 1);
    }

}
