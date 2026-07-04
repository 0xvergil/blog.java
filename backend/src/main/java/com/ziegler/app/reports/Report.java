package com.ziegler.app.reports;

import java.util.Date;

import com.ziegler.app.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reports")
public class Report {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "reporter_id")
   private User reporter;

   @ManyToOne
   @JoinColumn(name = "reported_id")
   private User reported;

   private String reason;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt = new Date();

   public Report() {
   }

   public Report(User reporter, User reported, String reason) {
      this.reporter = reporter;
      this.reported = reported;
      this.reason = reason;
   }

   public Long getId() {
      return id;
   }

   public User getReporter() {
      return reporter;
   }

   public User getReported() {
      return reported;
   }

   public String getReason() {
      return reason;
   }

   public Date getCreatedAt() {
      return createdAt;
   }
}