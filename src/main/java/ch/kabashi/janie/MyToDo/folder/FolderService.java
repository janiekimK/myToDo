package ch.kabashi.janie.MyToDo.folder;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import ch.kabashi.janie.MyToDo.storage.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private final FolderRepository repository;

    public FolderService(FolderRepository repository) {
        this.repository = repository;
    }

    public List<Folder> getFolders() {
        return repository.findByOrderByTitleAsc();
    }

    public Folder getFolder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Folder.class));
    }

    public Folder insertFolder(Folder folder) {
        return repository.save(folder);
    }

    public Folder updateFolder(Folder folder, Long id) {
        return repository.findById(id)
                .map(folderOrig -> {
                    folderOrig.setTitle(folder.getTitle());
                    folderOrig.setDescription(folder.getDescription());
                    return repository.save(folderOrig);
                })
                .orElseGet(() -> repository.save(folder));
    }

    public MessageResponse deleteFolder(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Folder " + id + " deleted");
    }
}
