package nsu.oop.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tasks extends GroovyConfigurable {
    private List<Task> tasks;
}
