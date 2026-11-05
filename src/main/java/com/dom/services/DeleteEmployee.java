package com.dom.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class DeleteEmployee {
    public static void main(String[] args) {
        File file = new File("employee.xml");
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(file);

            System.out.print("Введите ID сотрудника для удаления: ");
            int id = Integer.parseInt(input.readLine());

            boolean deleted = deleteById(doc, id);
            if (deleted) {
                WriteXML.saveDocument(doc, file);
                System.out.println("Сотрудник с ID " + id + " успешно удален.");
            } else {
                System.out.println("Сотрудник с ID " + id + " не найден.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteById(Document doc, int id) {
        NodeList list = doc.getElementsByTagName("Employee");
        for (int i = 0; i < list.getLength(); i++) {
            Element el = (Element) list.item(i);
            if (el.getAttribute("id").equals(String.valueOf(id))) {
                // Удаляем пробельный узел перед элементом (для красоты форматирования)
                Node prev = el.getPreviousSibling();
                if (prev != null && prev.getNodeType() == Node.TEXT_NODE && prev.getNodeValue().trim().isEmpty()) {
                    doc.getDocumentElement().removeChild(prev);
                }
                doc.getDocumentElement().removeChild(el);
                return true;
            }
        }
        return false;
    }
}