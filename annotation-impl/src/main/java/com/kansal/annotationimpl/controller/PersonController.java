package com.kansal.annotationimpl.controller;

import com.kansal.annotationimpl.model.PersonDecrypt;
import com.kansal.annotationimpl.model.PersonEncrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @PostMapping(produces = "application/json", path = "/encrypt")
    @ResponseBody
    public PersonEncrypt encryptPerson(@RequestBody PersonEncrypt personEncrypt) {
        System.out.println("personEncrypt = [" + personEncrypt + "]");
        return personEncrypt;
    }

    @PostMapping(produces = "application/json", path = "/decrypt")
    @ResponseBody
    public PersonDecrypt encryptPerson(@RequestBody PersonDecrypt personDecrypt) {
        System.out.println("personDecrypt = [" + personDecrypt + "]");
        return personDecrypt;
    }
}
