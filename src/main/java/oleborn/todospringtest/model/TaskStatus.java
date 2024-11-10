package oleborn.todospringtest.model;

import lombok.Getter;

@Getter
public enum TaskStatus {

    RESEARCHING("Изучается наверно, хотя кому вы нахер врете)"),
    TO_PROCESSING("В работе. Там даже что-то делается наверно"),
    CANCELLED("Отменено. Ибо нехер."),
    COMPLETED("Невероятно, но задача закончена!");

    private String value;

    TaskStatus(String value) {
        this.value = value;
    }
}
