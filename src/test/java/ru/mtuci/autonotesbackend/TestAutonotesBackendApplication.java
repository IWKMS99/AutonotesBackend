package ru.mtuci.autonotesbackend;

import org.springframework.boot.SpringApplication;

public class TestAutonotesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.from(AutonotesBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
