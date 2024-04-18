package ch.kabashi.janie.MyToDo.tag;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import ch.kabashi.janie.MyToDo.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getTag(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) {
        Tag savedTag = tagService.insertTag(tag);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Tag> updateTag(@Valid @RequestBody Tag tag, @PathVariable Long id) {
        Tag updatedTag = tagService.updateTag(tag, id);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteTag(@PathVariable Long id) {
        MessageResponse response = tagService.deleteTag(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
