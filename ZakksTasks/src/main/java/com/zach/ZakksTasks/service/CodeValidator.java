package com.zach.ZakksTasks.service;

import org.springframework.stereotype.Service;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

@Service
public class CodeValidator {

    // Validate syntax using Java compiler (stubbed here)
    public boolean validateJavaCode(String code) {
        try {
            // You can implement real compilation validation later
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Limit code length
    public boolean validateCodeLength(String code) {
        final int MAX_CODE_LENGTH = 5000;  // 5 KB
        return code.length() <= MAX_CODE_LENGTH;
    }

    // Detect unsafe or dangerous code patterns
    public boolean checkForUnsafeCode(String code) {
        String[] blacklistedPatterns = {
            "Thread.sleep", "while(true)", "for(;;)",
            "System.exit", "Runtime.getRuntime()", 
            "java.io", "java.net", "ProcessBuilder",
            "Files.read", "Files.write", "InputStream", "OutputStream"
        };

        for (String pattern : blacklistedPatterns) {
            if (code.contains(pattern)) {
                return false;
            }
        }

        return true;
    }

    // Auto-format Java code
    public String formatCode(String code) throws FormatterException {
        Formatter formatter = new Formatter();
        return formatter.formatSource(code);
    }
}
