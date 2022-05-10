package kg.peaksoft.ebookb4.db.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 24/4/22
 */
@Getter
@Setter
public class VendorUpdateDTO {
//    @NotBlank
//    @Size(max = 50)
//    @Email
//    private String email;
//    private Long id;

    private String firstName;

    private String lastName;

    private String number;

    @NotBlank
    @Size(min = 6, max = 40)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    private String confirmNewPassword;
}
