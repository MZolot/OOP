package model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDescription extends GroovyConfigurable {
    private String id;
    private String name;
    private int maxScore;

}
