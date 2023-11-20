package test.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Getter
@Builder
public class UploadFile {
    private String originalFilename;
    private String storeFilename;

    protected UploadFile(){}
}
