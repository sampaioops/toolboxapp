package com.sampaio.controller;

import com.jfoenix.controls.JFXListView;
import com.sampaio.main.MainApplication;
import com.sampaio.modelo.Configuracao;
import com.sampaio.servico.ConfiguracaoServico;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


public class Controller {

    private FileChooser fileChooser = new FileChooser();

    private Configuracao configuracao;

    @FXML
    private JFXListView<String> jfxListApps;

    @FXML
    public void initialize() {
        configuracao = ConfiguracaoServico.buscar();

        configuracao.getPathApp().forEach((title, path) -> {
            jfxListApps.getItems().add(title);
        });

    }


    public void selecionaArquivo() {
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        if (file != null && file.exists()) {

            configuracao.getPathApp().put(file.getName(), file.getAbsolutePath());

            ConfiguracaoServico.salvar(configuracao);

            jfxListApps.getItems().add(file.getName());

        }


    }

    public void removerItem() {
        String selectedItem = jfxListApps.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            configuracao.getPathApp().remove(selectedItem);

            ConfiguracaoServico.salvar(configuracao);

            jfxListApps.getItems().remove(selectedItem);
        }

    }


}
