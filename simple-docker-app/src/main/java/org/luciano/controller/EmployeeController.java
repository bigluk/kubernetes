package org.luciano.controller;

import org.luciano.entity.Employee;
import org.luciano.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/v1")
public class EmployeeController {
    

    @Inject
    EmployeeService employeeService;


    @POST
    @Path("/new-employee")
    public Response insertNewEmployee (Employee employee) {

        log.info("Received new request to insert {} employee", employee);

        employeeService.insertNewEmployee(employee);

        return Response.ok().build();

    }



    @GET
    @Path("/employee")
    public Response getEmployeeByName (@QueryParam("name") String name) {

        log.info("Received new request to retrieve {} employee", name);

        Employee employee = employeeService.retrieveEmployeeByName(name);

        return Response.ok(employee).status(200).build();

    }



    @DELETE
    @Path("/employee")
    public Response deleteEmployeeById (@QueryParam("id") Long id) {

        log.info("Received new request to delete {} employee", id);

        employeeService.deleteEmployeeById(id);

        return Response.ok().build();

    }



    @PUT
    @Path("/updated-employee")
    public Response updateEmployeeById (@QueryParam("id") Long id, Employee employeeUpdates) {

        log.info("Received new request to update {} employee", employeeUpdates);

        Employee employeeUpdated = employeeService.updateSpeaker(id, employeeUpdates);

        return Response.ok(employeeUpdated).status(200).build(); 

    }


}
