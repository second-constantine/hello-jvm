package by.next.way.cucumber.kotlin

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.jupiter.api.Assertions

class StepDefs {
  private var today: String? = null
  private var actualAnswer: String? = null

  @Given("^today is \"([^\"]*)\"$")
  fun today_is(today: String) {
    this.today = today
  }

  @When("^I ask whether it's Friday yet$")
  fun i_ask_whether_it_s_Friday_yet() {
    this.actualAnswer = IsItFriday.isItFriday(today)
  }

  @Then("^I should be told \"([^\"]*)\"$")
  fun i_should_be_told(expectedAnswer: String) {
    Assertions.assertEquals(expectedAnswer, actualAnswer)
  }
}