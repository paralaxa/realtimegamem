package sk.stopangin.realtimegamem.repository;


import sk.stopangin.realtimegamem.field.Question;

import java.util.List;

public interface QuestionsRepository {
    List<Question> getAllQuestions();
}
