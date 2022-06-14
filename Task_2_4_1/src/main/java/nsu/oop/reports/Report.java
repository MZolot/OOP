package nsu.oop.reports;

import nsu.oop.model.*;
import nsu.oop.handlers.*;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Report {

    private final String repositories;
    private List<TaskReport> taskReports;

    public Report(String repositoriesPath) {
        repositories = repositoriesPath;
    }

    public void writeNewReport() throws IOException, NoSuchFieldException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, GitAPIException {

        Tasks tasks = new Tasks();
        tasks.configure("Task_2_4_1/src/main/java/nsu/oop/configs/tasks_config.groovy", true);

        Group groupConfig = new Group();
        groupConfig.configure("Task_2_4_1/src/main/java/nsu/oop/configs/group_config.groovy", true);
        List<Student> students = groupConfig.getStudents();

        RepositoryHandler repH = new RepositoryHandler(repositories);
        repH.cloneRepository(students);

        taskReports = new ArrayList<>();

        for (Task task : tasks.getTasks()) {
            if (!task.isGiven()) {
                continue;
            }
            taskReports.add(new TaskReport(task, students, repositories));
        }
    }

    public void printReport() throws GitAPIException, IOException, NoSuchFieldException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (taskReports == null) {
            writeNewReport();
        }

        System.out.println("\nFinal report:");
        for (TaskReport taskReport : taskReports) {
            printTask(taskReport);
        }
    }

    private void printTask(TaskReport report) {
        System.out.printf("\n%8s     Task %s (%s)   \n", "", report.getId(), report.getName());
        System.out.print("|---------------------------------------------|\n");
        System.out.printf("|%21s| Build | Tests | Score |\n", "");
        for (TaskReport.StudentReport studentReport : report.getResults()) {
            System.out.print("|---------------------------------------------|\n");
            System.out.printf("|%20s ", studentReport.name());
            if (studentReport.buildResult()) {
                System.out.print("|   +   ");
            } else {
                System.out.print("|   -   ");
            }
            if (studentReport.testResult()) {
                System.out.print("|   +   ");
            } else {
                System.out.print("|   -   ");
            }
            System.out.printf("|  %.1f  |\n", studentReport.score());
        }
        System.out.print("|---------------------------------------------|\n\n");
    }
}
