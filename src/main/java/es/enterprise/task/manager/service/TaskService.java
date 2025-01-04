package es.enterprise.task.manager.service;

import es.enterprise.task.manager.dto.TaskDTO;
import es.enterprise.task.manager.entity.Task;
import es.enterprise.task.manager.exception.InvalidTaskDataException;
import es.enterprise.task.manager.exception.TaskNotFoundException;
import es.enterprise.task.manager.mapper.TaskMapper;
import es.enterprise.task.manager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Obtener todas las tareas
    public List<TaskDTO> getAllTasks() {
        log.info("Obteniendo todas las tareas");
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            log.warn("No se encontraron tareas");
        }
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener una tarea por ID
    public TaskDTO getTaskById(Long id) {
        log.info("Buscando tarea con ID: {}", id);
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            log.info("Tarea encontrada: {}", task.get().getTitle());
            return TaskMapper.toDTO(task.get());
        } else {
            log.error("Tarea con ID: {} no encontrada", id);
            throw new TaskNotFoundException("Tarea no encontrada con ID: " + id.toString());
        }
    }

    // Crear una nueva tarea
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO == null || taskDTO.getTitle() == null || taskDTO.getTitle().isEmpty()) {
            log.error("Datos inválidos al intentar crear una tarea: {}", taskDTO);
            throw new InvalidTaskDataException("El título de la tarea no puede estar vacío");
        }
        log.info("Creando nueva tarea con título: {}", taskDTO.getTitle());
        Task task = TaskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        log.info("Tarea creada con éxito: {}", savedTask.getTitle());
        return TaskMapper.toDTO(savedTask);
    }

    // Borrar una tarea por ID
    public boolean deleteTask(Long id) {
        log.info("Intentando borrar tarea con ID: {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            log.info("Tarea con ID: {} eliminada exitosamente", id);
            return true;
        } else {
            log.error("Tarea con ID: {} no encontrada para eliminar", id);
            throw new TaskNotFoundException("Tarea no encontrada con ID: " + id);
        }
    }

    // Actualizar una tarea
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Actualizando tarea con ID: {}", id);
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());

            Task updatedTask = taskRepository.save(task);
            log.info("Tarea actualizada con éxito: {}", updatedTask.getTitle());
            return TaskMapper.toDTO(updatedTask);
        } else {
            log.error("Tarea con ID: {} no encontrada para actualizar", id);
            throw new TaskNotFoundException("Tarea no encontrada con ID: " + id);
        }
    }

    // Consultar tareas por estado
    public List<TaskDTO> getTasksByStatus(String status) {
        log.info("Buscando tareas con estado: {}", status);
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            log.warn("No se encontraron tareas con estado: {}", status);
        } else {
            log.info("Se han encontrado {} tareas con estado: {}", tasks.size(), status);
        }
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}
