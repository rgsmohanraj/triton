*** Settings ***
Resource    ../../keywords/variables.robot
Test Template     TC_BD_Beneficiary_DataFile_Negative_Validation  
Suite Setup        operation_maker_Authentication

*** Keywords ***
operation_maker_Authentication
    ${username}    Set Variable       operation_maker_lead
    ${password}    Set Variable       admin@1234
    Obtain_Auth_Token    ${username}    ${password}
       
*** Test Cases ***                                   bankAcctNumber    bankBranchCode    bankIfscCode       bankName     benName      benType          appId              bankbranchname        bankCode
TC_BD_09_Invalid bankbranch and Account number       34455                Koyam@!bedu    SBIN0000834        Kotak        karthi       Individual       ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_10_Invalid Ifsc code                           433263849922         Koyambedu      ^&&&&$$!@@1        Kotak        karthi       Individual       ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_11_Invalid Ifsc code and bankName              433263849911         Koyambedu      223                0            karthi       Individual       ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_12_Invalid bankName                            903263849938         Koyambedu      SBIN0000834        I!*(#)@      karthi       Individual       ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_13_Invalid App id                              999958996868         Alwarpet       SBIN0000834        Kotak        karthi       Individual       2!@                    Chennai          SBIN0000834

TC_BD_14_Invalid Ben type and missing docpath        123958996868         Alwarpet       SBIN0000834        Kotak        karthi       @###!d23         ${Anchor_App_id}       ${EMPTY}         SBIN0000834

TC_BD_15_Invalid bankAcctNumber                      45                   Porur          SBIN0000834        SBI          karthi       Individual       ${Anchor_App_id}       Chennais         SBIN0000834

TC_BD_16_Missing bankAcctNumber and Ifsc code        ${EMPTY}             Ramapuram      ${EMPTY}           HDFC         karthi       Individual       ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_17_Invalid bankBranch                          454709839938         !@@#$$%%       SBIN0000834        Equitas      karthi       Individual       ${Anchor_App_id}       Chennais         SBIN0000834

TC_BD_18_Missing bankAcctNumber and bank branch      ${EMPTY}             ${EMPTY}       SBIN0000834        Equitas      karthi       Individual       ${Anchor_App_id}       Chennais         SBIN0000834

TC_BD_19_Missing bankName and benificery Name        826363839938         Porur          SBIN0000834        ${EMPTY}     ${EMPTY}     Individual       ${Anchor_App_id}       sample           SBIN0000834

TC_BD_20_Missing bank branch and Ifsc code           826363849938         ${EMPTY}       ${EMPTY}           HDFC         karthi       Individual       ${Anchor_App_id}       sample           SBIN0000834

TC_BD_21_Negative sign in Account number             -898963809938        Porur          SBIN0000887        Equitas      karthi       Individual       ${Anchor_App_id}       Chennais         SBIN0000834

TC_BD_22_Missing benificery name                     233263849938         guindy         HDFC0000887        HDFC         ${EMPTY}     Individual       ${Anchor_App_id}       sample           SBIN0000834

TC_BD_23_Missing App id                              433263849938         guindy         HDFC0000887        HDFC         karthi       Individual       ${EMPTY}               sample           SBIN0000834
    
TC_BD_24_Missing bankbranch and benificery type      344557796868         ${EMPTY}       SBIN0000834        Kotak        karthi       ${EMPTY}         ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_25_Wrong App id                                399558996868         Alwarpet       SBIN0000834        Kotak        karthi       Individual       !Q@                    Chennai          SBIN0000834

TC_BD_26_Missing Bank Code and benificery type       344557796868         Thousand       SBIN0000834        Kotak        karthi       ${EMPTY}         ${Anchor_App_id}       Chennai          ${EMPTY}

TC_BD_27_Missing Ifsc code and benificery type       344557796870         Thousand       ${EMPTY}           Kotak        karthi       ${EMPTY}         ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_28_Missing Bank Code bankName and ben type     344557799991         Porur          SBIN0000834       ${EMPTY}      karthi       ${EMPTY}         ${Anchor_App_id}       ${EMPTY}         ${EMPTY}

TC_BD_29_Invalid Account Number and bank branch      110056000042AB       $!adyar        FDRL00011AB        HDFC         marques      Vendor           ${Anchor_App_id}         exc            SBIN0000834

TC_BD_30_Invlaid Account Number and bank Name        1234ABCD             kk nagar       SBIN01256AB        SBI          marques      Dealer           ${Anchor_App_id}         exc            SBIN0000834

TC_BD_31_Invlaid Account Number and IFSC code        abcd1234             rk nagar       1234321            ICIC         marques      Vendor           ${Anchor_App_id}       Chennai          SBIN0000834

TC_BD_32_Invalid Values for all                      1                    2              1                  2            1            1                    1                     1                 1

TC_BD_33_Missing All Values                          ${EMPTY}             ${EMPTY}       ${EMPTY}           ${EMPTY}     ${EMPTY}     ${EMPTY}         ${EMPTY}               ${EMPTY}         ${EMPTY}

TC_BD_34_Invalid Values and Missing IFSC             ABCabcqwerrts        139*abn        ${EMPTY}           )!@*#        @sjnsn&      *#uwn*            *#uwn*                   @#$             *#uwn*

TC_BD_35_Zero Value for all                          0                    0              0                  0            0            0                0                         0                0

TC_BD_36_Symbols for all values                      &!@@##               &!@@##         !@@@##             &!@@##       &!@@##       &!@@##           &!@@##                  &!@@##           &!@@##

TC_BD_37_Missing Values and Incorrect Acocuntnum     2312233445859595     ${EMPTY}       ${EMPTY}           ${EMPTY}     ${EMPTY}     ${EMPTY}         ${EMPTY}               ${EMPTY}          ${EMPTY}

TC_BD_38_Incorrect Values for all                    23.235               1.2345         22.22              12.134       9.0          9.12             12.37                    9.2            12.98

TC_BD_39_Incorrect and space for fields              1/238440404091929    12/techg       23/1223            Fed ral      new jr       ven dor          12 &                     dual           23/1223


