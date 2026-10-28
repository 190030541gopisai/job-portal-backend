package com.gopi.job.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCategoryRequest {
    @NotBlank(message = "category name is required")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private String iconUrl;

    /**
     * Set to make this a sub-category; null means root-level.
     */
    private Long parentId;
}
