import java.util.ArrayList;
import java.util.Comparator;

public class RecordBook {

    private class SubjectInfo {
        public final String name;
        public int grade;
        public final int semester;

        public SubjectInfo(String subjName, int subjSemester) {
            name = subjName;
            semester = subjSemester;
        }
    }

    private int gradesSum;
    private int gradedNum;
    public int qualificationGrade;
    private final ArrayList<SubjectInfo> allInfo;

    public RecordBook() {
        allInfo = new ArrayList<>();
    }

    public void addSubject(String subjName, int subjSemester) {
        SubjectInfo newSubj = new SubjectInfo(subjName, subjSemester);
        allInfo.add(newSubj);
    }

    public void addSubjectsForSemester(int semester, String[] subjects) {
        if(subjects == null || subjects.length == 0) {
            throw new IllegalArgumentException("Subjects list is empty/null.");
        }
        for (String subject : subjects) {
            SubjectInfo newSubj = new SubjectInfo(subject, semester);
            allInfo.add(newSubj);
        }
    }

    public void grade(String subjName, int subjSemester, int grade) {
        for (int i = allInfo.size() - 1; i >= 0; i--) {
            if (allInfo.get(i).name.equals(subjName)) {
                if (allInfo.get(i).semester == subjSemester) {
                    if (allInfo.get(i).grade != 0) {
                        gradesSum -= allInfo.get(i).grade;
                        gradedNum--;
                    }
                    allInfo.get(i).grade = grade;
                    gradesSum += allInfo.get(i).grade;
                    gradedNum++;
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Trying to grade non-existent subject");
    }

    public Boolean stipend(int semester) {
        allInfo.sort(Comparator.comparing(this::getName));
        allInfo.sort(Comparator.comparing(this::getSemester));
        int i = 0;
        while (i < allInfo.size() && allInfo.get(i).semester != semester) {
            i++;
        }
        if (i >= allInfo.size()) {
            throw new IllegalArgumentException("No information about this semester");
        }
        while (i < allInfo.size() && allInfo.get(i).semester == semester) {
            if (allInfo.get(i).grade != 5) {
                return false;
            }
            i++;
        }
        return true;
    }

    public Boolean redDiploma() {
        allInfo.sort(Comparator.comparing(this::getSemester));
        allInfo.sort(Comparator.comparing(this::getName));
        int lastGradeAvg = 0;
        if (allInfo.get(allInfo.size() - 1).grade == 3) {
            return false;
        }
        for (int i = allInfo.size() - 2; i >= 0; i--) {
            if (allInfo.get(i).grade == 3) {
                return false;
            }
            if (!allInfo.get(i).name.equals(allInfo.get(i+1).name)) {
                lastGradeAvg += allInfo.get(i).grade;
            }
        }
        return (qualificationGrade == 5 && lastGradeAvg >= 4.75);
    }

    public void printAll() {
        System.out.println(allInfo.size());
        for (SubjectInfo allSubject : allInfo) {
            System.out.printf("%s, %d: %d\n", allSubject.name,
                    allSubject.semester, allSubject.grade);
        }
        System.out.println();
    }

    private int getSemester(SubjectInfo subj) {
        return subj.semester;
    }

    private String getName(SubjectInfo subj) {
        return subj.name;
    }

    public float AverageGrade() {
        return (float)gradesSum / gradedNum;
    }


}
