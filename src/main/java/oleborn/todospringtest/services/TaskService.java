package oleborn.todospringtest.services;

import jakarta.annotation.Resource;
import oleborn.todospringtest.annotation.MeasureExecutionTime;
import oleborn.todospringtest.exceptions.InvalidTaskException;
import oleborn.todospringtest.exceptions.TaskNotFoundException;
import oleborn.todospringtest.model.Task;
import oleborn.todospringtest.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Аннотация для обозначения сервиса
public class TaskService {

    @Resource
    private TaskRepository taskRepository;

    // Получение всех задач
    @MeasureExecutionTime
    public List<Task> findAll() {
        return taskRepository.findAll();  // JpaRepository автоматически находит все записи в таблице
    }

    // Получение задачи по ID
    @MeasureExecutionTime
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));  // Исключение, если задача не найдена
    }

    // Сохранение новой задачи
    @MeasureExecutionTime
    public Task save(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new InvalidTaskException("Task title cannot be empty");  // Исключение, если заголовок пустой
        }
        return taskRepository.save(task);  // Сохранение задачи в БД
    }

    // Удаление задачи по ID
    @MeasureExecutionTime
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);  // Исключение, если задача не существует
        }
        taskRepository.deleteById(id);  // Удаление задачи
    }
}
