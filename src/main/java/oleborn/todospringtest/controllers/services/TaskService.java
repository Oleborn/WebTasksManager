package oleborn.todospringtest.controllers.services;

import oleborn.todospringtest.exceptions.InvalidTaskException;
import oleborn.todospringtest.exceptions.TaskNotFoundException;
import oleborn.todospringtest.model.Task;
import oleborn.todospringtest.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Аннотация для обозначения сервиса
public class TaskService {

    private final TaskRepository taskRepository;

    // Внедрение репозитория через конструктор
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Получение всех задач
    public List<Task> findAll() {
        return taskRepository.findAll();  // JpaRepository автоматически находит все записи в таблице
    }

    // Получение задачи по ID
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));  // Исключение, если задача не найдена
    }

    // Сохранение новой задачи
    public Task save(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new InvalidTaskException("Task title cannot be empty");  // Исключение, если заголовок пустой
        }
        return taskRepository.save(task);  // Сохранение задачи в БД
    }

    // Удаление задачи по ID
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);  // Исключение, если задача не существует
        }
        taskRepository.deleteById(id);  // Удаление задачи
    }
}
