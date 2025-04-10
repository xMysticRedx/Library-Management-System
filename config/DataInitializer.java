package com.library.management.config;

import com.library.management.model.Book;
import com.library.management.model.Role;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      BookRepository bookRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@library.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("✅ Admin user created: admin / admin123");
            }

            if (bookRepository.count() == 0) {
                List<Book> books = List.of(
                        new Book("1984", "George Orwell", "Dystopian", 7),
                        new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 5),
                        new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 6),
                        new Book("Brave New World", "Aldous Huxley", "Science Fiction", 4),
                        new Book("Dune", "Frank Herbert", "Science Fiction", 8),
                        new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 10),
                        new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 6),
                        new Book("The Fellowship of the Ring", "J.R.R. Tolkien", "Fantasy", 5),
                        new Book("Gone Girl", "Gillian Flynn", "Thriller", 4),
                        new Book("The Da Vinci Code", "Dan Brown", "Mystery", 7),
                        new Book("Pride and Prejudice", "Jane Austen", "Romance", 6),
                        new Book("The Notebook", "Nicholas Sparks", "Romance", 4),
                        new Book("The Shining", "Stephen King", "Horror", 5),
                        new Book("Dracula", "Bram Stoker", "Horror", 3),
                        new Book("Educated", "Tara Westover", "Biography", 4),
                        new Book("Becoming", "Michelle Obama", "Biography", 5),
                        new Book("Sapiens", "Yuval Noah Harari", "Science", 6),
                        new Book("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", "Science", 4),
                        new Book("The Power of Now", "Eckhart Tolle", "Self-Help", 4),
                        new Book("Atomic Habits", "James Clear", "Self-Help", 5),
                        new Book("Into the Wild", "Jon Krakauer", "Adventure", 5),
                        new Book("Life of Pi", "Yann Martel", "Adventure", 3),
                        new Book("The Alchemist", "Paulo Coelho", "Philosophy", 7),
                        new Book("Meditations", "Marcus Aurelius", "Philosophy", 4),
                        new Book("Leaves of Grass", "Walt Whitman", "Poetry", 3),
                        new Book("Milk and Honey", "Rupi Kaur", "Poetry", 4),
                        new Book("Maus", "Art Spiegelman", "Graphic Novel", 3),
                        new Book("Watchmen", "Alan Moore", "Graphic Novel", 4),
                        new Book("Charlotte's Web", "E.B. White", "Children’s", 6),
                        new Book("Matilda", "Roald Dahl", "Children’s", 5),
                        new Book("The Fault in Our Stars", "John Green", "Young Adult", 5),
                        new Book("The Hunger Games", "Suzanne Collins", "Young Adult", 6),
                        new Book("The Book Thief", "Markus Zusak", "Historical Fiction", 4),
                        new Book("All the Light We Cannot See", "Anthony Doerr", "Historical Fiction", 5),
                        new Book("Kitchen Confidential", "Anthony Bourdain", "Cooking", 3),
                        new Book("Salt, Fat, Acid, Heat", "Samin Nosrat", "Cooking", 4),
                        new Book("Vagabonding", "Rolf Potts", "Travel", 3),
                        new Book("Into the Heart of the Sea", "Nathaniel Philbrick", "Travel", 2),
                        new Book("The Girl on the Train", "Paula Hawkins", "Thriller", 5)
                );

                bookRepository.saveAll(books);
                System.out.println("📚 40 sample books added to the database.");
            } else {
                System.out.println("ℹ️ Books already present in the database.");
            }
        };
    }
}
