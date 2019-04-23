package ru.otus.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.BookService;

import java.util.UUID;

@ShellComponent
public class LibraryCommands {


    private final JobLauncher jobLauncher;

    private final Job loadDataJob;

    private final BookService bookService;

    @Autowired
    public LibraryCommands(BookService bookService, JobLauncher jobLauncher, Job loadDataJob) {
        this.bookService = bookService;
        this.jobLauncher = jobLauncher;
        this.loadDataJob = loadDataJob;
    }

    @ShellMethod(value = "Show all books in Library", key = "showAllBooks")
    public void printAllBooks() {
        bookService.printAllBooks().subscribe();

    }


    @ShellMethod(value = "Show all genres in Library", key = "showAllGenres")
    public void printAllGenres() {
        bookService.printAllGenres().subscribe();

    }

    @ShellMethod(value = "Add comment to Book", key = "addComment")
    public void addComment(@ShellOption({"-t", "--title"}) String bookTitle,
                           @ShellOption({"-c", "--comment"}) String comment) {
        bookService.addCommentToBook(comment, bookTitle).subscribe();

    }

    @ShellMethod(value = "Show All Authors", key = "showAllAuthors")
    public void showAllAuthors() {
        bookService.printAllAuthors().subscribe();
    }

    @ShellMethod(value = "Put new book in Library", key = "newBook")
    public void storeNewBook(@ShellOption({"-t", "--title"}) String title,
                             @ShellOption({"-a", "--author"}) String author, @ShellOption({"-g", "--genre"}) String genre) {


        bookService.storeNewBook(title, author, genre).subscribe();
    }

    @ShellMethod(value = "Show book in Library by title", key = "findByTitle")
    public void showBookByTitle(@ShellOption String title) {
        bookService.printByName(title).subscribe();
    }

    @ShellMethod(value = "Remove book from library with Title", key = "removeBook")
    public void removeByTitle(@ShellOption({"-t", "--title"}) String title) {
        bookService.deleteBook(title).subscribe();
    }

    @ShellMethod(value = "Prints all Comments", key = "showAllComments")
    public void showAllComments() {
        bookService.printAllComments().subscribe();
    }


    @ShellMethod(value = "Load books from file", key = "loadBooks")
    public void loadBooks() {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", UUID.randomUUID().toString())
                .toJobParameters();
        try {
            jobLauncher.run(loadDataJob, params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
