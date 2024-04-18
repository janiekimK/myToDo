package ch.kabashi.janie.MyToDo.tag;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag getTag(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    public Tag insertTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag, Long id) {
        tag.setId(id);
        return tagRepository.save(tag);
    }

    public MessageResponse deleteTag(Long id) {
        tagRepository.deleteById(id);
        return new MessageResponse("Tag " + id + " deleted");
    }
}
