package com.planbookai.backend.presentation.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // ==================== ADMIN ROUTES ====================
    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("currentPage", "users");
        model.addAttribute("pageTitle", "Quản lý Tài khoản (User Lifecycle)");
        model.addAttribute("userName", "Admin PlanbookAI");
        return "admin/users";
    }

    @GetMapping("/admin/settings")
    public String adminSettings(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("currentPage", "settings");
        model.addAttribute("pageTitle", "Cấu hình Hệ thống");
        model.addAttribute("userName", "Admin PlanbookAI");
        return "admin/settings";
    }

    @GetMapping("/admin/templates")
    public String adminTemplates(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("currentPage", "templates");
        model.addAttribute("pageTitle", "Khung chương trình chuẩn (Templates)");
        model.addAttribute("userName", "Admin PlanbookAI");
        return "admin/templates";
    }

    @GetMapping("/admin/revenue")
    public String adminRevenue(Model model) {
        model.addAttribute("role", "ADMIN");
        model.addAttribute("currentPage", "revenue");
        model.addAttribute("pageTitle", "Báo cáo Doanh thu & Tăng trưởng");
        model.addAttribute("userName", "Admin PlanbookAI");
        return "admin/revenue";
    }

    // ==================== MANAGER ROUTES ====================
    @GetMapping("/manager/packages")
    public String managerPackages(Model model) {
        model.addAttribute("role", "MANAGER");
        model.addAttribute("currentPage", "packages");
        model.addAttribute("pageTitle", "Quản lý Gói dịch vụ (SaaS) & Đơn hàng");
        model.addAttribute("userName", "Quản Lý Vận Hành");
        return "manager/packages";
    }

    @GetMapping("/manager/review")
    public String managerReview(Model model) {
        model.addAttribute("role", "MANAGER");
        model.addAttribute("currentPage", "review");
        model.addAttribute("pageTitle", "Phê duyệt Nội dung (Review Queue)");
        model.addAttribute("userName", "Quản Lý Vận Hành");
        return "manager/review";
    }

    // ==================== STAFF ROUTES ====================
    @GetMapping("/staff/questions")
    public String staffQuestions(Model model) {
        model.addAttribute("role", "STAFF");
        model.addAttribute("currentPage", "questions");
        model.addAttribute("pageTitle", "Ngân hàng Câu hỏi Hóa học (KaTeX)");
        model.addAttribute("userName", "Nhân Viên Biên Soạn");
        return "staff/questions";
    }

    @GetMapping("/staff/prompts")
    public String staffPrompts(Model model) {
        model.addAttribute("role", "STAFF");
        model.addAttribute("currentPage", "prompts");
        model.addAttribute("pageTitle", "Quản lý Mẫu Prompt AI");
        model.addAttribute("userName", "Nhân Viên Biên Soạn");
        return "staff/prompts";
    }

    @GetMapping("/staff/lesson-drafts")
    public String staffLessonDrafts(Model model) {
        model.addAttribute("role", "STAFF");
        model.addAttribute("currentPage", "lesson-drafts");
        model.addAttribute("pageTitle", "Soạn Giáo án Mẫu (Data Builders)");
        model.addAttribute("userName", "Nhân Viên Biên Soạn");
        return "staff/lesson-drafts";
    }

    // ==================== TEACHER ROUTES ====================
    @GetMapping("/teacher/dashboard")
    public String teacherDashboard(Model model) {
        model.addAttribute("role", "TEACHER");
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("pageTitle", "Bảng điều khiển & Phân tích Hiệu suất");
        model.addAttribute("userName", "Thầy Nguyễn Văn A");
        return "teacher/dashboard";
    }

    @GetMapping("/teacher/classes")
    public String teacherClasses(Model model) {
        model.addAttribute("role", "TEACHER");
        model.addAttribute("currentPage", "classes");
        model.addAttribute("pageTitle", "Quản lý Lớp học & Học sinh");
        model.addAttribute("userName", "Thầy Nguyễn Văn A");
        return "teacher/classes";
    }

    @GetMapping("/teacher/workspace")
    public String teacherWorkspace(Model model) {
        model.addAttribute("role", "TEACHER");
        model.addAttribute("currentPage", "workspace");
        model.addAttribute("pageTitle", "Soạn Giáo án Tích hợp Gemini AI (Three-Pane)");
        model.addAttribute("userName", "Thầy Nguyễn Văn A");
        return "teacher/workspace";
    }

    @GetMapping("/teacher/exams")
    public String teacherExams(Model model) {
        model.addAttribute("role", "TEACHER");
        model.addAttribute("currentPage", "exams");
        model.addAttribute("pageTitle", "Trình Tạo Đề Thi Thông Minh (Chuẩn 10.0)");
        model.addAttribute("userName", "Thầy Nguyễn Văn A");
        return "teacher/exams";
    }

    @GetMapping("/teacher/omr")
    public String teacherOmr(Model model) {
        model.addAttribute("role", "TEACHER");
        model.addAttribute("currentPage", "omr");
        model.addAttribute("pageTitle", "Chấm thi OMR & Nhận diện OpenCV");
        model.addAttribute("userName", "Thầy Nguyễn Văn A");
        return "teacher/omr";
    }
}
