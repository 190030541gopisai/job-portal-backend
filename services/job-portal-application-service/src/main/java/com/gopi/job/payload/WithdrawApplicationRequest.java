package com.gopi.job.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawApplicationRequest {
    private String reason;
}
