package Project1.com.LibraryManagement.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryController {

    // Trang chính thư viện
    @GetMapping({"/library", "/library/dashboard"})
    public String libraryDashboard() {
        // trả về templates/library/dashboard.html
        return "library/dashboard";
    }

    // Tùy chọn: map "/" vào thư viện (nếu bạn muốn)
    @GetMapping("/")
    public String home() {
        return "library/dashboard";
    }
    // Độc giả
    @GetMapping("library/readers")
    public String reader() {
        return "library/readers";
    }

    // Sách
    @GetMapping("library/books")
    public String books() {
        return "library/books";
    }

    // Lưu thông / Mượn - Trả
    @GetMapping("library/circulation")
    public String circulation() {
        return "library/circulation";
    }

    // Thu phí phạt
    @GetMapping("library/fees")
    public String fees() {
        return "library/fees";
    }

    // Reports
    @GetMapping("library/fee-reports")
    public String feeReports() {
        return "library/fee-reports";
    }

    @GetMapping("library/member-reports")
    public String memberReports() {
        return "library/member-reports";
    }

    @GetMapping("library/circulation-reports")
    public String circulationReports() {
        return "library/circulation-reports";
    }

    @GetMapping("library/overdue-reports")
    public String overdueReports() {
        return "library/overdue-reports";
    }
}
