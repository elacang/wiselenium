package org.wiselenium.core.element.field;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.wiselenium.core.element.field.ButtonPage.BUTTON_CLICKED_MESSAGE;
import static org.wiselenium.core.element.field.ButtonPage.BUTTON_DOUBLE_CLICKED_MESSAGE;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wiselenium.core.test.WiseTestNG;

@SuppressWarnings("javadoc")
public class BasicFieldTest extends WiseTestNG<BasicFieldTest> {
	
	ButtonPage page;
	
	
	@BeforeMethod
	public void initPage() {
		this.page = this.initElements(ButtonPage.class);
		this.page.get();
	}
	
	@Test
	public void shouldAllowChainCallsWithAnd() {
		Button button = this.page.getButton();
		assertEquals(button, button.and());
	}
	
	@Test
	public void shouldClick() {
		this.page.getButton().click();
		assertEquals(this.page.getMessage(), BUTTON_CLICKED_MESSAGE);
	}
	
	@Test
	public void shouldDoubleClick() {
		this.page.getButton().doubleClick();
		assertEquals(this.page.getMessage(), BUTTON_DOUBLE_CLICKED_MESSAGE);
	}
	
	@Test
	public void shouldGetAttribute() {
		assertNotNull(this.page.getButton().getAttribute("id"));
		assertNotNull(this.page.getHiddenButton().getAttribute("id"));
	}
	
	@Test
	public void shouldGetCssValue() {
		assertTrue(this.page.getButton().getCssValue("inexistent").isEmpty());
		assertNotNull(this.page.getHiddenButton().getCssValue("visibility"));
	}
	
	@Test
	public void shouldGetDisplayCondition() {
		assertTrue(this.page.getButton().isDisplayed());
		assertFalse(this.page.getHiddenButton().isDisplayed());
	}
	
	@Test
	public void shouldGetId() {
		assertFalse(this.page.getButton().getId().isEmpty());
	}
	
	@Test
	public void shouldGetStyleClass() {
		assertFalse(this.page.getButton().getStyleClass().isEmpty());
		assertTrue(this.page.getHiddenButton().getStyleClass().isEmpty());
	}
	
	@Test
	public void shouldGetTitle() {
		assertFalse(this.page.getButton().getTitle().isEmpty());
		assertTrue(this.page.getHiddenButton().getTitle().isEmpty());
	}
	
}
