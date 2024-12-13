package app.service;


import app.common.word.AbstractWordGenerator;
import app.report.AccountPermissionReport;
import app.report.ProjectInformationReport;
import org.apache.commons.io.FileUtils;
import org.jodconverter.core.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataConverterService {

    @Autowired
    GenerateTestDataService generateTestDataService;

    public static File byteArrayToFile(byte[] byteArray, String fileName) throws IOException {
        // 創建最終檔案
        File file = Files.createTempFile(null, fileName).toFile();
        file.deleteOnExit(); // 設定程式結束時刪除該檔案

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = byteArrayInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
        return file;
    }

    public void accountPermission() {

        List<Map<String, Object>> dataList = generateTestDataService.generateAccountPermissionData();
        // 單位分組
        Map<Object, List<Map<String, Object>>> unitGroupData = dataList.stream().collect(Collectors.groupingBy(item -> item.get("UNIT")));
        // map 轉 list
        List<Map<String, Object>> listGroupData = unitGroupData.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("UNIT", entry.getKey());
                    map.put("table1", entry.getValue());
                    return map;
                })
                .toList();

        final Map<String, Object> results = new HashMap<>();
        results.put("objectives", listGroupData);

        final AbstractWordGenerator<Map<String, Object>> report = new AccountPermissionReport(results);
        final byte[] bytes = report.generateWord();
        final String finalName = "帳號權限審查表(範本).docx";
        File outputFile = new File( "D:\\" + finalName);
        try {
            FileUtils.writeByteArrayToFile(outputFile, bytes);
        } catch (Exception e) {
            throw new RuntimeException("寫入檔案失敗", e);
        }

        try {
            Desktop.getDesktop().open(outputFile);
        } catch (Exception e) {
            throw new RuntimeException("開啟檔案失敗", e);
        }

//        return wordService.downloadWord("帳號權限審查表", report, true);

    }

    public void projectInformation() {
        Map<String, Object> results = generateTestDataService.generateProjectInformationData();
        final AbstractWordGenerator<Map<String, Object>> report = new ProjectInformationReport(results);
        final byte[] bytes = report.generateWord();
        final String finalName = "活動計畫基本資料(範本).docx";
        File outputFile = new File( "D:\\" + finalName);
        try {
            FileUtils.writeByteArrayToFile(outputFile, bytes);
        } catch (Exception e) {
            throw new RuntimeException("寫入檔案失敗", e);
        }
        try {
            Desktop.getDesktop().open(outputFile);
        } catch (Exception e) {
            throw new RuntimeException("開啟檔案失敗", e);
        }

    }
}
