package com.SE3_NachhilfeApp.Student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    /*
    @Bean
    CommandLineRunner commandLineRunner(StudentsRepository repository){
        return  args -> {
            Student s1 = new Student(
                    "mari",
                    "mail@dsf",
                    LocalDate.of(2000, Month.APRIL, 5)
            );

            Student s2 =new Student(
                    "alex",
                    "alex@dsf",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            repository.saveAll(List.of(s1, s2));
        };

        }
     */

}
