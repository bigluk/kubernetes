package org.luciano.service;

import org.apache.commons.lang3.StringUtils;
import org.luciano.entity.Employee;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class EmployeeService {
    

    @Transactional
    public void insertNewEmployee (Employee employee) {

        employee.persist();

    }



    public Employee retrieveEmployeeByName (String name) {

        return Employee.find("name", name).firstResult();

    }



    @Transactional
    public void deleteEmployeeById (Long id) {

        Employee.deleteById(id);

    }



    @Transactional
    public Employee updateSpeaker (Long id, Employee employeeUpdates) {

        Employee employeePersisted = Employee.findById(id);

        applyUpdatesToEmployeePersisted(employeePersisted, employeeUpdates);

        Employee.persist(employeePersisted);

        return employeePersisted;

    }



    private void applyUpdatesToEmployeePersisted(Employee employeePersisted, Employee employeeUpdates) {

        if (StringUtils.isNotBlank(employeeUpdates.getName()))
            employeePersisted.setName(employeeUpdates.getName());

        if (StringUtils.isNotBlank(employeeUpdates.getLastName()))
            employeePersisted.setLastName(employeeUpdates.getLastName());

        if (StringUtils.isNotBlank(employeeUpdates.getDepartment()))
            employeePersisted.setDepartment(employeeUpdates.getDepartment());

    }


}
