package ch.kabashi.janie.MyToDo.task;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import ch.kabashi.janie.MyToDo.storage.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getTasks() {
        return repository.findAll();
    }

    public Task getTask(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Task.class));
    }

    public Task insertTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Task task, Long id) {
        return repository.findById(id)
                .map(taskOrig -> {
                    taskOrig.setTitle(task.getTitle());
                    taskOrig.setDescription(task.getDescription());
                    taskOrig.setDueDate(task.getDueDate());
                    taskOrig.setPriority(task.getPriority());
                    return repository.save(taskOrig);
                })
                .orElseGet(() -> repository.save(task));
    }

    public MessageResponse deleteTask(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Task " + id + " deleted");
    }
}
