package es.enterprise.task.manager.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks") // Mapea a la tabla "tasks"
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera IDs automáticamente
    private Long id;

    private String title;
    private String description;
    private String status;
}