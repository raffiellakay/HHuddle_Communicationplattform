package com.knoettner.hhuddle.security.modelsDtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private Long id;
    //private String msgBody;
    //private String subject;

}
