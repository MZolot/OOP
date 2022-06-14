package nsu.oop;

import picocli.CommandLine;
import nsu.oop.reports.Report;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "DSLReport", mixinStandardHelpOptions = true)
public class Commands implements Callable<Integer> {

    @CommandLine.Option(names = "report", arity = "1", description = "configures the system and prints a report")
    String repositoriesPath;

    @Override
    public Integer call() throws Exception {
        if (repositoriesPath != null) {
            Report report = new Report(repositoriesPath);
            report.printReport();
        }
        return null;
    }

}
