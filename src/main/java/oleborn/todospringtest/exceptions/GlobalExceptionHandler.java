package oleborn.todospringtest.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice  // Глобальная обработка ошибок
public class GlobalExceptionHandler {

    // Обработка исключений, если задача не найдена
    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());  // Добавляем сообщение об ошибке
        return "redirect:/tasks";  // Перенаправляем на страницу списка задач
    }

    // Обработка исключений для некорректных задач
    @ExceptionHandler(InvalidTaskException.class)
    public String handleInvalidTask(InvalidTaskException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());  // Добавляем сообщение об ошибке
        return "tasks/create";  // Перенаправляем на страницу создания задачи
    }

    // Обработка любых других ошибок
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());  // Сообщение об ошибке
        return "error";  // Страница ошибки
    }
}
