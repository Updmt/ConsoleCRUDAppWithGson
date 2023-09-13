package controller;

import model.*;
import repository.LabelRepository;
import repository.gson.GsonLabelRepositoryImpl;


import java.util.List;


public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public Label createLabel(String name) {
        Label label = Label.builder()
                .name(name)
                .status(Status.CREATED)
                .build();
        return labelRepository.save(label);
    }

    public List<Label> getAllLabels() {
        return labelRepository.getAll();
    }

    public Label getLabel(String labelId) {
        return labelRepository.getById(Long.valueOf(labelId));
    }

    public Label updateLabel(String labelId, String updatedName) {
        Label label = labelRepository.getById(Long.valueOf(labelId));
        label.setName(updatedName);
        label.setStatus(Status.UPDATED);
        return labelRepository.update(label);
    }

    public void deleteLabel(String labelId) {
        labelRepository.deleteById(Long.valueOf(labelId));
    }
}
