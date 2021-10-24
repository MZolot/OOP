import java.util.*;

public class RecordBook {

    record SubjectInfo(String name, int semester) {
    }

    private int qualificationGrade;
    private final Map<SubjectInfo, Integer> allInfo;
    private final Map<String, Integer> diplomaApplication;
    private final int[] stipend;


    public RecordBook() {
        allInfo = new HashMap<>();
        diplomaApplication = new HashMap<>();
        stipend = new int[20];
        Arrays.fill(stipend, -1);
    }

    public void addSubject(String subjName, int subjSemester) {
        allInfo.put(new SubjectInfo(subjName, subjSemester), 0);
    }

    public void addSubjectsForSemester(int semester, String[] subjects) {
        if (subjects == null || subjects.length == 0) {
            throw new IllegalArgumentException("Subjects list is empty/null.");
        }
        for (String subject : subjects) {
            addSubject(subject, semester);
        }
    }

    public void grade(String subjName, int subjSemester, int grade) {
        allInfo.put(new SubjectInfo(subjName, subjSemester), grade);
        diplomaApplication.put(subjName, grade);
        if (stipend[subjSemester] == -1) {
            stipend[subjSemester] = 1;
        }
        if (grade != 5) {
            stipend[subjSemester] = 0;
        }
    }

    public float averageGrade() {
        Collection<Integer> grades = allInfo.values();
        Integer sum = grades.stream().reduce(0, Integer::sum);
        long length = grades.stream().filter(a -> a > 0).count();
        return (float) sum / length;
    }

    public void setQualificationGrade(int grade) {
        qualificationGrade = grade;
    }

    public int getQualificationGrade() {
        return qualificationGrade;
    }

    public boolean isRedDiploma() {
        Collection<Integer> grades = allInfo.values();
        Collection<Integer> applicationGrades = diplomaApplication.values();
        long goodGradesSum = applicationGrades.stream().reduce(0, Integer::sum);
        return grades.stream().allMatch(a -> a > 3)
                && (qualificationGrade == 5)
                && ((float) goodGradesSum / diplomaApplication.size() >= 4.75);
    }

    public boolean isStipend(int semester) {
        if (stipend[semester] == -1) {
            throw new IllegalArgumentException("This semester wasn't graded yet");
        }
        return stipend[semester] == 1;
    }

}
