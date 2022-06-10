package com.billwen.learning.imooc.uaa.domain.dto;

import com.billwen.learning.imooc.uaa.domain.MfaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendTotpDto implements Serializable {

    @NotNull
    private String mfaId;

    @NotNull
    private MfaType mfaType;
}
