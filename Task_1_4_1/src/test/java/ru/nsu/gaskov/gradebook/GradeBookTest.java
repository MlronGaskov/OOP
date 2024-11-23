package ru.nsu.gaskov.gradebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests.
 */
class GradeBookTest {
    private GradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
    }

    @Test
    void testAddGrade() {
        Grade grade = new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT);
        gradeBook.addGrade(grade);

        assertEquals(1, gradeBook.getGrades().size());
        assertEquals(grade, gradeBook.getGrades().getFirst());
        assertEquals(1, gradeBook.getCurrentSemester());
    }

    @Test
    void testCalculateAverageGrade() {
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.addGrade(
            new Grade(1, "Programming", CreditType.GRADED_CREDIT, GradeScore.EXCELLENT)
        );

        double average = gradeBook.calculateAverageGrade();
        assertEquals(4.5, average, 0.01); // Средняя оценка: (4+5)/2
    }

    @Test
    void testCanTransferToBudget() {
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.addGrade(new Grade(1, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(
            new Grade(2, "Chemistry", CreditType.EXAM, GradeScore.SATISFACTORY)
        );

        assertFalse(gradeBook.canTransferToBudget());

        gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.addGrade(new Grade(2, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));

        assertTrue(gradeBook.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma() {
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(1, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertTrue(gradeBook.canGetRedDiploma());

        gradeBook.addGrade(new Grade(3, "History", CreditType.EXAM, GradeScore.SATISFACTORY));
        assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(1, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setCurrentSemester(1);

        assertTrue(gradeBook.canGetIncreasedScholarship());

        gradeBook.addGrade(new Grade(1, "History", CreditType.EXAM, GradeScore.GOOD));
        assertFalse(gradeBook.canGetIncreasedScholarship());
    }

    @Test
    void testAllConditionsMet() {
        // Успешное выполнение всех условий
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(1, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNotEnoughExcellents() {
        // Менее 75% оценок "Отлично"
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(1, "Physics", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testSatisfactoryGradePresent() {
        // Есть "Удовлетворительная" оценка
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Physics", CreditType.EXAM, GradeScore.SATISFACTORY));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testNoQualificationWorkGrade() {
        // Нет оценки за дипломную работу
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));

        assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testFailingQualificationWorkGrade() {
        // Неудовлетворительная оценка за дипломную работу
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.PAID);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.SATISFACTORY);

        assertFalse(gradeBook.canGetRedDiploma());
    }

    @Test
    void testCreditsNotCounted() {
        // Зачеты не влияют на результат
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.BUDGET);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.CREDIT, GradeScore.SATISFACTORY));
        gradeBook.addGrade(new Grade(1, "Physics", CreditType.CREDIT, GradeScore.SATISFACTORY));
        gradeBook.addGrade(new Grade(2, "Programming", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertTrue(gradeBook.canGetRedDiploma());
    }

    @Test
    void testMultipleGradesForSameSubject() {
        // Учитывается последняя оценка по предмету
        GradeBook gradeBook = new GradeBook("Ivan Ivanov", StudyForm.BUDGET);
        gradeBook.addGrade(new Grade(1, "Mathematics", CreditType.EXAM, GradeScore.GOOD));
        gradeBook.addGrade(new Grade(2, "Mathematics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.addGrade(new Grade(1, "Physics", CreditType.EXAM, GradeScore.EXCELLENT));
        gradeBook.setQualificationWorkGrade(GradeScore.EXCELLENT);

        assertTrue(gradeBook.canGetRedDiploma());
    }
}
