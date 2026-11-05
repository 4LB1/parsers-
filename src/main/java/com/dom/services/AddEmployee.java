package com.dom.services;

import com.dom.model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class AddEmployee {
    public static void main(String[] args) {
        File file = new File("employee.xml");
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(file);

            NodeList list = doc.getElementsByTagName("Employee");
            int nextId = list.getLength() + 1;

            Employee emp = new Employee();
            System.out.println("--- Добавление сотрудника (ID: " + nextId + ") ---");
            System.out.print("Имя: ");
            emp.setName(input.readLine());
            System.out.print("Пол: ");
            emp.setGender(input.readLine());
            System.out.print("Возраст: ");
            emp.setAge(Integer.parseInt(input.readLine()));
            System.out.print("Должность: ");
            emp.setRole(input.readLine());
            System.out.print("Отдел: ");
            emp.setDepartment(input.readLine());
            System.out.print("Зарплата: ");
            emp.setSalary(Double.parseDouble(input.readLine()));
            System.out.print("Валюта (USD/RUB/EUR): ");
            emp.setCurrency(input.readLine());

            Element newEmployeeNode = WriteXML.createEmployeeNode(emp, nextId, doc);
            doc.getDocumentElement().appendChild(newEmployeeNode);

            WriteXML.saveDocument(doc, file);
            System.out.println("Сотрудник успешно добавлен!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}