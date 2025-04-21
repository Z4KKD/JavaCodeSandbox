package com.zach.ZakksTasks.controller;

import com.zach.ZakksTasks.service.CodeExecutionService;
import com.zach.ZakksTasks.service.CodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;
    private final CodeValidator codeValidator;

    @Autowired
    public CodeExecutionController(CodeExecutionService codeExecutionService, CodeValidator codeValidator) {
        this.codeExecutionService = codeExecutionService;
        this.codeValidator = codeValidator;
    }

    @PostMapping("/execute")
    public ResponseEntity<String> executeCode(@RequestBody String code) {
        // Validate code length
        if (!codeValidator.validateCodeLength(code)) {
            return ResponseEntity.badRequest().body("Code is too long. Max allowed length is 5000 characters.");
        }

        // Check for unsafe code patterns
        if (!codeValidator.checkForUnsafeCode(code)) {
            return ResponseEntity.badRequest().body("Code contains unsafe operations and was rejected.");
        }

        // Optionally format code
        try {
            code = codeValidator.formatCode(code);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Code formatting failed: " + e.getMessage());
        }

        // Execute code
        return codeExecutionService.executeCode(code);
    }
}
