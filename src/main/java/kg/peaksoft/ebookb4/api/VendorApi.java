package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
@CrossOrigin
@RestController
@RequestMapping("/api/vendor/create-promo")
@AllArgsConstructor
@RolesAllowed("ROLE_VENDOR")
@Tag(name = "Vendor",description = "Vendor accessible apis")
public class VendorApi {



}
