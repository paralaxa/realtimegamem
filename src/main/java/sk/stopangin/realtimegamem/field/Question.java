package sk.stopangin.realtimegamem.field;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Entity
public class Question {

    @Id
    private Long id;
    @Lob
    private String content;
    private String answer;
    private Integer score = 1;
}
