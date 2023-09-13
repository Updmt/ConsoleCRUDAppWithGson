package repository.gson;

import com.google.gson.reflect.TypeToken;
import model.Label;
import model.Status;
import repository.AbstractFileRepository;
import repository.LabelRepository;

import java.util.List;

public class GsonLabelRepositoryImpl extends AbstractFileRepository<Label> implements LabelRepository {

    private static final String DEFAULT_FILE_NAME = "src/main/resources/labels.json";
    IdRepository labelIdRepository = new IdRepository("src/main/resources/labels_id.txt");

    public GsonLabelRepositoryImpl() {
        super(DEFAULT_FILE_NAME, new TypeToken<List<Label>>() {}.getType());
    }

    @Override
    public Label save(Label label) {
        List<Label> labelList = getAll();
        label.setId(labelIdRepository.createId());
        labelList.add(label);
        writeListToFile(labelList);
        return label;
    }

    @Override
    public Label getById(Long id) {
        return getAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> getAll() {
        return getAllItemsInternal();
    }

    @Override
    public Label update(Label labelToUpdate) {
        List<Label> currentLabelList = getAll()
                .stream()
                .map(existingLabel -> {
                    if (existingLabel.getId().equals(labelToUpdate.getId())) {
                        return labelToUpdate;
                    } else {
                        return existingLabel;
                    }
                }).toList();
        writeListToFile(currentLabelList);
        return labelToUpdate;
    }

    @Override
    public void deleteById(Long labelIdToDelete) {
        List<Label> LabelList = getAll()
                .stream()
                .map(existingLabel -> {
                    if (existingLabel.getId().equals(labelIdToDelete)) {
                        existingLabel.setStatus(Status.DELETED);
                    }
                    return existingLabel;
                }).toList();
        writeListToFile(LabelList);
    }

    public Label findLabelByIdFromPost(List<Label> listLabel, String labelId) {
        return listLabel.stream()
                .filter(p -> p.getId().equals(Long.valueOf(labelId)))
                .findFirst()
                .orElse(null);
    }
}
