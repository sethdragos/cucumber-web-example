package com.sethdragos.bdd.steps;

import com.sethdragos.bdd.util.DateUtil;
import com.sethdragos.bdd.util.FirefoxWebDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ro.Atunci;
import cucumber.api.java.ro.Când;
import cucumber.api.java.ro.Datefiind;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;


public class ParkingCalculatorSteps {

    private static WebElement element;
    private static Select select;
    private static int numberOfDays;
    private static final String PARKING_CALC_URL = "http://adam.goucher.ca/parkcalc/";
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    @Before
    public void load_the_page() throws InterruptedException {
        FirefoxWebDriver.getDriver().get(PARKING_CALC_URL);
    }

    @Datefiind("^elementul editabil '(.+)' de pe pagină$")
    public void check_editable_page_elements_are_loaded(String element) throws Exception {
        FirefoxWebDriver.waitForElementDisplayed(FirefoxWebDriver.Locators.id, element);
        this.element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.id, element);
    }

    @Datefiind("^selectorul de parcări de pe pagină$")
    public void check_lot_select_is_loaded() throws Exception {
        FirefoxWebDriver.waitForElementDisplayed(FirefoxWebDriver.Locators.id, "Lot");
        element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.id, "Lot");
        select = new Select(element);
    }

    @Datefiind("^informaţiile introduse pentru (\\d+) zile de parcare de tip '(.+)'$")
    public void fill_in_parking_information(int numberOfDays, String parkingType) throws Exception {
        check_lot_select_is_loaded();
        select.selectByValue(parkingType);

        this.numberOfDays = numberOfDays;
        String entryDate = DateUtil.formattedDate(DATE_FORMAT);
        this.element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.id, "EntryDate");
        FirefoxWebDriver.inputText(element, entryDate);

        String leavingDate = DateUtil.formattedDate(numberOfDays, DATE_FORMAT);
        this.element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.id, "ExitDate");
        FirefoxWebDriver.inputText(element, leavingDate);
    }

    @Când("^încerc să accesez elementul$")
    public void try_to_click_on() {
        try {
            FirefoxWebDriver.click(element);
        } catch (Exception e) {
            fail();
        }
    }

    @Când("^selectez opţiunea '(.+)'$")
    public void select_option_by_visible_text(String option) {
        select.selectByVisibleText(option);
    }

    @Când("^apăs butonul 'Calculate'$")
    public void click_the_calculate_button() throws Exception {
        FirefoxWebDriver.click(FirefoxWebDriver.Locators.name, "Submit");
    }

    @Atunci("^iniţial are o valoare standard$")
    public void check_element_has_default_value() {
        assertThat(element.getText(), is(notNullValue()));
    }

    @Atunci("^pot să-i modific valoarea$")
    public void check_element_value_is_editable() {
        try {
            FirefoxWebDriver.inputText(element, "text");
        } catch (Exception e) {
            fail();
        }
    }

    @Atunci("^opţiunea selectată va avea valoarea '(.+)'$")
    public void check_select_value_matches(String expectedValue) {
        assertThat(select.getFirstSelectedOption().getAttribute("value"), is(equalTo(expectedValue)));
    }

    @Atunci("^durata afişată a parcării este corectă$")
    public void check_parking_duration_is_correct() throws Exception {
        this.element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.css, "span.BodyCopy");
        assertThat(element.getText().trim(), is(equalTo("(" + numberOfDays + " Days, 0 Hours, 0 Minutes)")));
    }

    @Atunci("^costul parcării este (.+)$")
    public void check_the_parking_cost_is(String expectedCost) throws Exception {
        this.element = FirefoxWebDriver.getWebElement(FirefoxWebDriver.Locators.css, "span.SubHead");
        assertThat(element.getText(), is(equalTo(expectedCost)));
    }

    @After
    public void close_the_page() {
        FirefoxWebDriver.close();
    }
}