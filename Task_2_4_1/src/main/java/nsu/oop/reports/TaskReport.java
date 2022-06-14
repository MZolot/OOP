package nsu.oop.reports;

import lombok.Getter;
import nsu.oop.handlers.RepositoryHandler;
import nsu.oop.model.Mapping;
import nsu.oop.model.Student;
import nsu.oop.model.Task;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TaskReport {
    private final String id;
    private final String name;
    private final List<StudentReport> results;

    record StudentReport(String name, boolean buildResult, boolean testResult, float score) {
    }

    public TaskReport(Task task, List<Student> students, String repositoriesPath) throws GitAPIException {
        id = task.getId();
        name = task.getName();

        results = new ArrayList<>();
        for (Student student : students) {
            RepositoryHandler repH= new RepositoryHandler(repositoriesPath);
            if (!new File(repositoriesPath.concat(student.getNickname())).exists()) {
                if (!repH.cloneRepository(student) || !new File(repositoriesPath.concat(student.getNickname())).exists()) {
                    results.add(new StudentReport(student.getFullName(), false, false, 0));
                    continue;
                }
            }
            else {
                repH.initializeRepository(student);
            }

            String branch = branchToCheckout(student);
            if (branch != null) {
                if (!repH.checkoutToBranch(student.getNickname(), branch)) {
                    results.add(new StudentReport(student.getFullName(), false, false, 0));
                    continue;
                }
            }

            String folder = findTaskFolder(repositoriesPath, student);
            if (folder == null || !new File(folder).exists()) {
                results.add(new StudentReport(student.getFullName(), false, false, 0));
                continue;
            }

            nsu.oop.handlers.GradleHandler gradleHandler = new nsu.oop.handlers.GradleHandler();
            boolean buildRes = gradleHandler.buildTask(folder);
            boolean testRes = gradleHandler.testTask(folder);
            results.add(new StudentReport(student.getFullName(), buildRes, testRes,
                    calculateScore(buildRes, testRes, task.getMaxScore())));

            if (branch != null) {
                repH.checkoutToBranch(student.getNickname(), student.getMainBranch());
            }
        }
    }

    private float calculateScore(boolean buildResult, boolean testResult, int maxScore) {
        if (buildResult && testResult) {
            return maxScore;
        } else if (buildResult || testResult) {
            return (float) maxScore/ 2;
        } else {
            return 0;
        }
    }

    private String findTaskFolder(String repositoriesPath, Student student) {
        if (new File(repositoriesPath.concat(student.getNickname()).concat("/Task_").concat(id)).exists()) {
            return repositoriesPath.concat(student.getNickname()).concat("/Task_").concat(id);
        }
        if (student.getFolders() != null) {
            for (Mapping mapping : student.getFolders()) {
                if (mapping.getId().equals(id)) {
                    return repositoriesPath.concat(student.getNickname()).concat("/").concat(mapping.getPath());
                }
            }
        }
        return null;
    }

    private String branchToCheckout(Student student) {
        if (student.getBranches() != null) {
            for (Mapping mapping : student.getBranches()) {
                if (mapping.getId().equals(id)) {
                    return mapping.getPath();
                }
            }
        }
        return null;
    }
}
