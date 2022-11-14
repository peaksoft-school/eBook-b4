package kg.peaksoft.ebookb4.db.models.mappers;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.dto.ClientOperationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ClientOperationMapper {

    private BookRepository bookRepository;

    public ClientOperationDTO build(int count, Double discount, Double sum, Double total) {
        ClientOperationDTO dto = new ClientOperationDTO();
        dto.setCount(count);
        dto.setDiscount(discount);
        dto.setSum(sum);
        dto.setTotal(total);
        return dto;
    }

    public ClientOperationDTO create(String name) {
        List<Book> all = bookRepository.findBasketByClientId(name);
        ClientOperationDTO dto = new ClientOperationDTO();
        dto.setCount(count(all));
        dto.setDiscount(discount(all));
        dto.setSum(priseSum(all));
        dto.setTotal(total(all));
        return dto;
    }

    public Double discount(List<Book> list) {
        double sumAfterDiscount = 0;
        for (Book book : list) {
            if (book.getDiscount() != null) {
                sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
            } else {
                continue;
            }
        }
        return sumAfterDiscount;
    }

    public Double total(List<Book> list) {
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getDiscount() != null) {
                sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
            }
            continue;
        }
        for (Book book : list) {
            sum += book.getPrice();
        }
        return sum - sumAfterDiscount;
    }

    public Double priseSum(List<Book> list) {
        Double sum = 0.0;
        for (Book book : list) {
            sum += book.getPrice();
        }
        return sum;
    }

    public Integer count(List<Book> list) {
        Integer sum = 0;
        for (Book book : list) {
            sum++;
        }
        return sum;
    }

}