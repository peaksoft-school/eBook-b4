package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Boolean existsByEmail(String email);

  @Query("select s from User s where s.email = ?1")
  Optional<User> getUser(String username);

  @Query("select s from User s where s.email = ?1")
  User findByUserName(String username);

  //fin all vendors / admin panel
  @Query("select u from User u where u.role.name=?1")
  List<User> findAllVendors(ERole role);

  //fin all clients / admin panel
  @Query("select u from User u where u.role.name=?1")
  List<User> findAllClients(ERole role);

  @Query("select c.likedBooks from User c where c.id = ?1")
  List<Book> getAllLikedBooks(Long id);

  @Query("select new kg.peaksoft.ebookb4.db.models.response.BookResponse(b.bookId, b.title, b.authorFullName, b.aboutBook, b.publishingHouse,b.yearOfIssue, b.price, b.fileInformation)" +
          "from Book b where b.operations.user.email = ?1")
  List<BookResponse> getBooksInPurchased(String name);

  //change discountPromo to null if it is expired
  @Transactional
  @Modifying
  @Query("update Book b set b.discountFromPromo = null where b.user = ?1")
  void checkForPromos(User user);

  //We give here a promo
  @Transactional
  @Modifying
  @Query("update Book b set b.discountFromPromo = ?2 where b.user = ?1 and b.discount is null ")
  void givePromo(User user, int discount);

}
