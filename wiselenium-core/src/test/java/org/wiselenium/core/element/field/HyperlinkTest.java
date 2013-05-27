package org.wiselenium.core.element.field;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.wiselenium.core.element.field.HyperlinkPage.HYPERLINK_CLICKED_MESSAGE;
import static org.wiselenium.core.element.field.HyperlinkPage.HYPERLINK_HREF;
import static org.wiselenium.core.element.field.HyperlinkPage.HYPERLINK_TARGET;
import static org.wiselenium.core.element.field.HyperlinkPage.HYPERLINK_TEXT;
import static org.wiselenium.core.element.field.HyperlinkPage.URL;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.wiselenium.core.test.WiseTestNG;

@SuppressWarnings("javadoc")
public class HyperlinkTest extends WiseTestNG<HyperlinkTest> {
	
	HyperlinkPage page;
	
	
	@BeforeMethod
	public void initPage() {
		this.page = this.initElements(HyperlinkPage.class);
		this.page.get();
	}
	
	@Test
	public void shouldClick() {
		this.page.getHyperlink().click();
		assertEquals(this.page.getMessage(), HYPERLINK_CLICKED_MESSAGE);
	}
	
	@Test
	public void shouldGetHref() {
		assertTrue(this.page.getHyperlink().getHref().endsWith(URL + HYPERLINK_HREF));
	}
	
	@Test
	public void shouldGetTarget() {
		assertEquals(this.page.getHyperlink().getTarget(), HYPERLINK_TARGET);
	}
	
	@Test
	public void shouldGetText() {
		assertEquals(this.page.getHyperlink().getText(), HYPERLINK_TEXT);
	}
	
}
