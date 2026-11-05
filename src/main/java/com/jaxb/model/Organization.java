package com.jaxb.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "organization")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization {
    private String orgName;
    private String inn;

    @XmlElementWrapper(name = "departments")
    @XmlElement(name = "department")
    private List<Department> departments;
}