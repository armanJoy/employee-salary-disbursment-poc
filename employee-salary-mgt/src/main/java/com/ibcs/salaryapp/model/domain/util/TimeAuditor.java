package com.ibcs.salaryapp.model.domain.util;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class TimeAuditor {

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    @Column(name = "createdAt", columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    @Column(name = "updatedAt", columnDefinition = "timestamp without time zone DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime updatedAt;

    public TimeAuditor() {
    }

}
