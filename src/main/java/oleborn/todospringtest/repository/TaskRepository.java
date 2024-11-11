package oleborn.todospringtest.repository;

import oleborn.todospringtest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Интерфейс для доступа к данным, автоматический доступ к таблице Task
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository предоставляет базовые CRUD операции
}
