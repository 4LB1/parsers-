package com.dom.services;

import com.dom.model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class WriteXML {
    public static void main(String[] args) {
        File file = new File("employee.xml");

        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("Employees");
            document.appendChild(root);

            System.out.print("Сколько сотрудников добавить? ");
            int count = Integer.parseInt(input.readLine());

            for (int i = 1; i <= count; i++) {
                System.out.println("--- Сотрудник #" + i + " ---");
                Employee emp = new Employee();

                System.out.print("Имя: ");
                emp.setName(input.readLine());

                System.out.print("Пол (male/female): ");
                emp.setGender(input.readLine());

                System.out.print("Возраст: ");
                emp.setAge(Integer.parseInt(input.readLine()));

                System.out.print("Должность (role): ");
                emp.setRole(input.readLine());

                System.out.print("Отдел: ");
                emp.setDepartment(input.readLine());

                System.out.print("Зарплата: ");
                emp.setSalary(Double.parseDouble(input.readLine()));

                System.out.print("Валюта зарплаты (USD/RUB/EUR): ");
                emp.setCurrency(input.readLine());

                Element employeeElement = createEmployeeNode(emp, i, document);
                root.appendChild(employeeElement);
            }

            saveDocument(document, file);
            System.out.println("Файл employee.xml успешно создан!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Element createEmployeeNode(Employee emp, int id, Document doc) {
        Element empElement = doc.createElement("Employee");
        empElement.setAttribute("id", String.valueOf(id));

        empElement.appendChild(createPropertyNode("name", emp.getName(), doc));
        empElement.appendChild(createPropertyNode("gender", emp.getGender(), doc));
        empElement.appendChild(createPropertyNode("age", String.valueOf(emp.getAge()), doc));
        empElement.appendChild(createPropertyNode("role", emp.getRole(), doc));
        empElement.appendChild(createPropertyNode("department", emp.getDepartment(), doc));

        Element salaryElement = doc.createElement("salary");
        salaryElement.setAttribute("currency", emp.getCurrency() != null ? emp.getCurrency() : "USD");
        salaryElement.setTextContent(String.valueOf(emp.getSalary()));
        empElement.appendChild(salaryElement);

        return empElement;
    }

    private static Element createPropertyNode(String tag, String value, Document doc) {
        Element el = doc.createElement(tag);
        el.setTextContent(value);
        return el;
    }

    public static void saveDocument(Document doc, File file) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
}