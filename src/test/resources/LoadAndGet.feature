# execute a tag mvn test -Dcucumber.filter.tags="@tagx"
Feature: load data

  @javaLdRnEnt
  Scenario Outline: load of ldRnEnt using java only <name> <domain> <project>
    Given load of LdRnEnt <name> <app> <description> <active> <starter> <domain> <project>
    Then LdRnEnt in db no arguments <name> <app> <description> <active> <starter> <domain> <project>
    
    Examples:
    | name | app      | description             | active     | starter |  domain | project |
    | "n1" | "appx"   | "my description"        | true       | "a.bat" |  "d1"   | "p1"    |
    | "n2" | "appy"   | "my description"        | false      | "b.bat" |  "d1"   | "p2"    |
    | "n3" | "appy"   | "my description"        | true       | "c.bat" |  "d1"   | "p2"    |
    
  @javaOtherRepo
  Scenario Outline: load of OtherRepo using java only <name> <app> <description> <active> <starter> <domain> <url>
    Given load of OtherRepo <name> <app> <description> <active> <starter> <domain> <url>
    Then OtherRepo in db no arguments <name> <app> <description> <active> <starter> <domain> <url>
    
    Examples:
    | name | app      | description        | active | starter | domain   | url                |
    | "n1" | "appy"   | "my description"   | true   | "a.bat" | "d1"     | "http://x.com"     |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/1"   |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/2"   |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/3"   |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/3"   |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/3"   |
    | "n2" | "appz"   | "my description"   | true   | "b.bat" | "d3"     | "http://y.net/4"   |
  
  @jsonLdRnEnt01
  Scenario Outline: load of ldRnEnt using JSON <name> <domain> <project>
    Given load of LdRnEnt using JSON <name> <domain> <project>
    Then LdRnEnt in db no arguments using JSON <name> <domain> <project>
    
    Examples:
    | name     | domain     | project     |
    | "n1Json" | "d1Json"   | "p1Json"    |
    | "n2Json" | "d2Json"   | "p2Json"    |
  
  @jsonOtherRepo01
  Scenario Outline: load of OtherRepo using JSON <name> <app> <description> <active> <starter> <domain> <url>
    Given load of OtherRepo using JSON <name> <app> <description> <active> <starter> <domain> <url>
    Then OtherRepo in db no arguments using JSON <name> <app> <description> <active> <starter> <domain> <url>
    And OtherRepo by Test Suite key JSON <name> <app> <description> <active> <starter> <domain> <url>
    
    Examples:
    | name               | domain             | url                               |
    | "jsonOtherRepo01a" | "jsonOtherRepo01a" | "http://jsonOtherRepo01b.com"     |
    | "jsonOtherRepo01b" | "jsonOtherRepo01b" | "http://jsonOtherRepo01b.com"     |
    