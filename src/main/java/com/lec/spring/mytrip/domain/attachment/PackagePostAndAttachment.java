package com.lec.spring.mytrip.domain.attachment;

import com.lec.spring.mytrip.domain.PackagePost;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PackagePostAndAttachment {
    PackagePost packagePost;
    List<PackagePostAttachment> packagePostAttachment;
}
