*** Settings ***
Resource    ../resources/imports.robot
Resource    ../keywords/variables.robot

*** Keywords ***
Select the case in Inbox
     Wait Until Element Is Visible   xpath://td[contains(text(),'${caseName}')]    timeout=25s
    # Wait Until Element Is Visible   xpath://*[text()='Work In Progress']//parent::tr//td[text()='${caseName}']    timeout=15s
    ${scrollInbox}=    Run Keyword And Return Status    Click Element   xpath://td[contains(text(),'${caseName}')]
    Run Keyword If    '${scrollInbox}' == 'False'        Execute JavaScript    var xpath = "//td[contains(text(),'${caseName}')]"; var element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;element.scrollIntoView(false);
    Sleep    2
    Run Keyword If    '${scrollInbox}' == 'False'        Set Focus to Element     xpath://td[contains(text(),'${caseName}')]
    Run Keyword If    '${scrollInbox}' == 'False'        Click Element            xpath://td[contains(text(),'${caseName}')]

