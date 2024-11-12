package src.com;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import src.com.model.Customer;
import src.com.model.Employee;
import src.com.utils.HibernateUtils;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                Session session = HibernateUtils.getSessionFactory().openSession();
                Employee employee = null;
                try {
                    session.beginTransaction();

                    // Lấy Customer nhưng Employee chưa được tải vì FetchType.LAZY
                    Customer customer = session.find(Customer.class, 278);

                    // Tại thời điểm này, Employee chưa được lấy từ DB
                    employee = customer.getSalesRepEmployeeNumber();
                    System.out.println(employee.getId());// Truy cập sẽ kích hoạt truy vấn lấy Employee


                    session.getTransaction().commit();
                } catch (Exception e) {
                    if (session.getTransaction() != null) {
                        session.getTransaction().rollback();
                    }
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Hello World!");
//        System.out.println(employee.getFirstName());

        launch();
    }
}