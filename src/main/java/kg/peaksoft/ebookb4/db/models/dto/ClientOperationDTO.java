package kg.peaksoft.ebookb4.db.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientOperationDTO {

    int count;

    Double discount;

    Double sum;

    Double total;

}