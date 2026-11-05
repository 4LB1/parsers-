package com.dom.services;

import com.dom.model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadXML {
    public static void main(String[] args) {
        File file = new File("employee.xml");
        if (!file.exists()) {
            System.out.println("Файл employee.xml еще не создан! Сначала запустите WriteXML.");
            return;
        }

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(file);
            doc.getDocumentElement().normalize();

            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());

            List<Employee> employees = parseEmployees(doc);
            employees.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseEmployees(Document doc) {
        List<Employee> list = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("Employee");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                list.add(parseEmployeeElement((Element) node));
            }
        }
        return list;
    }

    public static Employee parseEmployeeElement(Element element) {
        Employee emp = new Employee();
        emp.setId(Integer.parseInt(element.getAttribute("id")));
        emp.setName(getTagValue(element, "name"));
        emp.setGender(getTagValue(element, "gender"));
        emp.setAge(Integer.parseInt(getTagValue(element, "age")));
        emp.setRole(getTagValue(element, "role"));
        emp.setDepartment(getTagValue(element, "department"));

        Element salaryElement = (Element) element.getElementsByTagName("salary").item(0);
        if (salaryElement != null) {
            emp.setSalary(Double.parseDouble(salaryElement.getTextContent()));
            emp.setCurrency(salaryElement.getAttribute("currency"));
        }
        return emp;
    }

    private static String getTagValue(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0 && list.item(0) != null) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}