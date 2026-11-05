package com.jaxb.test;

import com.jaxb.model.Department;
import com.jaxb.model.Employee;
import com.jaxb.model.Organization;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestExample {
    private static final String XML_FILE = "organization-info.xml";

    public static void main(String[] args) {
        // ================= 1. ПОДГОТОВКА ТЕСТОВЫХ ДАННЫХ =================
        // Сотрудники первого отдела
        List<Employee> itEmployees = new ArrayList<>();
        itEmployees.add(new Employee("E01", "Tom", null));
        itEmployees.add(new Employee("E02", "Mary", "E01"));

        // Сотрудники второго отдела
        List<Employee> hrEmployees = new ArrayList<>();
        hrEmployees.add(new Employee("E03", "Anna", null));

        // Отделы
        Department itDept = new Department("D01", "IT", "New York");
        itDept.setEmployees(itEmployees);

        Department hrDept = new Department("D02", "HR", "London");
        hrDept.setEmployees(hrEmployees);

        List<Department> deptList = new ArrayList<>();
        deptList.add(itDept);
        deptList.add(hrDept);

        // Организация (корень)
        Organization org = new Organization("TechCorp Solutions", "7701234567", deptList);

        try {
            // Инициализируем JAXBContext для корневого класса Organization
            JAXBContext context = JAXBContext.newInstance(Organization.class);

            // ================= 2. МАРШАЛИНГ (Java -> XML) =================
            System.out.println("====== НАЧАЛО МАРШАЛИНГА ======");
            Marshaller marshaller = context.createMarshaller();
            // Делаем красивое форматирование с отступами:
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Выводим в консоль
            marshaller.marshal(org, System.out);

            // Записываем в файл
            File outFile = new File(XML_FILE);
            marshaller.marshal(org, outFile);
            System.out.println("\nXML успешно сохранен в файл: " + outFile.getAbsolutePath());

            // ================= 3. ДЕМАРШАЛИНГ (XML -> Java) =================
            System.out.println("\n====== НАЧАЛО ДЕМАРШАЛИНГА ======");
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Читаем из файла обратно в объект Java
            try (FileReader reader = new FileReader(outFile)) {
                Organization orgFromFile = (Organization) unmarshaller.unmarshal(reader);

                System.out.println("Организация прочитана из файла:");
                System.out.println("Название: " + orgFromFile.getOrgName() + ", ИНН: " + orgFromFile.getInn());
                System.out.println("Отделы:");

                for (Department dept : orgFromFile.getDepartments()) {
                    System.out.println("  └─ Отдел: " + dept.getDepartmentName() + " (" + dept.getLocation() + ")");
                    if (dept.getEmployees() != null) {
                        for (Employee emp : dept.getEmployees()) {
                            System.out.println("       └─ Сотрудник: " + emp.getEmployeeName() + " (№" + emp.getEmployeeNumber() + ")");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}