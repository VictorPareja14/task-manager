package es.enterprise.task.manager.service;

import es.enterprise.task.manager.dto.TaskDTO;
import es.enterprise.task.manager.entity.Task;
import es.enterprise.task.manager.exception.TaskNotFoundException;
import es.enterprise.task.manager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetTaskById_Success() {
        // Arrange
        Long taskId = 1L;
        Task mockTask = new Task(taskId, "Test Task", "Description", "pending");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        // Act
        TaskDTO result = taskService.getTaskById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testGetTaskById_NotFound() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testCreateTask() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO(null, "New Task", "Description", "pending");
        Task mockTask = new Task(1L, "New Task", "Description", "pending");
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        // Act
        TaskDTO result = taskService.createTask(taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }


    @Test
    public void testDeleteTask() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);

        // Act
        boolean result = taskService.deleteTask(taskId);

        // Assert
        assertTrue(result);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testUpdateTask() {
        // Arrange
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO(null, "Updated Task", "Updated Description", "done");
        Task mockTask = new Task(taskId, "Old Task", "Old Description", "pending");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        // Act
        TaskDTO result = taskService.updateTask(taskId, taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("done", result.getStatus());
        verify(taskRepository, times(1)).save(mockTask);
    }

    @Test
    public void testGetTasksByStatus() {
        // Arrange
        String status = "pending";
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1", "pending"),
                new Task(2L, "Task 2", "Description 2", "pending")
        );
        when(taskRepository.findByStatus(status)).thenReturn(mockTasks);

        // Act
        List<TaskDTO> result = taskService.getTasksByStatus(status);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByStatus(status);
    }
}
