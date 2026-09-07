package com.planbookai.frontend;

/**
 * Thông tin cấu hình và phiên bản của giao diện Front-End PlanbookAI.
 */
public class FrontendAppInfo {
    public static final String APP_NAME = "PlanbookAI Web Client";
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "Giao diện quản lý Giáo án, Đề thi và Học sinh dành cho Giáo viên";

    public static String getAppDetails() {
        return String.format("%s (v%s) - %s", APP_NAME, VERSION, DESCRIPTION);
    }
}
