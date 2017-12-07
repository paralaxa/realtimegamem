package sk.stopangin.realtimegamem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.stopangin.realtimegamem.field.Question;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long> {
}
