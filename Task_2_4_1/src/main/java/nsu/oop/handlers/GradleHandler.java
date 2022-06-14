package nsu.oop.handlers;

import org.gradle.tooling.*;
import org.gradle.tooling.model.GradleProject;

import java.io.File;

public class GradleHandler {

    public boolean buildTask(String projectPath) {
        if (projectPath == null) {
            return false;
        }
        File file = new File(projectPath);
        if (!file.exists()) {
            return false;
        }

        ProjectConnection connection;
        try {
            connection = GradleConnector.newConnector()
                    .forProjectDirectory(file)
                    .connect();
        } catch (BuildException e) {
            return false;
        }

        System.out.println("Building " +
                connection.getModel(GradleProject.class).getProjectIdentifier().getBuildIdentifier());
        BuildLauncher build = connection.newBuild();
        build.forTasks("assemble");
        try {
            build.run();
        } catch (BuildException e) {
            //System.out.println("Build unsuccessful");
            connection.close();
            return false;
        }
        //System.out.println("Build successful");

        connection.close();

        return true;
    }

    public boolean testTask(String projectPath) {
        File file = new File(projectPath);
        if (!file.exists()) {
            return false;
        }

        ProjectConnection connection;
        try {
            connection = GradleConnector.newConnector()
                    .forProjectDirectory(new File(projectPath))
                    .connect();
        } catch (BuildException e) {
            //System.out.println("Incorrect path to the project");
            return false;
        }

        System.out.println("Testing " +
                connection.getModel(GradleProject.class).getProjectIdentifier().getBuildIdentifier().getRootDir());


        TestLauncher test = connection.newTestLauncher();
        test.withJvmTestClasses("*");
        try {
            test.run();
        } catch (BuildException | TestExecutionException e) {
            //System.out.println("One or more tests failed");
            connection.close();
            return false;
        }
        //System.out.println("Tests successful");

        connection.close();

        return true;
    }


}
