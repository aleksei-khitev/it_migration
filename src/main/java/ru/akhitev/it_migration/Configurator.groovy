package ru.akhitev.it_migration

import org.springframework.context.support.FileSystemXmlApplicationContext
import ru.akhitev.installer.nix.INixInstaller
import ru.akhitev.it_migration.gui.localization.ILocalizator
import ru.akhitev.net.ssh.ISshClient

/**
 * Created by hitev on 27.05.14.
 */
class Configurator {
    static final String LOCALIZATION_PATH = "conf${File.separator}localization"
    static final String SPRING_CONF_PATH = "conf${File.separator}spring${File.separator}spring-config.xml"
    static final String FXMLS_PATH = "conf${File.separator}views${File.separator}MainFrame.fxml"

    static ILocalizator localizator
    static ISshClient sshClient
    static INixInstaller nixInstaller
    static FileSystemXmlApplicationContext springXmlAppContext

    static void loadClassesFromSpring(){
        springXmlAppContext = new FileSystemXmlApplicationContext(SPRING_CONF_PATH);
        localizator = (ILocalizator)springXmlAppContext.getBean("localizator")
        sshClient = (ISshClient)springXmlAppContext.getBean("ssh-client")
        nixInstaller = (INixInstaller)springXmlAppContext.getBean("nix-installer")
    }
}
