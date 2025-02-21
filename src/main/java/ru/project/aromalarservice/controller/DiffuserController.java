package ru.project.aromalarservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.project.aromalarservice.model.entity.Diffuser;
import ru.project.aromalarservice.repositiria.DiffuserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/diffusers")
@AllArgsConstructor
public class DiffuserController {

    private DiffuserRepository diffuserRepository;

    @GetMapping("/add")
    public String addGet() {
        return "add_diffuser";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("price") Integer price,
                          @RequestParam("image") MultipartFile file,
                          Model model) {
        try {
            // 1. Проверка на пустой файл
            if (file.isEmpty()) {
                model.addAttribute("error", "Файл изображения обязателен");
                return "add_diffuser";
            }

            // 2. Проверка имени файла
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                model.addAttribute("error", "Неверное имя файла");
                return "add_diffuser";
            }

            // 3. Правильный путь к директории
            String uploadDir = "src/main/resources/static/img/";
            File dirImg = new File(uploadDir);
            if (!dirImg.exists() && !dirImg.mkdirs()) {
                log.error("Не удалось создать директорию: {}", uploadDir);
                model.addAttribute("error", "Ошибка сервера при создании директории");
                return "add_diffuser";
            }

            // 4. Генерация уникального имени файла
            String fileName = UUID.randomUUID() + "_" + originalFileName.replace(" ", "_");
            Path filePath = Paths.get(uploadDir + fileName);

            // 5. Сохранение файла (автоматически создает файл если его нет)
            Files.write(filePath, file.getBytes());
            log.info("Файл сохранен: {}", filePath);

            // 6. Формирование относительного пути для web
            String webPath = "/static/img/" + fileName.replace("\\", "/"); // Убедимся в правильных слешах

            // 7. Создание и сохранение объекта
            Diffuser diffuser = new Diffuser();
            diffuser.setName(name);
            diffuser.setPrice(price);
            diffuser.setDescription(description);
            diffuser.setUrl(webPath); // Сохраняем web-путь

            diffuserRepository.save(diffuser);
            log.info("Объект сохранен в БД: {}", diffuser);

            model.addAttribute("success", "Товар успешно добавлен");
            return "add_diffuser";

        } catch (IOException e) {
            log.error("Ошибка при сохранении файла: {}", e.getMessage());
            model.addAttribute("error", "Ошибка при загрузке изображения: " + e.getMessage());
            return "add_diffuser";
        } catch (Exception e) {
            log.error("Общая ошибка: {}", e.getMessage());
            model.addAttribute("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return "add_diffuser";
        }
    }
}
