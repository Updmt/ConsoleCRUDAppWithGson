package repository;

import model.Label;

import java.util.List;

public interface LabelRepository extends GenericRepository<Label, Long>{
    Label findLabelByIdFromPost(List<Label> labelList, String labelId);
}
