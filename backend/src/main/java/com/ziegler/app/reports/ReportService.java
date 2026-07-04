package com.ziegler.app.reports;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ziegler.app.user.User;
import com.ziegler.app.user.UserRepository;

@Service
public class ReportService {

   private final ReportRepository reportRepository;
   private final UserRepository userRepository;

   public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
      this.reportRepository = reportRepository;
      this.userRepository = userRepository;
   }

   public Report createReport(String reporterUsername, Long reportedId, String reason) {
      User reporter = userRepository.findByUsername(reporterUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));
      User reported = userRepository.findById(reportedId)
            .orElseThrow(() -> new RuntimeException("User not found"));

      if (reporter.getId().equals(reportedId)) {
         throw new RuntimeException("Cannot report yourself");
      }

      return reportRepository.save(new Report(reporter, reported, reason));
   }

   public List<Report> getAllReports() {
      return reportRepository.findAllByOrderByCreatedAtDesc();
   }
}