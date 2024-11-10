package oleborn.todospringtest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // Аннотация, чтобы Hibernate знал, что это сущность, связанную с таблицей в базе данных
@Data
public class Task {

    @Id  // Это поле будет идентификатором (ключом) для задачи
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоматическая генерация значений ID
    private Long id;

    @NotNull(message = "Title cannot be null")  // Валидация: название задачи не может быть пустым
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")  // Валидация длины заголовка
    private String title;

    @Size(max = 200, message = "Description cannot exceed 200 characters")  // Валидация длины описания задачи
    private String description;

    @NotNull(message = "Status cannot be null")  // Статус задачи не может быть пустым
    @Enumerated(EnumType.STRING)  // Храним значение как строку в базе данных
    private TaskStatus status; // Например: "IN_PROGRESS", "COMPLETED"

}
