package org.mule.module.google.spreadsheet.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.spreadsheet.model.Spreadsheet;

public class CreateSpreadsheetTestCases extends GoogleSpreadsheetsTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("createSpreadsheet");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testCreateSpreadsheet() {
		try {
			String title = (String) testObjects.get("title");
			
			MessageProcessor flow = lookupFlowConstruct("create-spreadsheet");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Spreadsheet> spreadsheets = getAllSpreadsheets();

			boolean found = false;
			for (Spreadsheet spreadsheet : spreadsheets) {
				if (spreadsheet.getTitle().equals(title)) {
					found = true;
				}
			}
			assertTrue(found);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
		
}
