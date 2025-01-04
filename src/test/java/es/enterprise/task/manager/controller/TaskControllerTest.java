package es.enterprise.task.manager.controller;

import es.enterprise.task.manager.dto.TaskDTO;
import es.enterprise.task.manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllTasks() {
        // Arrange
        List<TaskDTO> mockTasks = Arrays.asList(
                new TaskDTO(1L, "Task 1", "Description 1", "pending"),
                new TaskDTO(2L, "Task 2", "Description 2", "done")
        );
        when(taskService.getAllTasks()).thenReturn(mockTasks);

        // Act
        List<TaskDTO> tasks = taskController.getAllTasks().getBody();

        // Assert
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Task 1");
    }
    @Test
    public void testGetTaskById() {
        // Arrange
        Long taskId = 1L;
        TaskDTO mockTask = new TaskDTO(taskId, "Task 1", "Description 1", "pending");
        when(taskService.getTaskById(taskId)).thenReturn(mockTask);

        // Act
        TaskDTO task = taskController.getTaskById(taskId).getBody();

        // Assert
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo("Task 1");
    }
    @Test
    public void testCreateTask() {
        TaskDTO newTask = new TaskDTO(null, "New Task", "New Description", "pending");
        TaskDTO createdTask = new TaskDTO(1L, "New Task", "New Description", "pending");
        when(taskService.createTask(newTask)).thenReturn(createdTask);

        TaskDTO task = taskController.createTask(newTask).getBody();

        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getTitle()).isEqualTo("New Task");
        assertThat(task.getDescription()).isEqualTo("New Description");
        assertThat(task.getStatus()).isEqualTo("pending");


    }
    @Test
    public void testUpdateTask() {
        // Arrange
        Long taskId = 1L;
        TaskDTO updatedTask = new TaskDTO(null, "Updated Task", "Updated Description", "done");
        TaskDTO returnedTask = new TaskDTO(taskId, "Updated Task", "Updated Description", "done");
        when(taskService.updateTask(taskId, updatedTask)).thenReturn(returnedTask);

        // Act
        TaskDTO task = taskController.updateTask(taskId, updatedTask).getBody();

        // Assert
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getStatus()).isEqualTo("done");
    }

    @Test
    public void testDeleteTask_Success() throws Exception {
        // Arrange
        Long taskId = 1L;
        when(taskService.deleteTask(taskId)).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        // Act & Assert
        mockMvc.perform(delete("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Tarea eliminada correctamente"));

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    public void testGetTasksByStatus() {
        // Arrange
        String status = "pending";
        List<TaskDTO> mockTasks = Arrays.asList(
                new TaskDTO(1L, "Task 1", "Description 1", "pending"),
                new TaskDTO(2L, "Task 2", "Description 2", "pending")
        );
        when(taskService.getTasksByStatus(status)).thenReturn(mockTasks);

        // Act
        List<TaskDTO> tasks = taskController.getTasksByStatus(status).getBody();

        // Assert
        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getStatus()).isEqualTo("pending");
    }


}
