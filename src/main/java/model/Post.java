package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Post implements Identify<Long> {

    private Long id;
    private String content;
    private String created;
    private String updated;
    private PostStatus postStatus;
    private List<Label> labels;
}
