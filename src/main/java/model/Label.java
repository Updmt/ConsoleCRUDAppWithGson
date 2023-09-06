package model;

import lombok.*;
import repository.Identify;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Label implements Identify {
    private Long id;
    private String name;
    private Status status;
}
