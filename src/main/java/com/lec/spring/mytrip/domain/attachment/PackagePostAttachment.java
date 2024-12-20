package com.lec.spring.mytrip.domain.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackagePostAttachment {
    private int packageAttachmentId; // 첨부파일 ID
    private int packageId;           // 패키지 ID
    private String packageAttachmentFile;          // 첨부파일 이름'
}
