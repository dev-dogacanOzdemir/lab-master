    package com.doa.labmasterv10.Service;

    import com.doa.labmasterv10.Config.ResourceNotFoundException;
    import com.doa.labmasterv10.Entities.Report;
    import com.doa.labmasterv10.Repository.ReportRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class ReportService {
        @Autowired
        private ReportRepository reportRepository;

        public List<Report> getAllReports() {
            return reportRepository.findAll();
        }

        public Optional<Report> getReportById(Long id) {
            return reportRepository.findById(id);
        }

        public Report saveReport(Report report) {
            return reportRepository.save(report);
        }

        public Report updateReport(Long id, Report reportDetails) {
            Optional<Report> optionalReport = reportRepository.findById(id);
            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();
                report.setPatientFirstName(reportDetails.getPatientFirstName());
                report.setPatientLastName(reportDetails.getPatientLastName());
                report.setPatientTC(reportDetails.getPatientTC());
                report.setDiagnoseTitle(reportDetails.getDiagnoseTitle());
                report.setDiagnoseDetails(reportDetails.getDiagnoseDetails());
                report.setReportDate(reportDetails.getReportDate());
                report.setLaborant(reportDetails.getLaborant());
                if (reportDetails.getReportImagePath() != null) {
                    report.setReportImagePath(reportDetails.getReportImagePath());
                }
                return reportRepository.save(report);
            } else {
                throw new ResourceNotFoundException("Bu ID'ye sahip bir rapor bulunamadı : " + id);
            }
        }
        public void save(Report report) {
            reportRepository.save(report);
        }
        public void deleteReport(Long id) {
            if (!reportRepository.existsById(id)) {
                throw new ResourceNotFoundException("Bu ID'ye sahip bir rapor bulunamadı : " + id);
            }
            reportRepository.deleteById(id);
        }
        public boolean existsByPatientTC(String patientTC) {
            return reportRepository.existsByPatientTC(patientTC);
        }
    }
