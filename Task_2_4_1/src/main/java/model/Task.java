package model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends GroovyObjectSupport {
    private String id;
    private LocalDateTime deadline;
}
