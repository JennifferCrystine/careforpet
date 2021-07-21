package com.gpms.petcare.validator;

import com.gpms.petcare.model.Hospedagem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@Component
public class HospedagemValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Hospedagem.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Hospedagem h = (Hospedagem) o;

        Field[] fields = h.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(f-> {
            f.setAccessible(true);
            try {
                if (!f.getName().equals("id") && Objects.isNull(f.get(h))) {
                    errors.reject("Todos campos devem ser preenchidos");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
