package ru.nsu.gaskov.gradebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a student's grade book, containing information about their grades,
 * study form, and academic performance.
 */
public class GradeBook {
    private final String studentName;
    private StudyForm studyForm;
    private final List<Grade> grades = new ArrayList<>();
    private GradeScore qualificationWorkGrade;
    private int currentSemester = 1;

    /**
     * Creates a new grade book for a student.
     */
    public GradeBook(String studentName, StudyForm studyForm) {
        this.studentName = studentName;
        this.studyForm = studyForm;
    }

    /**
     * Returns the student's name.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns the current study form of the student.
     */
    public StudyForm getStudyForm() {
        return studyForm;
    }

    /**
     * Sets a new study form for the student.
     */
    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    /**
     * Returns a list of all grades in the grade book.
     */
    public List<Grade> getGrades() {
        return new ArrayList<>(grades);
    }

    /**
     * Returns the grade for the qualification work.
     */
    public GradeScore getQualificationWorkGrade() {
        return qualificationWorkGrade;
    }

    /**
     * Sets the grade for the qualification work.
     */
    public void setQualificationWorkGrade(GradeScore qualificationWorkGrade) {
        this.qualificationWorkGrade = qualificationWorkGrade;
    }

    /**
     * Returns the current semester of the student.
     */
    public int getCurrentSemester() {
        return currentSemester;
    }

    /**
     * Updates the current semester of the student.
     */
    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Adds a new grade to the grade book.
     */
    public void addGrade(Grade grade) {
        if (grade.semester() > currentSemester) {
            currentSemester = grade.semester();
        }
        grades.add(grade);
    }

    /**
     * Calculates the average grade for all recorded grades.
     */
    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        int totalScore = 0;
        int gradesCount = 0;
        for (Grade grade : grades) {
            totalScore += grade.getScore();
            if (grade.getScore() != 0) {
                gradesCount += 1;
            }
        }
        return (double) totalScore / gradesCount;
    }

    /**
     * Determines if the student is eligible to transfer to the budget study form.
     */
    public boolean canTransferToBudget() {
        if (studyForm != StudyForm.PAID) {
            return false;
        }

        List<Grade> lastTwoSessions = grades.stream()
            .filter(grade -> grade.semester() >= currentSemester - 1)
            .filter(grade -> grade.creditType() == CreditType.EXAM)
            .toList();

        return lastTwoSessions.stream().noneMatch(grade -> grade.getScore() <= 3);
    }

    /**
     * Determines if the student is eligible for a red diploma.
     */
    public boolean canGetRedDiploma() {
        Map<String, Grade> lastGrades = new HashMap<>();

        grades.forEach(
            grade -> {
                if ((!lastGrades.containsKey(grade.subject())
                    || grade.semester() > lastGrades.get(grade.subject()).semester())
                    && grade.creditType() != CreditType.CREDIT) {
                    lastGrades.put(grade.subject(), grade);
                }
            }
        );

        long excellentCount = lastGrades.keySet()
            .stream()
            .filter(subject -> lastGrades.get(subject).gradeScore() == GradeScore.EXCELLENT)
            .count();

        if ((double) excellentCount / lastGrades.size() < 0.75) {
            return false;
        }

        boolean noSatisfactoryGrades = grades.stream().noneMatch(grade -> grade.getScore() == 3);
        return noSatisfactoryGrades
            && (qualificationWorkGrade == null || qualificationWorkGrade == GradeScore.EXCELLENT);
    }

    /**
     * Determines if the student is eligible for an increased scholarship in the current semester.
     */
    public boolean canGetIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        List<Grade> currentSemesterGrades = grades.stream()
            .filter(grade -> grade.semester() == currentSemester)
            .toList();

        return currentSemesterGrades.stream().noneMatch(grade -> grade.getScore() < 5);
    }
}
