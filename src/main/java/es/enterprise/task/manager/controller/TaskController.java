package es.enterprise.task.manager.controller;

import es.enterprise.task.manager.dto.TaskDTO;
import es.enterprise.task.manager.exception.InvalidTaskDataException;
import es.enterprise.task.manager.service.TaskService;
import es.enterprise.task.manager.exception.TaskNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tareas", description = "Endpoints para gestionar tareas")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Obtener todas las tareas
    @GetMapping
    @Operation(summary = "Obtener todas las tareas", description = "Devuelve una lista de todas las tareas existentes")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Obtener una tarea por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por su ID", description = "Devuelve un objeto TaskDTO con la información de la tarea")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        if (taskDTO != null) {
            return ResponseEntity.ok(taskDTO);
        } else {
            throw new TaskNotFoundException(id.toString()); // Lanzamos una excepción personalizada
        }
    }

    // Crear una nueva tarea
    @PostMapping
    @Operation(summary = "Crear una nueva tarea", description = "Crea y devuelve la nueva tarea")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            throw new InvalidTaskDataException("El título de la tarea no puede estar vacío");
        }
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask); // 201 Created
    }

    // Borrar una tarea por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una tarea", description = "Elimina una tarea existente por su ID")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.ok("Tarea eliminada correctamente");
        } else {
            throw new TaskNotFoundException("Tarea no encontrada con el id "+id.toString()); // Lanzamos una excepción personalizada
        }
    }

    // Actualizar una tarea por ID
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea", description = "Actualiza la información de una tarea existente")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            throw new TaskNotFoundException(id.toString()); // Lanzamos una excepción personalizada
        }
    }

    // Consultar tareas por estado (pending/done)
    @GetMapping("/status/{status}")
    @Operation(summary = "Consultar tareas por estado", description = "Devuelve las tareas que tienen el estado especificado")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable String status) {
        List<TaskDTO> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 si no hay tareas con ese estado
        }
        return ResponseEntity.ok(tasks);
    }
}
