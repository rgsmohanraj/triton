*** Settings ***
Resource     ../../PageObjects/loginPage.robot
Resource     ../../PageObjects/AnchorPageObjects/07_Deferral_OpsMakerPage.robot

*** Test Cases ***
Deferral Operation Maker
    [Documentation]    Deferral operation Maker
    ${caseName}    Get Variable Value    ${anchorName}
    Set Test Variable    ${caseName}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    07_Deferral_OpsMakerPage.Deferral ops maker



