/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.urls

import org.scalatest._
import org.scalatestplus.play.OneServerPerSuite
import play.api.i18n.Lang

class LinkSpec extends FunSpecLike with GivenWhenThen with Matchers with OneServerPerSuite {

  import play.api.i18n.Messages.Implicits._

  implicit val locale = Lang("en")

  describe("portal page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered without a value")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"client\"></a>"

    }

    it("be created with the given the value,id and css ") {

      Given("the link has id, css and value")
      val value = Some("link text")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl",
        value = value,
        id = Some("link-id"),
        cssClasses = Some("link-style blink"))

      Then("the link should be rendered with id and styles")
      portalLink.toHtml.toString() shouldBe "<a id=\"link-id\" href=\"https://someurl\" target=\"_self\" data-sso=\"client\" class=\"link-style blink\">link text</a>"

    }

    it("be created with the given the value for same window target without escaping the text") {

      Given("the link value attribute as 'Pay &pound;4,000 now - it's due'")
      val value = Some("Pay &pound;4,000 now - it's due")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = value)

      Then("the link should be rendered in the same way")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_self" data-sso="client">Pay &pound;4,000 now - it's due</a>"""

    }

    it("be created with the hidden info span when specified") {

      Given("the hiddenInfo value is 'my hiddenInfo'")
      val hiddenInfo = Some("my hiddenInfo")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = None, hiddenInfo = hiddenInfo)

      Then("the link should have hidden span")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_self" data-sso="client"><span class="visuallyhidden">my hiddenInfo</span></a>"""

    }
  }
  describe("internal page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("internal page link is created")
      val portalLink = Link.toInternalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in the same window")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\"></a>"

    }

    it("be created with data attribute") {

      Given("the data attribute as 'data-some=test'")
      val data = Some(Map("some" -> "test"))

      When("link is created")
      val linkWithDataAttr = Link.toInternalPage.apply(url = "https://someurl", value = None, dataAttributes = data)

      Then("the link should render with data attribute")
      linkWithDataAttr.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" data-some=\"test\"></a>"
    }

    it("be created with multiple data attributes") {

      Given("the data attributes as 'data-some1=test1' and 'data-some2=test2'")
      val data = Some(Map("some1" -> "test1", "some2" -> "test2"))

      When("link is created")
      val linkWithDataAttr = Link.toInternalPage.apply(url = "https://someurl", value = None, dataAttributes = data)

      Then("the link should render with data attribute")
      linkWithDataAttr.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" data-some1=\"test1\" data-some2=\"test2\"></a>"
    }
  }

  describe("internal page with sso link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("internal page link is created")
      val portalLink = Link.toInternalPageWithSso.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in a new window")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"server\"></a>"

    }
  }
  describe("external page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("external page link is created")
      val portalLink = Link.toExternalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in a new window")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_blank" data-sso="false" rel="external noopener noreferrer"><span class="visuallyhidden">link opens in a new window</span></a>"""

    }

    it("be created with hidden info span for screen readers") {

      Given("the link value attribute as 'Pay &pound;4,000 now - it's due'")
      val value = Some("Pay £4,000 now - it's due")

      When("external page link is created")
      val portalLink = Link.toExternalPage.apply(url = "https://someurl", value = value)

      Then("the link should be rendered with title including a new window prompt")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_blank" data-sso="false" rel="external noopener noreferrer">Pay £4,000 now - it's due<span class="visuallyhidden">link opens in a new window</span></a>"""

    }
  }
}
