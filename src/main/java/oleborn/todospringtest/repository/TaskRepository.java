package oleborn.todospringtest.repository;

import oleborn.todospringtest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


// Интерфейс для доступа к данным, автоматический доступ к таблице Task
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository предоставляет базовые CRUD операции
}
