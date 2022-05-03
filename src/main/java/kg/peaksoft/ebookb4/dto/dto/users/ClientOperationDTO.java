package kg.peaksoft.ebookb4.dto.dto.users;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientOperationDTO {

    int count;
    Double discount;
    Double sum;
    private String promocode;
    Double total;
}
