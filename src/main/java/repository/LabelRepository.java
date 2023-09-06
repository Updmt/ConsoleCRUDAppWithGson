package repository;

import com.google.gson.reflect.TypeToken;
import model.Label;
import model.Status;
import utils.Utils;

import java.util.List;

public class LabelRepository extends AbstractFileRepository<Label, Long>{

    private static final String DEFAULT_FILE_NAME = "labels.json";

    public LabelRepository() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Label>>() {}.getType());
    }

    @Override
    public void save(Label label) {
        List<Label> labelList = getAll();
        labelList.add(label);
        writeListToFile(labelList);
    }

    @Override
    public Label getById(Long id) {
        return getAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Label update(Label label) {
        List<Label> LabelList = getAll();
        int index = Utils.findIndexInList(LabelList, label);
        LabelList.set(index, label);
        writeListToFile(LabelList);
        return label;
    }

    @Override
    public Label deleteById(Long id) {
        List<Label> LabelList = getAll();
        Label label = getById(id);
        int index = Utils.findIndexInList(LabelList, label);
        label.setStatus(Status.DELETED);
        LabelList.set(index, label);
        writeListToFile(LabelList);
        return label;
    }

    public Label findLabelByIdFromPost(List<Label> listLabel, String labelId) {
        return listLabel.stream()
                .filter(p -> p.getId().equals(Long.valueOf(labelId)))
                .findFirst()
                .orElse(null);
    }
}
