# execute a tag mvn test -Dcucumber.filter.tags="@tagx"
Feature: load data

  @javaLdRnEnt
  Scenario Outline: load of ldRnEnt using java only <name> <domain> <project>
    Given load of LdRnEnt <name> <domain> <project>
    Then LdRnEnt in db no arguments <name> <domain> <project>
    
    Examples:
    | name | domain | project |
    | "n1" | "d1"   | "p1"    |
    | "n2" | "d2"   | "p2"    |
    
  @javaOtherRepo
  Scenario Outline: load of OtherRepo using java only <name> <domain> <url>
    Given load of OtherRepo <name> <domain> <url>
    Then OtherRepo in db no arguments <name> <domain> <url>
    
    Examples:
    | name | domain | url             |
    | "n1" | "d1"   | "http://x.com"  |
    | "n2" | "d2"   | "http://y.net"  |
  
  @jsonLdRnEnt01
  Scenario Outline: load of ldRnEnt using JSON <name> <domain> <project>
    Given load of LdRnEnt using JSON <name> <domain> <project>
    Then LdRnEnt in db no arguments using JSON <name> <domain> <project>
    
    Examples:
    | name     | domain     | project     |
    | "n1Json" | "d1Json"   | "p1Json"    |
    | "n2Json" | "d2Json"   | "p2Json"    |
  
  @jsonOtherRepo01
  Scenario Outline: load of OtherRepo using JSON <name> <domain> <url>
    Given load of OtherRepo using JSON <name> <domain> <url>
    Then OtherRepo in db no arguments using JSON <name> <domain> <url>
    And OtherRepo by Test Suite key JSON <name> <domain> <url>
    
    Examples:
    | name               | domain             | url                               |
    | "jsonOtherRepo01a" | "jsonOtherRepo01a" | "http://jsonOtherRepo01b.com"     |
    | "jsonOtherRepo01b" | "jsonOtherRepo01b" | "http://jsonOtherRepo01b.com"     |
    