package test.domain.embedded;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Data
public class TimeStamps {
    private Date createDate;    //생성 일자
    private Date modifyDate;    //수정 일자

}
