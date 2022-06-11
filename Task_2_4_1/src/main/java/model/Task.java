package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private int maxScore;
    private boolean given;
}
