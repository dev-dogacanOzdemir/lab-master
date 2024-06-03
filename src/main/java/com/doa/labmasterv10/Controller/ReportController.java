package com.doa.labmasterv10.Controller;

import com.doa.labmasterv10.Config.ResourceNotFoundException;
import com.doa.labmasterv10.Entities.Laborant;
import com.doa.labmasterv10.Entities.Report;
import com.doa.labmasterv10.Service.LaborantService;
import com.doa.labmasterv10.Service.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private LaborantService laborantService;

    @GetMapping
    public ModelAndView getAllReports(){
        List<Report> reports = reportService.getAllReports();
        ModelAndView mav = new ModelAndView("reports");
        mav.addObject("reports",reports);
        return mav;
    }
    @GetMapping("/{id}")
    public ModelAndView getReportById(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("reportDetails");
        return reportService.getReportById(id).map(report -> {
            mav.addObject("report", report);
            return mav;
        }).orElseGet(() -> {
            mav.setViewName("reportNotFound");
            return mav;
        });
    }
    @GetMapping("/createReport")
    public ModelAndView showCreateForm(){
        ModelAndView mav = new ModelAndView("createReport");
        mav.addObject("report",new Report());
        mav.addObject("laborants", laborantService.getAllLaborants());
        return mav;
    }

    @PostMapping
    public String createReport(@ModelAttribute Report report){
        reportService.saveReport(report);
        return "redirect:/reports";
    }
    @GetMapping("/editReport/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("editReport");
        List<Laborant> laborants = laborantService.getAllLaborants();
        mav.addObject("laborants", laborants);

        return reportService.getReportById(id).map(report -> {
            mav.addObject("report", report);
            return mav;
        }).orElseGet(() -> {
            mav.setViewName("reportNotFound");
            return mav;
        });

    }
    @PostMapping("/saveReport")
    public String saveReport(@ModelAttribute @Valid Report report, BindingResult bindingResult, @RequestParam("reportImage") MultipartFile file,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("laborants", laborantService.getAllLaborants());
            return "createReport";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String uploadDir = "src/main/resources/uploads/";
            try {
                Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);
                report.setReportImagePath(uploadDir + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            reportService.save(report);
            return "redirect:/reports";

    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Report report = reportService.getReportById(id).orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + id));
        Path path = Paths.get(report.getReportImagePath());
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName().toString() + "\"")
                .body(resource);
    }

    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable Long id, @ModelAttribute @Valid Report reportDetails, BindingResult bindingResult, @RequestParam("reportImage") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("laborants", laborantService.getAllLaborants());
            return "editReport";
        }

        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String uploadDir = "src/main/resources/uploads/";
                try {
                    Files.copy(file.getInputStream(), Paths.get(uploadDir + fileName), StandardCopyOption.REPLACE_EXISTING);
                    reportDetails.setReportImagePath(uploadDir + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            reportService.updateReport(id, reportDetails);
            return "redirect:/reports";
        } catch (ResourceNotFoundException e) {
            return "redirect:/reports/notFound";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id){
        reportService.deleteReport(id);
        return "redirect:/reports";
    }
}
