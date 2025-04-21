package com.zach.ZakksTasks.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.*;

@Service
public class CodeExecutionService {

    public ResponseEntity<String> executeCode(String code) {
        String containerName = "taskifydb-container"; // replace if you renamed it
        String tempFilePath = "TempCode.java"; // write the code to a file

        try {
            // Write code to a temporary file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath))) {
                writer.write(code);
            }

            // Docker command with security limits
            ProcessBuilder builder = new ProcessBuilder(
                "docker", "run", "--rm",
                "--memory=256m", "--cpus=0.5", "--network=none",
                "-v", new File(tempFilePath).getAbsolutePath() + ":/app/TempCode.java",
                containerName
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            // Wait for process to complete (with timeout)
            boolean finished = process.waitFor(5, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                return ResponseEntity.ok("Code execution timed out.");
            }

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            return ResponseEntity.ok(output.toString());

        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(500).body("Error during code execution: " + e.getMessage());
        } finally {
            new File(tempFilePath).delete(); // cleanup
        }
    }
}
