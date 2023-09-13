package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Writer implements Identify<Long> {

    private Long id;
    private String firstName;
    private String lastName;
    private Status status;
    private List<Post> posts;
}
