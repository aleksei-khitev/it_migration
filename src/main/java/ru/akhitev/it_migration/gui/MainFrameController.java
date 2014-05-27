/*
 * ru.akhitev.it_migration is an JavaFX application for automatically it infrastructure migration from Windows to Linux, with it's all services.
 * Copyright (c) 2014 Aleksei Khitevi (Хитёв Алексей Юрьевич).
 *
 * This file is part of ru.akhitev.it_migration
 *
 * ru.akhitev.it_migration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * ru.akhitev.it_migration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/

package ru.akhitev.it_migration.gui;


import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import ru.akhitev.it_migration.Configurator;

public class MainFrameController implements Initializable{
    private static final Logger logger = Logger.getLogger(MainFrameController.class);
    private ResourceBundle bundle;
    @FXML
    private TextArea actiontarget;
    @FXML
    private TextField terminalinline;
    @FXML
    private TextField sship;
    @FXML
    private TextField sshport;
    @FXML
    private TextField sshuname;
    @FXML
    private PasswordField sshupass;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label terminallabel;
    @FXML
    private Button sshconbtn;
    @FXML
    private TextArea frameLog;
    @FXML
    private Text title;
    @FXML
    private PasswordField adupass;
    @FXML
    private PasswordField sambaupass;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
    }
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event){

    }
    @FXML
    private void handleAboutAction(final ActionEvent event){
        provideAboutFunctionality();
    }
    @FXML
    private void handleKeyInput(final InputEvent event){
        if (event instanceof KeyEvent){
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A){
                provideAboutFunctionality();
            }
        }
    }
    //Набор методов обработки соединения с сервером SSH
    @FXML
    public void handleConDisconSsh(){
        if(!Configurator.getSshClient().isInitSession()){
            Configurator.getSshClient().setUserName(sshuname.getText());
            Configurator.getSshClient().setPassword(sshupass.getText());
            Configurator.getSshClient().setPort(Integer.parseInt(sshport.getText()));
            Configurator.getSshClient().setHost(sship.getText());
            Configurator.getSshClient().initSession();
            logger.info(bundle.getString("sshConnected"));
            frameLog.appendText(bundle.getString("sshConnected")+"\r\n");
        }
        if(Configurator.getSshClient().isConnected()){
            Configurator.getSshClient().closeConnection();
            logger.info(bundle.getString("sshDisconnected"));
            frameLog.appendText(bundle.getString("sshDisconnected")+"\r\n");
            terminallabel.setText(bundle.getString("terminalLabelDefaultText"));
        }else{
            Configurator.getSshClient().openConnection();
            logger.info("Соединение SSH установленно");
            frameLog.appendText("Соединение SSH установленно\r\n");
            terminallabel.setText(Configurator.getSshClient().getUserName()+"@"+Configurator.getSshClient().getHost()+" $");
        }
    }
    @FXML
    public void onEnter(){
        if(Configurator.getSshClient()==null){
            logger.info("Менеджер SSH не инициализирован");
            frameLog.appendText("Менеджер SSH не инициализирован\r\n");
            return;
        }
        if(!Configurator.getSshClient().isInitSession()){
            logger.info("Сессия SSH не инициализирована");
            frameLog.appendText("Сессия SSH не инициализирована\r\n");
            return;
        }
        actiontarget.appendText(terminallabel.getText()+" "+terminalinline.getText()+"\r\n");
        actiontarget.appendText(Configurator.getSshClient().executeCommand(terminalinline.getText()));
        terminalinline.setText("");
    }
    // Установка SAMBA

    @FXML
    public void handleInstallSamba(){
        //sshManager = (SshManager)springXmlAppContext.getBean("ssh-manager");
        Configurator.getNixInstaller().isSupported("ubuntu", "12.04", "samba");
        //Configurator.getNixInstaller().setSshClient(sshClient);
        Configurator.getNixInstaller().setGuiLoggerFX(frameLog);
        Configurator.getNixInstaller().setTerminalAreaFX(actiontarget);
        Configurator.getNixInstaller().install();
    }

    private void provideAboutFunctionality(){
        System.out.println("You clicked on About!");
    }
}

