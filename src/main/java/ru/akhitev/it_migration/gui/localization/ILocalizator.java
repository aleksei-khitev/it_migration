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

package ru.akhitev.it_migration.gui.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The interface for localization
 *
 * @author Aleksei Khitev (alexkhitev@gmail.com)
 */
public interface ILocalizator {
    /**
     * The method used for setting locale
     * @param locale
     */
    public void setLocale(String locale);

    /**
     * The method used for getting locale's bundle
     * @return
     */
    public ResourceBundle getLocalizationResource();
}
