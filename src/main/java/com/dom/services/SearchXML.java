package com.dom.services;

import com.dom.model.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class SearchXML {
    public static void main(String[] args) {
        File file = new File("employee.xml");
        try (Scanner scanner = new Scanner(System.in)) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(file);

            System.out.print("Введите ID сотрудника для поиска: ");
            int searchId = scanner.nextInt();

            Employee found = findById(doc, searchId);
            if (found != null) {
                System.out.println("Сотрудник найден: " + found);
            } else {
                System.out.println("Сотрудник с ID " + searchId + " не найден.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Employee findById(Document doc, int id) {
        NodeList list = doc.getElementsByTagName("Employee");
        for (int i = 0; i < list.getLength(); i++) {
            Element el = (Element) list.item(i);
            if (el.getAttribute("id").equals(String.valueOf(id))) {
                return ReadXML.parseEmployeeElement(el);
            }
        }
        return null;
    }
}