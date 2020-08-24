package com.sampaio.main;

import com.sampaio.modelo.Configuracao;
import com.sampaio.servico.ConfiguracaoServico;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainApplication extends Application {


    public static Stage stage;
    private java.awt.SystemTray tray = null;
    private java.awt.TrayIcon trayIcon;
    private java.awt.Image iconeTray = null;
    private Image icone = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        icone = new Image("/images/vetor.png");
        iconeTray = ImageIO.read(getClass().getResource("/images/vetor.png")).getScaledInstance(16, 16, 16);
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("main.fxml")));

        stage.setTitle("ToolBox App");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        stage.hide();


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stage.hide();
                    }
                });
            }
        });

        Platform.setImplicitExit(false);

        Configuracao configuracao = ConfiguracaoServico.buscar();

        if (configuracao == null) {
            configuracao = new Configuracao();

            Map<String, String> pathss = new HashMap<>();
            pathss.put("League of Legends", "C:\\Riot Games\\League of Legends\\LeagueClient.exe");

            configuracao.setPathApp(pathss);

            ConfiguracaoServico.salvar(configuracao);
        }

        javax.swing.SwingUtilities.invokeLater(() -> addAppToTray(ConfiguracaoServico.buscar()));

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
        Platform.exit();

    }

    private void addAppToTray(Configuracao configuracao) {
        try {
            Toolkit.getDefaultToolkit();

            if (!SystemTray.isSupported()) {

            }

            tray = SystemTray.getSystemTray();
            trayIcon = new TrayIcon(iconeTray);

            trayIcon.addActionListener(event -> Platform.runLater(() -> stage.show()));

//            java.awt.MenuItem openItem = new java.awt.MenuItem("Maximizar " + NOME);
//            openItem.addActionListener(event -> Platform.runLater(this::showStage));


            MenuItem exitItem = new MenuItem("Sair");
            exitItem.addActionListener(event -> {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        Platform.exit();
                        System.exit(0);

                    }
                });

            });


            final PopupMenu popup = new PopupMenu();

            configuracao.getPathApp().forEach((path, title) -> {
                MenuItem menuItem = retornaMenuItem(title, path);

                popup.add(menuItem);
            });

            popup.addSeparator();
            popup.add(exitItem);


            trayIcon.setPopupMenu(popup);

            MouseMotionListener mml = new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                }

                public void mouseMoved(MouseEvent e) {

                    trayIcon.setToolTip("teste");
                }
            };
            trayIcon.addMouseMotionListener(mml);

            tray.add(trayIcon);

//            trayIcon.displayMessage("HOLO", "HOLO", TrayIcon.MessageType.ERROR);

        } catch (Exception e) {

        }
    }

    public MenuItem retornaMenuItem(String path, String title) {
        MenuItem lolItem = new MenuItem(title);

        lolItem.addActionListener(event -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Runtime run = Runtime.getRuntime();


                    try {
                        run.exec(path);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        });

        return lolItem;
    }
}
