package sk.stopangin.realtimegamem.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.stopangin.realtimegamem.field.*;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinates;
import sk.stopangin.realtimegamem.movement.TwoDimensionalCoordinatesData;
import sk.stopangin.realtimegamem.repository.QuestionsRepository;

import java.util.List;
import java.util.Random;

public class ActionFieldInserter {
    private static final Logger log = LoggerFactory.getLogger(ActionFieldInserter.class);

    private Board board;
    private QuestionsRepository questionsRepository;
    private int sizeOfBoard;
    private List<Question> questions;

    public ActionFieldInserter(Board board, QuestionsRepository questionsRepository) {
        this.board = board;
        this.questionsRepository = questionsRepository;
    }

    public void init() {
        sizeOfBoard = (int) Math.sqrt(board.getFields().size());
        questions = questionsRepository.findAll();
    }

    public void insertActionFields(int number) {
        for (int i = 0; i < number; i++) {
            insertActionField();
        }
    }

    private void insertActionField() {
        if (!questions.isEmpty()) {
            TwoDimensionalCoordinates coordinates = generateRandomCoordinates();
            Field<TwoDimensionalCoordinatesData> fieldForCoordinates = board.getFieldForCoordinates(coordinates);
            if (fieldDoesNotHavePieceOnIt(fieldForCoordinates) && isNotActionField(fieldForCoordinates)) {
                board.getFields().remove(fieldForCoordinates);
                ActionField actionField = createNewActionField(coordinates);
                board.getFields().add(actionField);
                log.info("Inserting action field on coordinates : {}", coordinates);
            } else {
                insertActionField();
            }
        }
    }

    private ActionField createNewActionField(TwoDimensionalCoordinates coordinates) {
        ActionField actionField = new ActionField();
        actionField.setPosition(coordinates);
        Question question = getFirstQuestionFromList();
        ActionData actionData = ActionData.builder()
                .info(question.getContent())
                .data(question.getAnswer())
                .blocking(true)
                .build();
        actionData.setId(question.getId());
        QuestionAction questionAction = new QuestionAction(actionData);
        actionField.setAction(questionAction);
        questions.remove(question);
        return actionField;
    }

    private Question getFirstQuestionFromList() {
        return questions.get(0);
    }

    private boolean isNotActionField(Field<TwoDimensionalCoordinatesData> fieldForCoordinates) {
        return !(fieldForCoordinates instanceof ActionField);
    }

    private boolean fieldDoesNotHavePieceOnIt(Field<TwoDimensionalCoordinatesData> fieldForCoordinates) {
        return fieldForCoordinates.getPieces().isEmpty();
    }

    private TwoDimensionalCoordinates generateRandomCoordinates() {
        Random random = new Random();
        int x = random.nextInt(sizeOfBoard - 1) + 2;
        int y = random.nextInt(sizeOfBoard - 1) + 2;
        return new TwoDimensionalCoordinates(new TwoDimensionalCoordinatesData(x, y));
    }
}
