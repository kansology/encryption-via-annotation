package com.kansal.annotationimpl.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kansal.annotation.convertor.Decryptor;
import com.kansal.annotation.convertor.Encryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
public class PersonDecrypt {
    private String fname;
    private String lname;
    @JsonSerialize(converter = Decryptor.class)
    private String ssnLast4;
}
