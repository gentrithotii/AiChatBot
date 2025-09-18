package com.gentrit.aichatbot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Valid
public class ChatRequestDto {
    @Getter
    @Setter
    @NotBlank(message = "Question cannot be blank")
    private String question;

    @Getter
    @NotNull(message = "Explanation is required")
    private ExplanationLevel explanationLevel;
}
