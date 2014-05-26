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

import ru.akhitev.installer.nix.INixInstaller;
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
import ru.akhitev.net.ssh.ISshClient;

public class MainFrameController implements Initializable{
    private static final Logger logger = Logger.getLogger(MainFrameController.class);
    private ISshClient sshClient=null;
    private static FileSystemXmlApplicationContext springXmlAppContext;
    private ResourceBundle bundle;
    private INixInstaller installer = null;
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
        if(sshClient==null){
            springXmlAppContext = new FileSystemXmlApplicationContext(new String[] {"dat/spring-config.xml"});
            sshClient = (ISshClient)springXmlAppContext.getBean("ssh-manager");
        }
        if(!sshClient.isInitSession()){
            sshClient.setUserName(sshuname.getText());
            sshClient.setPassword(sshupass.getText());
            sshClient.setPort(Integer.parseInt(sshport.getText()));
            sshClient.setHost(sship.getText());
            sshClient.initSession();
            logger.info(bundle.getString("sshConnected"));
            frameLog.appendText(bundle.getString("sshConnected")+"\r\n");
        }
        if(sshClient.isConnected()){
            sshClient.closeConnection();
            logger.info(bundle.getString("sshDisconnected"));
            frameLog.appendText(bundle.getString("sshDisconnected")+"\r\n");
            terminallabel.setText(bundle.getString("terminalLabelDefaultText"));
        }else{
            sshClient.openConnection();
            logger.info("Соединение SSH установленно");
            frameLog.appendText("Соединение SSH установленно\r\n");
            terminallabel.setText(sshClient.getUserName()+"@"+sshClient.getHost()+" $");
        }
    }
    @FXML
    public void onEnter(){
        if(sshClient==null){
            logger.info("Менеджер SSH не инициализирован");
            frameLog.appendText("Менеджер SSH не инициализирован\r\n");
            return;
        }
        if(!sshClient.isInitSession()){
            logger.info("Сессия SSH не инициализирована");
            frameLog.appendText("Сессия SSH не инициализирована\r\n");
            return;
        }
        actiontarget.appendText(terminallabel.getText()+" "+terminalinline.getText()+"\r\n");
        actiontarget.appendText(sshClient.executeCommand(terminalinline.getText()));
        terminalinline.setText("");
    }
    // Установка SAMBA

    @FXML
    public void handleInstallSamba(){
        springXmlAppContext = new FileSystemXmlApplicationContext(new String[] {"dat/spring-config.xml"});
        installer = (INixInstaller)springXmlAppContext.getBean("samba-installer");
        //sshManager = (SshManager)springXmlAppContext.getBean("ssh-manager");
        installer.isSupported("ubuntu", "12.04", "samba");
        installer.setSshClient(sshClient);
        installer.setGuiLogger(frameLog);
        installer.setTerminalArea(actiontarget);
        installer.install();
    }

    private void provideAboutFunctionality(){
        System.out.println("You clicked on About!");
    }
}

