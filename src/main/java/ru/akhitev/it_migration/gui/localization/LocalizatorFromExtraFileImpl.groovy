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

package ru.akhitev.it_migration.gui.localization

import ru.akhitev.it_migration.Configurator

/**
 * Created by hitev on 27.05.14.
 */
class LocalizatorFromExtraFileImpl implements ILocalizator {
    String locale

    ResourceBundle getLocalizationResource(){
        File file = new File(Configurator.LOCALIZATION_PATH);
        URL[] urls = [file.toURI().toURL()];
        ClassLoader loader = new URLClassLoader(urls);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("local", new Locale(locale), loader)
    }
}
