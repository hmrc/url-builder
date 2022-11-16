/*
 * Copyright 2022 HM Revenue & Customs
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

import org.scalatest.GivenWhenThen
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest

class LinkSpec extends AnyFunSpecLike with GivenWhenThen with Matchers with GuiceOneServerPerSuite with I18nSupport {

  implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()

  implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

  describe("portal page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered without a value")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"client\" lang=\"en\"></a>"

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
      portalLink.toHtml.toString() shouldBe "<a id=\"link-id\" href=\"https://someurl\" target=\"_self\" data-sso=\"client\" class=\"link-style blink\" lang=\"en\">link text</a>"

    }

    it("be created with the given the value for same window target without escaping the text") {

      Given("the link value attribute as 'Pay &pound;4,000 now - it's due'")
      val value = Some("Pay &pound;4,000 now - it's due")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = value)

      Then("the link should be rendered in the same way")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_self" data-sso="client" lang="en">Pay &pound;4,000 now - it's due</a>"""

    }

    it("be created with the hidden info span when specified") {

      Given("the hiddenInfo value is 'my hiddenInfo'")
      val hiddenInfo = Some("my hiddenInfo")

      When("portal page link is created")
      val portalLink = Link.toPortalPage.apply(url = "https://someurl", value = None, hiddenInfo = hiddenInfo)

      Then("the link should have hidden span")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_self" data-sso="client" lang="en"><span class="visuallyhidden">my hiddenInfo</span></a>"""

    }
  }
  describe("internal page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("internal page link is created")
      val portalLink = Link.toInternalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in the same window")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" lang=\"en\"></a>"

    }

    it("be created with data attribute") {

      Given("the data attribute as 'data-some=test'")
      val data = Some(Map("some" -> "test"))

      When("link is created")
      val linkWithDataAttr = Link.toInternalPage.apply(url = "https://someurl", value = None, dataAttributes = data)

      Then("the link should render with data attribute")
      linkWithDataAttr.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" data-some=\"test\" lang=\"en\"></a>"
    }

    it("be created with multiple data attributes") {

      Given("the data attributes as 'data-some1=test1' and 'data-some2=test2'")
      val data = Some(Map("some1" -> "test1", "some2" -> "test2"))

      When("link is created")
      val linkWithDataAttr = Link.toInternalPage.apply(url = "https://someurl", value = None, dataAttributes = data)

      Then("the link should render with data attribute")
      linkWithDataAttr.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" data-some1=\"test1\" data-some2=\"test2\" lang=\"en\"></a>"
    }

    it("be created with lang attribute as cy for welsh") {

      Given("the link has lang attribute supplied")

      When("internal page link is created")
      val portalLink = Link.toInternalPage.apply(url = "https://someurl", value = None, lang = Cy)

      Then("the link should be rendered with lang attribute as cy")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"false\" lang=\"cy\"></a>"

    }
  }

  describe("internal page with sso link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("internal page link is created")
      val portalLink = Link.toInternalPageWithSso.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in a new window")
      portalLink.toHtml.toString() shouldBe "<a href=\"https://someurl\" target=\"_self\" data-sso=\"server\" lang=\"en\"></a>"

    }
  }
  describe("external page link should") {

    it("be created with no value for same window target") {

      Given("the link has no value attribute supplied")

      When("external page link is created")
      val portalLink = Link.toExternalPage.apply(url = "https://someurl", value = None)

      Then("the link should be rendered with no sso in a new window")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_blank" data-sso="false" rel="external noopener noreferrer" lang="en"><span class="visuallyhidden">link opens in a new window</span></a>"""

    }

    it("be created with hidden info span for screen readers") {

      Given("the link value attribute as 'Pay &pound;4,000 now - it's due'")
      val value = Some("Pay £4,000 now - it's due")

      When("external page link is created")
      val portalLink = Link.toExternalPage.apply(url = "https://someurl", value = value)

      Then("the link should be rendered with title including a new window prompt")
      portalLink.toHtml.toString() shouldBe """<a href="https://someurl" target="_blank" data-sso="false" rel="external noopener noreferrer" lang="en">Pay £4,000 now - it's due<span class="visuallyhidden">link opens in a new window</span></a>"""

    }
  }
}
