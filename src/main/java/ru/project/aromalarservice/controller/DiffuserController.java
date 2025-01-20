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
                          Model model
                          ) {




        try {
            // Убедимся, что директория существует
            log.info("");
            File dirImg = new File("img/");
            if (!dirImg.exists()) {
                dirImg.mkdirs(); // Создаем директорию, если её нет
                log.info("Создаем директорию");
            }
            log.info("диреткория уже была");


            // Получаем оригинальное имя файла
            String originalFileName = file.getOriginalFilename();
            log.info("name file " + originalFileName);

            // Создаем путь для сохранения файла
            File destinationFile = new File("img/" + originalFileName);
            log.info("path for file save "  +destinationFile.toString());


            Files.createFile(destinationFile.toPath());
            log.info("save img to dir");

            Files.write(destinationFile.toPath(),file.getBytes());

            Diffuser diffuser=new Diffuser();
            diffuser.setName(name);
            diffuser.setPrice(price);
            diffuser.setDescription(description);
            diffuser.setUrl(destinationFile.toString());
            log.info("create object " + diffuser );

            diffuserRepository.save(diffuser);
            log.info("save object to DB");

            // Возвращаем имя файла
            model.addAttribute("success","Товар добавлен");
            return "add_diffuser";

        } catch (IOException e) {
            model.addAttribute("error","Товар не добавлен");
            return "add_diffuser";
        }

    }

}
