package com.example.test1.MVC.Controller;

import com.example.test1.config.DicomEchoClient;
import com.example.test1.config.DicomQueryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class testController {
    private final DicomEchoClient dicomEchoClient = new DicomEchoClient();
    private final DicomQueryClient dicomQueryClient = new DicomQueryClient();

    @PostMapping("/echo")
    public ResponseEntity<String> testEcho() {
        boolean result = dicomEchoClient.echo("192.168.1.251", 1400, "MAROTECH", "MAROTECH");

        if (result) {
            return ResponseEntity.ok("DICOM Echo 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DICOM Echo 실패");
        }
    }

    @PostMapping("/query")
    public ResponseEntity<String> queryDicom(
            @RequestParam String patientId,
            @RequestParam String accessionNumber,
            @RequestParam String seriesNumber,
            @RequestParam String imageNumber) {

        boolean result = dicomQueryClient.query(patientId, accessionNumber, seriesNumber, imageNumber);

        if (result) {
            return ResponseEntity.ok("DICOM Query 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DICOM Query 실패");
        }
    }
}
