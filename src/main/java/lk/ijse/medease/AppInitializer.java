package lk.ijse.medease;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setResizable(false);
        stage.setTitle("MEDEASE");

        Image icon = new Image(getClass().getResourceAsStream("/images/billLogo.png"));
        stage.getIcons().add(icon);

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoadingScreen.fxml"))));
        stage.show();

        Task<Scene> sceneTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        sceneTask.setOnSucceeded(event -> {
            Scene value = sceneTask.getValue();
            stage.setScene(value);
        });

        sceneTask.setOnFailed(event -> {
            System.out.println("Fail to load MedEase");
        });

        new Thread(sceneTask).start();

//        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
//
//        Scene scene = new Scene(parent);
//        stage.setScene(scene);
//        stage.setTitle("MedEase");
//        stage.show();
    }
}
