/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.wiki.test.po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @since 6.0M1
 */
public class DeleteWikiPage extends ExtendedViewPage
{
    @FindBy(id = "confirmButton")
    private WebElement confirmButton;
    
    @FindBy(id = "wikiDeleteConfirmation")
    private WebElement wikiDeleteConfirmationInputField;

    @FindBy(xpath = "//*[@id=\"xwikicontent\"]/div[@class=\"box errormessage\"]")
    private WebElement errorMessage;

    @FindBy(xpath = "//*[@id=\"xwikicontent\"]/div[@class=\"box successmessage\"]")
    private WebElement successMessage;

    public DeleteWikiPage confirm(String wikiId)
    {
        wikiDeleteConfirmationInputField.clear();
        wikiDeleteConfirmationInputField.sendKeys(wikiId);
        confirmButton.click();
        return new DeleteWikiPage();
    }

    public boolean hasSuccessMessage()
    {
        return successMessage.isDisplayed();
    }

    public boolean hasUserErrorMessage()
    {
        return errorMessage.isDisplayed() && errorMessage.getText().contains("Type in the exact");
    }

    public boolean hasWikiDeleteConfirmationInput(String value)
    {
        return value.equals(wikiDeleteConfirmationInputField.getAttribute("value"));
    }

}
