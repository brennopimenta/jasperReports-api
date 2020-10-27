package com.go.pm.jasperreports.controller;

import com.go.pm.jasperreports.model.Employees;
import com.go.pm.jasperreports.repository.EmployeesRepository;
import com.go.pm.jasperreports.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping
    public List<Employees> getEmployees() {
        return employeesRepository.findAll();
    }

    // Não foi criado a camada de serviço para agilizar o entendimento, pois o intuito eh mostrar o relatorio!
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employees criar(@Valid @RequestBody Employees employees) throws Exception {
        Employees employeesSalvo = employeesRepository.save(employees);
        return employees;
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.exportReport(format);
    }

}
