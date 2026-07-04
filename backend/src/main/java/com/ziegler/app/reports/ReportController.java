package com.ziegler.app.reports;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

   private final ReportService reportService;

   public ReportController(ReportService reportService) {
      this.reportService = reportService;
   }

   @PostMapping("/{userId}")
   public ResponseEntity<String> createReport(
         @PathVariable Long userId,
         @RequestBody ReportRequest request,
         Authentication authentication) {
      reportService.createReport(authentication.getName(), userId, request.getReason());
      return ResponseEntity.ok("Report submitted");
   }

   @GetMapping
   public ResponseEntity<List<Report>> getAllReports(Authentication authentication) {
      // TODO: restrict to admin only — we'll add this when we build the admin panel
      return ResponseEntity.ok(reportService.getAllReports());
   }
}