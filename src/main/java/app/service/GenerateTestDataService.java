package app.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GenerateTestDataService {

    public List<Map<String, Object>> generateAccountPermissionData() {
        SecureRandom secureRandom = new SecureRandom();

        final String[] unitNameAry = {"秘書室", "人事室", "主計室", "企劃處", "技術處"};
        final String[] roleAry = {"系統管理者", "系統C_業務承辦", "系統C_一般使用者", "系統A_系統管理者", "系統A_審查人員", "系統B_一般使用者", "系統B_機關使用者"};

        List<Map<String, Object>> testData = new ArrayList<>();
        for (int i = 0; i < unitNameAry.length; i++) {
            String unitName = unitNameAry[i];
            double randomLength = Math.floor(secureRandom.nextDouble() * 4) + 1;

            for (int j = 0; j < randomLength; j++) {
                // 每次迴圈都創建新的 Map 實例
                Map<String, Object> unitData = new HashMap<>();
                unitData.put("UNIT", unitName);

                unitData.put("NAME", "測試人員-" + (i + 1) + j);
                unitData.put("ID", "testUser-" + (i + 1) + j);

                // 隨機生成 ROLE 清單
                StringBuilder roleStr = new StringBuilder();
                int randomRoleCount = (int) Math.floor(secureRandom.nextDouble() * roleAry.length) + 1;
                Set<String> selectedRoles = new HashSet<>();

                while (selectedRoles.size() < randomRoleCount) {
                    int randomIndex = (int) Math.floor(secureRandom.nextDouble() * roleAry.length);
                    selectedRoles.add(roleAry[randomIndex]);
                }

                for (String role : selectedRoles) {
                    roleStr.append("□").append(role).append(",\n");
                }
                unitData.put("ROLE", roleStr.substring(0, roleStr.lastIndexOf(",")));

                testData.add(unitData);
            }
        }

        return testData;
    }

    public Map<String, Object> generateProjectInformationData() {

        final String[] prjKind = {"財務採購", "資訊採購", "小額採購", "獎補捐助", "維運人力", "其他" };

        SecureRandom secureRandom = new SecureRandom();

        // 壹、計畫基本資料
        Map<String, Object> planInfo = new HashMap<>();
        planInfo.put("PLNNO", "1080000033");
        planInfo.put("YEAR", "113");
        planInfo.put("PLNNAME", "活動計畫名稱(測試)");
        planInfo.put("BDATE", "2024-07-01");
        planInfo.put("EDATE", "2025-02-01");
        planInfo.put("PLNAMT", "100");
        planInfo.put("PLN_MAN", "計畫申請人(測試)");
        planInfo.put("PLN_TEL", "01-23456789");
        planInfo.put("PLN_E_MAIL", "test-mail@mail.test.com");

        // 貳、工作基本資料
        List<Map<String, Object>> jobInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> job = new HashMap<>();
            job.put("SEQ", i + 1);
            job.put("PRJNO", "10800000330000" + (i+1));
            job.put("NAME", "測試工作名稱0" + (i+1));
            job.put("TOTAMT", (int) Math.floor(secureRandom.nextDouble() * 10) + 1);
            job.put("BDATE", "2024-07-01");
            job.put("EDATE", "2024-07-0" + (i+2));
            job.put("PRJKIND",  prjKind[(int) Math.floor(secureRandom.nextDouble() * prjKind.length)]);
            job.put("CONTAIN", "工作說明");
            jobInfoList.add(job);
        }

        // 參、各月累積預定執行經費
        List<Map<String, Object>> pexpInfoList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> pexp = new HashMap<>();
            pexp.put("SEQ", i + 1);
            pexp.put("SYRMNTH", "2024-0" + (7 + i) + "-01");
            pexp.put("PEXP", (int) Math.floor(secureRandom.nextDouble() * 10) + 1);
            pexp.put("MEMO", "說明");

            pexpInfoList.add(pexp);
        }

        // 肆、上傳相關照片
        List<Map<String, Object>> photoInfoList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            try {
                // 使用 ClassPathResource 來讀取資源
                ClassPathResource resource = new ClassPathResource("template-pic/" + "0" + (i + 1) + ".png");

                // 確保資源存在
                if (!resource.exists()) {
                    throw new RuntimeException("Resource not found: template-pic/0" + (i + 1) + ".png");
                }

                // 將資源寫入臨時文件
                File tempFile = Files.createTempFile("photo_0" + (i + 1), ".png").toFile();
                tempFile.deleteOnExit(); // 程式結束後自動刪除臨時文件

                try (InputStream inputStream = resource.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                    inputStream.transferTo(outputStream);
                }

                // 構建 photo 資訊 Map
                Map<String, Object> photo = new HashMap<>();
                photo.put("CONTENT", "檔案說明(測試內容)");
                photo.put("FILENAME", tempFile.getName());
                photo.put("FILE", tempFile.getAbsolutePath());
                photo.put("SEQ", i + 1);
                photoInfoList.add(photo);
            } catch (Exception e) {
                throw new RuntimeException("Error loading photo info for index " + (i + 1), e);
            }
        }


        planInfo.put("table2", jobInfoList);
        planInfo.put("table3", pexpInfoList);
        planInfo.put("table4", photoInfoList);

        return planInfo;
    }
}
