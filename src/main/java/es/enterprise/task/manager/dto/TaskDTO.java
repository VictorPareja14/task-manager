package es.enterprise.task.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String title;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String description;

    @NotBlank(message = "El estado no puede estar vacío")
    private String status; // "pending" o "done"

}
