import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordBookTest {

    @Test
    public void testMyOwnRecordBook() {
        RecordBook r = new RecordBook();
        String[] semester1subj = {"Mathematical analysis",
                "Mathematical logic",
                "Declarative programming",
                "Imperative programming"};
        r.addSubjectsForSemester(1, semester1subj);
        r.addSubject("Digital platforms", 1);
        r.grade("Mathematical analysis", 1, 4);
        r.grade("Mathematical logic", 1, 4);
        r.grade("Declarative programming", 1, 5);
        r.grade("Imperative programming", 1, 5);
        r.grade("Digital platforms", 1, 4);
        assertFalse(r.isStipend(1));

        String[] semester2subj = {"Mathematical analysis",
                "Mathematical logic",
                "Declarative programming",
                "Imperative programming",
                "Digital platforms"};
        r.addSubjectsForSemester(2, semester2subj);
        r.grade("Mathematical analysis", 2, 5);
        r.grade("Mathematical logic", 2, 5);
        r.grade("Declarative programming", 2, 4);
        r.grade("Imperative programming", 2, 4);
        r.grade("Digital platforms", 2, 5);
        assertFalse(r.isStipend(2));
        assertEquals(4.5, r.averageGrade());
        assertFalse(r.isRedDiploma());
    }

    RecordBook recordBook = new RecordBook();

    @Test
    public void testSubjectsNullListException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> recordBook.addSubjectsForSemester(1, null));
    }

    @Test
    public void testSubjectsEmptyListException() {
        String[] emptyList = {};
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> recordBook.addSubjectsForSemester(1, emptyList));
    }

    @Test
    public void testStipendForWrongSemester() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> recordBook.isStipend(5));
    }

    @Test
    public void testStipend() {
        recordBook.grade("Math", 1, 5);
        recordBook.grade("History", 1, 5);
        recordBook.grade("English", 1, 5);
        Assertions.assertTrue(recordBook.isStipend(1));
    }

    @Test
    public void testRedDiploma() {
        recordBook.grade("Math", 1, 5);
        recordBook.grade("History", 1, 4);
        recordBook.grade("English", 1, 4);
        recordBook.grade("Programming", 1, 5);
        recordBook.grade("English", 2, 5);
        recordBook.setQualificationGrade(5);
        Assertions.assertTrue(recordBook.isRedDiploma());
    }

    @Test
    public void testNoRedDiplomaBadGrades() {
        recordBook.grade("Math", 1, 5);
        recordBook.grade("History", 1, 5);
        recordBook.grade("English", 1, 5);
        recordBook.grade("Programming", 1, 5);
        recordBook.grade("History", 2, 4);
        recordBook.grade("English", 2, 4);
        recordBook.setQualificationGrade(5);
        Assertions.assertFalse(recordBook.isRedDiploma());
    }

    @Test
    public void testNoRedDiplomaBadQualificationWork() {
        recordBook.grade("Math", 1, 5);
        recordBook.grade("History", 1, 5);
        recordBook.grade("English", 1, 5);
        recordBook.grade("Programming", 1, 5);
        recordBook.setQualificationGrade(3);
        Assertions.assertFalse(recordBook.isRedDiploma());
    }

}
