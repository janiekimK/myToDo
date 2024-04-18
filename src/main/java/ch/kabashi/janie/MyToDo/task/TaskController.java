package ch.kabashi.janie.MyToDo.task;

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
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("api/tasks")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<List<Task>> allTasks() {
        List<Task> tasks = taskService.getTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("api/tasks/{id}")
    @RolesAllowed(Roles.Read)
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("api/tasks")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = taskService.insertTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @PutMapping("api/tasks/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        Task updatedTask = taskService.updateTask(task, id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("api/tasks/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(taskService.deleteTask(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
