package com.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressRequest {

    private Long userId;
    private Long courseId;
    private float playedTime;
    private float duration;


}
