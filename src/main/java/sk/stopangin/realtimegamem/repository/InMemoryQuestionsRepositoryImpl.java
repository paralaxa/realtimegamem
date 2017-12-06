package sk.stopangin.realtimegamem.repository;

import org.springframework.stereotype.Component;
import sk.stopangin.realtimegamem.field.Question;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryQuestionsRepositoryImpl implements QuestionsRepository {
    private List<Question> questions = new ArrayList<>();

    private void init() {
        Question q1 = Question.builder()
                .id(1l)
                .content("kolko dni ma januar")
                .score(1)
                .answer("31")
                .build();

        Question q2 = Question.builder()
                .id(2l)
                .content("kolko dni ma marec")
                .score(1)
                .answer("31")
                .build();

        Question q3 = Question.builder()
                .id(3l)
                .content("kolko dni ma april")
                .score(1)
                .answer("30")
                .build();

        Question q4 = Question.builder()
                .id(4l)
                .content("kolko dni ma maj")
                .score(1)
                .answer("31")
                .build();

        Question q5 = Question.builder()
                .id(5l)
                .content("kolko dni ma jun")
                .score(1)
                .answer("30")
                .build();


        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);

        questions.addAll(createXQuestions(30));

    }

    public List<Question> createXQuestions(int x) {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i < x; i++) {
            questions.add(Question.builder()
                    .id(i+5l)
                    .content("kolko dni ma jun"+i)
                    .score(1)
                    .answer("30")
                    .build());
        }
        return questions;
    }

    public List<Question> getAllQuestions() {
        init();
        return questions;
    }
}
