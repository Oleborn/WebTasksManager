package oleborn.todospringtest.controllers;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import oleborn.todospringtest.services.TaskService;
import oleborn.todospringtest.model.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller  // Контроллер для обработки HTTP-запросов
@RequestMapping("/tasks")  // Базовый URL для задач
public class TaskController {

    @Resource
    private TaskService taskService;

    @GetMapping  // Обработка GET-запроса для отображения всех задач
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());  // Добавление всех задач в модель
        return "tasks/list";  // Возвращаем имя шаблона для отображения задач
    }

    @GetMapping("/create")  // Страница для создания новой задачи
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());  // Создаем пустую задачу
        return "tasks/create";  // Возвращаем форму для создания задачи
    }

    @PostMapping  // Обработка POST-запроса для сохранения задачи
    public String saveTask(@Valid @ModelAttribute Task task, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {  // Проверка на ошибки валидации
            return "tasks/error";  // Если есть ошибки, возвращаем форму создания задачи
        }

        taskService.save(task);  // Сохраняем задачу в БД
        return "redirect:/tasks";  // Перенаправляем на список задач
    }

    @GetMapping("/edit/{id}")  // Страница для редактирования задачи
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);  // Получаем задачу по ID
        model.addAttribute("task", task);  // Добавляем задачу в модель
        return "tasks/edit";  // Возвращаем форму редактирования задачи
    }

    @PostMapping("/edit/{id}")  // Обработка POST-запроса для обновления задачи
    public String updateTask(@Valid @ModelAttribute Task task, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {  // Если есть ошибки валидации
            return "tasks/error";  // Возвращаем форму редактирования задачи
        }

        task.setId(id);  // Устанавливаем ID для обновления задачи
        taskService.save(task);  // Обновляем задачу в БД
        return "redirect:/tasks";  // Перенаправляем на список задач
    }

    @GetMapping("/delete/{id}")  // Удаление задачи
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);  // Удаляем задачу по ID
        return "redirect:/tasks";  // Перенаправляем на список задач
    }
}
