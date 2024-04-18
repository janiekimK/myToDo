package ch.kabashi.janie.MyToDo.folder;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import ch.kabashi.janie.MyToDo.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("api/folder")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Folder>> all() {
        List<Folder> result = folderService.getFolders();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/folder/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Folder> one(@PathVariable Long id) {
        Folder folder = folderService.getFolder(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @PostMapping("api/folder")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Folder> newFolder(@Valid @RequestBody Folder folder) {
        Folder savedFolder = folderService.insertFolder(folder);
        return new ResponseEntity<>(savedFolder, HttpStatus.OK);
    }

    @PutMapping("api/folder/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Folder> updateFolder(@Valid @RequestBody Folder folder, @PathVariable Long id) {
        Folder savedFolder = folderService.updateFolder(folder, id);
        return new ResponseEntity<>(savedFolder, HttpStatus.OK);
    }

    @DeleteMapping("api/folder/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteFolder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(folderService.deleteFolder(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
