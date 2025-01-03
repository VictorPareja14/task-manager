package es.enterprise.task.manager.controller;

import es.enterprise.task.manager.dto.TaskDTO;
import es.enterprise.task.manager.entity.Task;
import es.enterprise.task.manager.mapper.TaskMapper;
import es.enterprise.task.manager.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tareas", description = "Endpoints para gestionar tareas")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Obtener todas las tareas
    @GetMapping
    @Operation(summary = "Obtener todas las tareas", description = "Devuelve una lista de todas las tareas existentes")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDTOs);
    }

    // Obtener una tarea por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por su ID", description = "Devuelve un objeto TaskDTO con la información de la tarea")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            TaskDTO taskDTO = TaskMapper.toDTO(task.get());
            return ResponseEntity.ok(taskDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 si no existe
        }
    }

    // Crear una nueva tarea
    @PostMapping
    @Operation(summary = "Crear una nueva tarea", description = "Crea y devuelve la nueva tarea")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        if (taskDTO == null || taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 si falta información
        }
        Task task = TaskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = TaskMapper.toDTO(savedTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDTO); // 201 Created
    }

    // Borrar una tarea por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una tarea", description = "Elimina una tarea existente por su ID")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Tarea eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        }
    }
}
