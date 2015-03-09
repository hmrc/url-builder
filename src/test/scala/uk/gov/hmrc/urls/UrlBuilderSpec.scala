/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.urls

import org.scalatest._

class UrlBuilderSpec extends FunSpecLike with Matchers with OptionValues with GivenWhenThen {

  describe("build url processes a URL template") {

    it("should replace multiple different tokens") {

      Given("The url template http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace2>")
      val urlToReplaceWithValues = "http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace2>"

      And("Some matching tokens")
      val sequenceToReplace = Seq("<keyToReplace1>" -> Some("value1"), "<keyToReplace2>" -> Some("value2"))

      When("The URL is built")
      val builtUrl = UrlBuilder.buildUrl(urlToReplaceWithValues, sequenceToReplace)

      Then("The tokens have been replaced")
      builtUrl shouldBe "http://someserver:8080/something1/value1/something2/value2"
    }


    it("should replace Some token and not None") {

      Given("The url template http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace2>")
      val urlToReplaceWithValues = "http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace2>"

      And("Some matching token")
      val sequenceToReplace = Seq("<keyToReplace1>" -> Some("value1"), "<keyToReplace2>" -> None)

      When("The URL is built")
      val builtUrl = UrlBuilder.buildUrl(urlToReplaceWithValues, sequenceToReplace)

      Then("The matching token has been replaced")
      builtUrl shouldBe "http://someserver:8080/something1/value1/something2/<keyToReplace2>"
    }

    it("should replace the same token multiple times") {

      Given("The url template http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace1>")
      val urlToReplaceWithValues = "http://someserver:8080/something1/<keyToReplace1>/something2/<keyToReplace1>"

      And("Some matching token")
      val sequenceToReplace = Seq("<keyToReplace1>" -> Some("value1"))

      When("The URL is built")
      val builtUrl = UrlBuilder.buildUrl(urlToReplaceWithValues, sequenceToReplace)

      Then("The matching token has been replace twice")
      builtUrl shouldBe "http://someserver:8080/something1/value1/something2/value1"
    }
  }
}
