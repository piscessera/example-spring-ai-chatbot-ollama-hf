package com.example.lab.demo;

import org.springframework.boot.SpringApplication;

public class TestApplication {
  public static void main(String[] args) {
    SpringApplication.from(DemoApplication::main)
      .with(TestcontainersConfiguration.class)
      .run(args);
  }
}
