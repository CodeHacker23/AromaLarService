package ru.project.aromalarservice;


import org.junit.jupiter.api.Test;

import java.io.File;

public class TestFileSave {


    @Test
    public void printPathFile(){
        String originalFileName = "dif7.jpg";
        File destinationFile = new File("img/" + originalFileName);

        System.out.println(destinationFile.toString());

    }


}
