package com.ibcs.salaryapp.model.domain.util;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class RecordAuditor extends TimeAuditor {

    @ApiModelProperty(hidden = true)
    @CreatedBy
    @Column(name = "createdBy", length = 50)
    private String createdBy;

    @ApiModelProperty(hidden = true)
    @LastModifiedBy
    @Column(name = "updatedBy", length = 50)
    private String updatedBy;

    public RecordAuditor() {
        super();
    }

}
