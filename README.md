<!--_
Copyright 2015 HM Revenue & Customs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
URL Builder
===========

[![Build Status](https://travis-ci.org/hmrc/url-builder.svg?branch=master)](https://travis-ci.org/hmrc/url-builder) [ ![Download](https://api.bintray.com/packages/hmrc/releases/url-builder/images/download.svg) ](https://bintray.com/hmrc/releases/url-builder/_latestVersion)

Micro-library for building URLs from templates and tags.

* Creates links with settings that are appropriate to the target. See [Link](src/main/scala/uk/gov/hmrc/urls/Link.scala)'s companion object: 
```scala
Links.toInternalPage            // No SSO, Same Window
Links.toExternalPage            // No SSO, New Window
Links.toPortalPage              // Client-based SSO, Same Window
Links.toInternalPageWithSso     // Server-based SSO, Same Window
```
* Constructs parametrized urls based on the tags and corresponding values provided.

## Usage

* **`Link`** can be used in scala html templates to generate consistent links across a page:

```scala
@import uk.gov.hmrc.urls.Link
<li>
  @{Link.toPortalPage(id = Some("link-id"), 
                      url = "http://someserver:8080/something", 
                      value = Some("Placeholder text displayed for the link")).toHtml}
</li>
```

* **`UrlBuilder`** can be used to build a URL based on parameters from a (tag, value) map:

```scala
UrlBuilder.buildUrl("http://server:8080/something/<tag1>/<tag2>",
                     Seq("<tag1>"->Some("value1"), "<tag2>"->Some("value2"))
                    )
```
would produce the url: `http://someserver:8080/something/value1/value2`


## Adding to your service

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "url-builder" % "x.x.x"
```

