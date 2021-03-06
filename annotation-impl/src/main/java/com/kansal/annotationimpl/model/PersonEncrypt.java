package com.kansal.annotationimpl.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kansal.annotation.convertor.Encryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
public class PersonEncrypt {
    private String fname;
    private String lname;
    @JsonDeserialize(converter = Encryptor.class)
    private String ssnLast4;
}
