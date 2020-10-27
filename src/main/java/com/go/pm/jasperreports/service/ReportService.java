package com.go.pm.jasperreports.service;

import com.go.pm.jasperreports.model.Employees;
import com.go.pm.jasperreports.repository.EmployeesRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Value("${jasperreports.disco.raiz}")
    private String raiz;

    @Value("${jasperreports.disco.diretorio-relatorios}")
    private String diretorioRelatorios;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = raiz + diretorioRelatorios;
        List<Employees> employees = employeesRepository.findAll();
        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CreateBy", "Brenno Pimenta");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/employees.pdf" );
            path = path + "/employees.html";
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/employees.pdf");
            path = path + "/employees.pdf";
        }

        return "report genarete in patch:" + path;
    }
}
