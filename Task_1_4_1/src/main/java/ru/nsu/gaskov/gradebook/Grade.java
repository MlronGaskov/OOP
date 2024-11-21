package ru.nsu.gaskov.gradebook;

/**
 * Represents a grade in a specific subject and semester.
 */
public record Grade(
    int semester,
    String subject,
    CreditType creditType,
    GradeScore gradeScore) {

    /**
     * Returns the numeric score for the grade.
     */
    public int getScore() {
        return switch (gradeScore) {
            case FAIL -> 2;
            case SATISFACTORY -> creditType != CreditType.CREDIT ? 3 : 0;
            case GOOD -> 4;
            case EXCELLENT -> 5;
        };
    }
}
