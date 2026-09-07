package com.planbookai.backend.frontend;

import com.planbookai.frontend.FrontendAppInfo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FrontendController {

    @GetMapping(value = "/fe-info", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getFrontendPortal() {
        return "<html><head><meta charset='UTF-8'><title>PlanbookAI FE Portal</title>"
                + "<style>body{font-family:Segoe UI,sans-serif;padding:40px;background:#0f172a;color:#f8fafc;}"
                + ".card{background:#1e293b;padding:24px;border-radius:12px;max-width:600px;border:1px solid #334155;}"
                + "a{color:#38bdf8;text-decoration:none;font-weight:600;}"
                + "a:hover{text-decoration:underline;}</style></head><body>"
                + "<div class='card'>"
                + "<h2>🚀 " + FrontendAppInfo.APP_NAME + "</h2>"
                + "<p><strong>Phiên bản:</strong> " + FrontendAppInfo.VERSION + "</p>"
                + "<p><strong>Mô tả:</strong> " + FrontendAppInfo.DESCRIPTION + "</p>"
                + "<hr style='border-color:#334155;'/>"
                + "<p>Trang chủ Giao diện Front-End tĩnh có thể truy cập tại: <a href='/index.html'>/index.html</a></p>"
                + "</div></body></html>";
    }
}
