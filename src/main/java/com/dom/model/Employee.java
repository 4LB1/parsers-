package com.dom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String role;
    // Новые поля
    private String department;
    private double salary;
    private String currency; // атрибут для salary
}