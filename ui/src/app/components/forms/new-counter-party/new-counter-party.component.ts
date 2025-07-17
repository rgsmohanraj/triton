    import { Component, OnInit ,ViewChild, OnDestroy } from '@angular/core';
    import { FormGroup, FormBuilder, Validators } from '@angular/forms';
    import { ToastrService } from 'ngx-toastr';
    import { MustMatch } from '../../../shared/validators/passwordMatch';
    import * as accordion from '../../../shared/data/faq/accordion'
    import { ApiRequestService } from 'src/app/shared/services/api-request.service';
    import { NgbDateStruct, NgbDate, NgbCalendar, NgbDatepickerConfig} from '@ng-bootstrap/ng-bootstrap';
    import { environment } from '../../../../environments/environment';
    import { Router } from '@angular/router';
    import { CommonModule,DatePipe } from '@angular/common';
    import { Observable } from 'rxjs/internal/Observable';
    import { of } from 'rxjs/internal/observable/of';
    import $ from "jquery";
    import { AuthService } from 'src/app/shared/services/firebase/auth.service';

    declare var require
    const Swal = require('sweetalert2')
    import {WizardComponent, ConfigurableNavigationMode} from 'angular-archwizard';
import { Subscription } from 'rxjs';


    @Component({
      selector: 'app-new-counter-party',
      templateUrl: './new-counter-party.component.html',
      styleUrls: ['./new-counter-party.component.scss'],

    })
    export class NewCounterPartyComponent implements OnInit,OnDestroy {
    historyData:any;
     viewData:[]=[];

deferralStatus:any;
     @ViewChild(WizardComponent)

     public wizard: WizardComponent;
    options$: Observable<string[]>;
    proposalList:[]=[];
    debtProfileList:[]=[];
    limitEligibilityList:[]=[];

    public isFirstActive = false;
    public isSecActive = false;
    public isThirdActive = false;
    public isFourthActive = false;

    public validate = false;
    public cpBasicValidate = false;
    public softPolicyValidation  = false;
    public deDupeEnable = false;
    public updateBasicCpDetails=true;
    public proposalValidate = false;
    public goToRisk = false;
    //Fund Requirement
    public saveFundReqFlag = false;
    public fundRequirementControl= false;
    public limitValidate = false;
    public termSheetValidate = false;
    public dueDiligenceValidate = false;
    public collateralValidate = false;
    public debtProfileValidate = false;
    public saveSoftPolicyFlag = true;
    public updateSoftPolicyFlag = false;
    public updateCreditPolicyFlag = false;
    public popUpSoftPolicy = false;
    public saveCreditPolicyFlag = true;
    public creditCheck = false;
    public creditPolicyView = false;
    public cPBasicFlag1 = true;
    public cPBasicFlag2 = false;

    public cpProposed1 = true;
    public cpProposed2 = false;
//     public DebtProfile1 = true;
    public DebtProfile2 = false;
    public cpLimitEligibility1 = true;
    public cpLimitEligibility2 = false;
    public cpTermSheet1 = true;
    public cpTermSheet2 = false;
    public cpCollateral1 = true;
    public cpCollateral2 = false;
    public dueDiligence1 = true;
    public dueDiligence2 = false;
    public uploadDataLoading = false;
    public camLoading = false;
    public opsMakerLoading = false;
    public commercial1 = true;
    public commercial2 = false;

    public commercialCC = false;
    public creditCC = false;

    public enableUploadDataKyc = false;
    public enableUploadDataCredit = false;

    public assessmentTypeFlag = false;
    public uploadDataEdit = false;
    public camUploadEdit = false;
    public opsMakerEdit = false;
    public beneficiaryValidate = false;
    public editBeneficiaryIcon = false;
    public editBeneficiary = false;
    public beneficiary1 = true;
    public beneficiary2 = false;

    public assignUwFlag1=true;
    public assignUwFlag2=true;
    public assignUwFlag3= true;


    public softPolicySave = true;
    public softPolicyUpdate = false;

    //Document Validation
    public docValidationCheck = false;
    public deferralCheck = false;
    public docProductCheck = false;
    public deferralDocView = false;
    public otherDocLabelView = false;
    public otherDocDefLabelView = false;
    public loading = false;

    //Application ID
    cpId:any;
    cusId:any;
    customerName1:any;
    cpPan:any;
    cpCin:any;



    //Bank List
    bankList:any;branchList:any;storeProcedureFlag:any;applyOfInterest:any;
    assignUnderwriterList:any;

    //Beneficiary Details
    bankDetails:any;
    bankBranchDetails:any;
    benPkId:any;beneficiaryType:any;beneficiaryName:any;bankName:any;bankCode:any;bankAccountNumber:any;bankIfscCode:any;bankBranchName:any;bankBranchCode:any;

    //Proposed Table
    anchorType:any;
    anchorProduct:any;
    anchorStatus:any;

    assessmentKycFlag:any;
    leadGrpName:any;
    cpCity1:any;
    panNumber3:any;
    cpState1:any;
    companyName1:any;
    custContactMobile1:any;
    custContactName1:any;
    pan:any;name:any;constitution:any;state:any;city:any;source:any;subSource:any;rmName:any;customerName:any;customerMob:any;customerEmail:any;cpPkId:any;
    cpAppId:any;cpCustId:any;firstName:any;lastName:any;file:any;panNumber2:any;cinNumber2:any;companyName:any;cpCIty:any;cpState:any;cpSubSource:any;custContactName:any;
    custContactMobile:any;custContactEmail:any;nameofAnchor:any;product:any;ProposedAmount:any;processingfee:any;interestRate:number;assignedTo:any;
    remark1:any;gstNumber1:any;cinNumber:any; value:any;programNormsProduct:any;
    currentLesson:string;

    customerInfostatus:any;
//     custFacilityTenure:any;

    assignTo:any;assignUwTo:any;

    bureauScore:any;smaA:any;smaB:any;smaC:any;bureauScore2:any;currentOD:any;currentODForCred:any;EfoInMonths:any;EntityPanGST:any;PanAadhaar:any;
    stateinservie:any;name1:any;vcplAtten:any;marketInfo:any;marketFeed:any;businessPlans:any;stockObservation:any;businessModel:any;observation:any;
    promoter:any;address:any;newType:any;proposed:any;anchorId:any;propoType:any;

    bankFI:any;facilityType:any;tenure:any;dateSanction:any;limitSanctioned:any;OSasOn:any;emi:any;interest:any;security:any;
    generalOrSpecificLimitCalculation:any;remarksDebt:any;LimitProduct:any;currentLimit:any;proposedLimit:any;eligibleLimit:any;adhocLimit:any;
    creditPeriod:any;doorToDoor:any;invoiceAgeing:any;Margin:any;expectedGrowthInTo:any;monthlyAverageToWithAnchor:any;calculatedLimit:any;
    approtionedExisitingWCLimits:any;exisitingSCFLimitFromOthers:any;modelLimit:any;customerRequestedAmount:any;anchorRecomendedAmount:any;

    limitAnchorName:any;limitEligibilitytProduct:any;LimitadhocLimit:any;regularLimit:any;LimitcreditPeriod:any;limitDoorToDoor:any;limitInvoice:any;
    limitMargin:any;margin:any;pf:any;renewal:any;interestRateType:any;renewalPeriod:any;limitInterest:any;limitPf:any;limitRenewal:any;limitInterestType:any;modelFooter:any;footerTemplate:any;vcplAttendees:any;
    addressvisited:any;facilityObservation:any;modelBusiness:any;stockObservation1:any;businessPlans1:any;marketInformation:any;marketFeedbacks:any;condition:any;
    personName:any;personNumber:any;cpType:any;

    fundReqValue:any;fundReqId:any;enableFundReq:any;
    answer:any;questionId:any;flag:any;activity:any;assessmentType:any;anchorRelationShip:any;totalInwardCheques:any;

    uploadDataSubName:any;UploadDataDocument:File;uploadDataDocEnable:any;
    camSubName:any;camDocument:File;camDocEnable:any;
    opsMakerSubName:any;opsMakerDocument:File;opsMakerDocEnable:any;

    camFileName:any;bool1:any;

    uploadDataFlag:any;camFlag:any;opsMakerFlag:any;

    applicationInterest:any;  interestOwnerShip:any; fundingPercent:any; graceDays:any;

    file_name:any;bool:any;document:File;documentTypeUpload:any;documentTypeNameUpload:any;documentSubTypeUpload:any;

    assignUwPkId:any;assignUwAppId:any;

//Deferral Workflow

otherDefDocRes:any;

    renewalPeriodRemarks:any;interestTypeRemarks:any;renewalRemarks:any;pfRemarks:any;interestRemarks:any;marginRemarks:any;invoiceRemarks:any;DoorRemarks:any;creditPeriodRemarks:any;adhocRemarks:any;regularLimitRemarks:any;
    commercialCcPkId:any;commercialCcAppId:any;nameRemarks:any;productRemarks:any;condition1:any;condition2:any;condition3:any;condition4:any;condition5:any;condition6:any;condition7:any;condition8:any;condition9:any;
    condition10:any;condition11:any;condition12:any;condition13:any;condition14:any;condition15:any;condition16:any;condition17:any;condition18:any;condition19:any;

    relationshipwithAnchor:any;achorDependenceRs:any;achorDependenceP:any;businessVintage:any;statutoryDuesDelay:any;commercialBureau:any;bankingNorm:any;
    positiveATNW:any;Profit:any;salesGrowth:any;currentRatio:any;TOLATNE:any;workingCapitalCycle:any;hygieneCheck:any;ageOfKeyPersion:any;isThisRelatedPartyToAnchor:any;

    clientList:any;emailId:any;uploadId:any;stageId:any;wfStatus:any;remarkWf:any;
    eve:any;eveAnchor:any;eveRegular:any;eveAdhoc:any;eveCredit:any;eveDoor:any;eveInvoice:any;eveMargin:any;eveInterest:any;evePf:any;eveRenewal:any;eveInterestType:any;eveRenewalPeriod:any;
    checkProd:any;
    dueDiligenceId:any;dueDiligenceValue:any;enableDueDiligence:any;dueDiligenceDataType:any;
    collateralId:any;collateralValue:any;cEnable:any;enableCollateral:any;
    ind:any;event:any;commercialName:any;condInd:any;condCommercialName:any;condEvent:any; remar:any;

    softPolicyId:any;softPolicyValue:any;softPolicyflag:any;softPolicyValidate:any;enableSoftPolicy:any;
    creditPolicyId:any;creditPolicyValue:any;creditPolicyflag:any;creditPolicyValidate:any;enableCreditPolicy:any;

    geoRestriction:any;cinNumber23:any;

    minDate:any; maxDate:any;
    docRMName:any;rmNames:any;
    public deDupeNp = true;public proposalNp = false;public uploadDataNp = false;
    public cPDetailsNp = true;public assignUnderwriterNp = false;
    public creditCpaCPDetailsNP = true; public proposaDetailsNp =false;public docChecklistNp = false;public softPolicyNp = false;
    public camCPDetailsNp = true;public camOverrideSoftPolicyNp = false;public camDebtProfileNp = false; public CAMUploadNp = false;
    public uwrCPDetailsNp = true;public uwrViewCamNp = false;public uwrLimitEligibilityNp = false;public uwrTermSheetNp = false;public uwrCollateralNp = false;public uwrCreditPolicyNp = false;
    public uwPdRCpDetailsNp = true;public uwPdRDueDiligenceNp = false;
    public commercialCreditCpDetailsNp = true;public commercialCreditNp=false;public commercialCCApprovalNp = false;public creditCCApprovalNp = false;
    public rmCustCpDetailsNp = true;public rmCustCommercialCCNp = false;public creditCpaRemarks = false;
    public OpsMakerCpDetailsNp = true;public OpsMakerDocUploadNp = false;
    public OpsMakerBeneficiaryNp = false;public assignUnderwriterRemarks = false;public businessRemarks = false;
    public OpsCheckerCpDetailsNp = true;public OpsCheckerDocDownloadNp = false; public overridePolicyCPDetailsNp = true;public overridePolicy = false; public overrideSoftPolicyRemarks = false;
    public assignUnder1 = true;public uwPdRemarks = false;public CommercialCreditRemarks = false;public OpsMakerRemarksNp = false;
    public assignUnder2 = false;public uwrRemarks = false;public CAMRemarks = false; public rmCustRemarksCCNp=false;public OpsCheckerRemarksNp =false;public workFlowRemarks=false;

    public CinNumbermad = false;public PanNumber = false;public ConstitutionFlag = false; public approveHide=false;public approveDisable1 = true;public waiveOffDisable1 = false;public approveDisable3 = false;public waiveOffDisable3 = true; public defStatus = false; public OtherDoc = false;
    //Deferral
    public approveDisableOther = false; public waiveOffDisableOther = false; public approveDisable1Other = true; public waiveOffDisable1Other = false; public approveDisable3Other = false; public waiveOffDisable3Other = true;

public deferrDoc = false;


    //Document Validation
     docValidationDataArray: Array<any> = [];
     deferralDocumentArray: Array<any> = [];
     deferralDocReportArray: Array<any> = [];
     rmNameArray: Array<any> = [];

    documentMaster: Array<any> = [];

    // Proposal List
    proposalArray: Array<any> = [];
    newProposalAttribute: any = {};
    addProposalValue() {
        if(this.proposalArray.length <= 19){
            this.newProposalAttribute = { appId : this.cpId };
            this.proposalArray.push(this.newProposalAttribute)
            this.proposalValidate = false;
            this.cpProposed1 = true;
        }else{
            this.toaster.error("Maximum allowed proposal details reached...");
        }
    }
    deleteProposalValue(id,index) {
        if(this.proposalArray.length > 1){
            if(id != undefined){
                this.deleteProposedProduct(index,id,true);
            }else{
                this.proposalArray.splice(index, 1);
                var proposalFlag = true;
                for(let proposals of this.proposalArray){
                    if(proposals.custId=="" || proposals.custId==null){
                        this.cpProposed1 = true;
                        proposalFlag = false;
                        break;
                    }
                    if(proposals.product=="" || proposals.product==null){
                        this.cpProposed1 = true;
                        proposalFlag = false;
                        break;
                    }
                    if(proposals.type=="" || proposals.type==null){
                        this.cpProposed1 = true;
                        proposalFlag = false;
                        break;
                    }
                    if(proposals.proposed=="" || proposals.proposed==null){
                        this.cpProposed1 = true;
                        proposalFlag = false;
                        break;
                    }
                }
                if(proposalFlag && this.cpProposed2){
                    this.cpProposed1 = false;
                }
            }
        }else{
            if(id != undefined){
                this.deleteProposedProduct(index,id,false);
            }else{
                this.toaster.error("Minimum of one proposal detail is required...");
            }
        }
    }

    //Fund Requirement
    fundReqArray: Array<any> = [];
    fundQuestion: Array<any> = [];


    // Limit Eligibility
    limitEligibilityArray: Array<any> = [];
    newLimitAttribute: any = {};
    addLimitEligibleValue() {
      this.limitEligibilityArray.push(this.newLimitAttribute)
      this.newLimitAttribute = {};
    }
    deleteLimitEligibleValue(index) {
      this.limitEligibilityArray.splice(index, 1);
    }

    addTermSheetValue() {
    //     this.getProgramNormsByProduct();
          this.termSheetArray.push(this.newTermSheetAttribute)
          this.newTermSheetAttribute = {};
        }
        deleteTermSheetValue(index) {
          this.termSheetArray.splice(index, 1);
        }


        //Commercial Cc
        addCommercialCcValue(){
        this.commercialArray.push(this.newCommercialAttribute)
                  this.newCommercialAttribute = {};
                }
                deleteCommercialValue(index) {
                  this.commercialArray.splice(index, 1);
                }


    //Debt Profile
    debtProfileArray: Array<any> = [];
    newDebtAttribute: any = {};
    addDebtProfileValue() {
        if(this.debtProfileArray.length <= 19){
            this.debtProfileArray.push(this.newDebtAttribute)
            this.newDebtAttribute = {};
            this.debtProfileValidate = false;
//             this.DebtProfile1 = true;
        }else{
            this.toaster.error("Maximum allowed debt profile details reached...");
        }
    }
    deleteDebtProfileValue(id,index) {
        if(this.debtProfileArray.length > 1){
            if(id != undefined){
                this.deleteDebtProfile(index,id,true);
            }else{
                this.debtProfileArray.splice(index, 1);
            }
        }else{
            if(id != undefined){
                this.deleteDebtProfile(index,id,false);
            }else{
                this.toaster.error("Deleting the debt profile structure is not allowed...");
            }
        }

    }

    //Term Sheet
    termSheetArray: Array<any> = [];
    newTermSheetAttribute: any = {};

//Commercial
     commercialArray: Array<any> = [];
     newCommercialAttribute: any = {};


    // Due Diligence
    dueDiligenceList: Array<any> = [];
    dueDiligenceArray: Array<any> = [];

    //Collateral
    collateralList: Array<any> = [];
    collateralArray: Array<any> = [];

    //Credit Norms
    creditNormDetails: Array<any> = [];

    //Commercial CC Remarks
    commercialRemarks: Array<any> = [];

    //Soft policy Master
    SoftPolicyMasterList: Array<any> = [];

    //Soft policy Details
    softPolicyResult:Array<any> = [];
    softPolicyDealerResult:Array<any> = [];
    softPolicyVendorResult:Array<any> = [];
    softPolicyArray: Array<any> = [];

    //credit policy Details
    creditPolicyMasterList: Array<any> = [];
    creditPolicyArray: Array<any> = [];
    creditPolicyFilterList:Array<any>=[];
    creditPolicyResults:Array<any> = [];

    //Document mandatory
    anchorOpsMakerMandatory :Array<any> = [];
    cpUploadDataMandatory: Array<any> = [];
    cpCamMandatory: Array<any> = [];
    cpOpsMakerMandatory: Array<any> = [];

    //File uploaded names
    anchorOpsMakerName: Array<any> = [];
    cpUploadDataName: Array<any> = [];
    cpCamName: Array<any> = [];
    cpOpsMakerName: Array<any> = [];

    //uploaded Mandatory
    cpUploadDataUploaded: Array<any> = [];
    cpCamUploaded: Array<any> = [];
    cpOpsMakerUploaded: Array<any> = [];

    //Proposed product
    ProductList:Array<any> = [];
    customerArray:Array<any> =[];

    //Other deferral
    OtherDeferralDocArray: Array<any> = [];
    OtherDocReports: Array<any> = [];

    public accordion2 = accordion.accordion2;
    private subscription: Subscription;

    constructor(public authService: AuthService,private requestapi:ApiRequestService,
        private _formBuilder: FormBuilder,
        private toaster: ToastrService,public router: Router,public datePipe:DatePipe
    ) {
    this.subscription = this.requestapi.getData1().subscribe(data => {
        if(data){
            this.historyData = data.id;
            this.deferralStatus = data.status;
        }
    });

      const currentDate = new Date();
      var min = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 2);
      var max = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate() + 32);
      this.minDate = min.toISOString().split('T')[0];
      this.maxDate = max.toISOString().split('T')[0];

    this.options$=of(["Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","chhatisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttarakhand","Uttar Pradesh","West Bengal"]);

    }

    anchorList: Array<any> = [];
    ProposalList:[]=[];
    creditProposalList: Array<any> = [];
    customerInfoCusId:[]=[];
    docSubMainName:[]=[];
    docExtension=["pdf", "xls", "xlsx", "doc", "docx", "txt", "png", "jpg", "jpeg","zip"];
    docReports:Array<any> = [];
    creditPolicyArrayss:Array<any> = [];
    creditPolicyCheckResults:any;
    fundReqDetails:[]=[];
    dueDiligenceValues:[]=[];
    cpCreditPolicyList:[]=[];
    clientDeDupeList:[]=[];
    proposedProductList:[]=[];
    customerDeDupeList:[]=[];
    customerDetails:[]=[];
    collateralDetailsList:[]=[];
    collateralValueList:[]=[];
    termSheetList:[]=[];
    counterPartyList:[]=[];
    commercialCcList:[]=[];
    UnderWriterList:Array<any>=[];
    cpBeneficiaryList:[]=[];
    fileUploadAnchorBeneficiaryList:[]=[];
    assignUwList:any;
    remarkArray :[] = [];
    newOtherDeferralAttribute:any ={};

    classes = [
    {
      name: 'Lesson1',
      level: 1,
      code: 1,
    }
    ]
    firstComplete:any;
    public firstWip = 'allow';
    firstReturn:any;
    secondComplete:any;
    secondWip:any;
    secondReturn:any;
    thirdComplete:any;
    thirdWip:any;
    thirdReturn:any;
    fourthComplete:any;
    customerCin:any;
    fourthWip:any;
    fourthReturn:any;
    fifthComplete:any;
    fifthWip:any;
    fifthReturn:any;
    sixComplete:any;
    sixWip:any;
    sixReturn:any;
    sevenComplete:any;
    sevenWip:any;
    sevenReturn:any;
    eightComplete:any;
    eightWip:any;
    customerStatus:any;
    eightReturn:any;
    nineComplete:any;
    nineWip:any;
    nineReturn:any;
    tenComplete:any;
    tenWip:any;
    tenReturn:any;
    elevenComplete:any;
    elevenWip:any;
    elevenReturn:any;
    twelveComplete:any;
    twelveWip:any;
    twelveReturn:any;

    mailId:any;
    softFlag:any;

    customerStatus1:any;



    cp1View=false;
    cp1Submit=false;
    cp1Return= false;
    cp1Approve= false;
    cp1Reject= false;
    cp1Edit=false;

    cp2View=false;
    cp2Submit=false;
    cp2Return= false;
    cp2Approve= false;
    cp2Reject= false;
    cp2Edit=false;


    cp3View=false;
    cp3Submit=false;
    cp3Return= false;
    cp3Approve= false;
    cp3Reject= false;
    cp3Edit=false;

    cp4View=false;
    cp4Submit=false;
    cp4Return= false;
    cp4Approve= false;
    cp4Reject= false;
    cp4Edit=false;

    cp5View=false;
    cp5Submit=false;
    cp5Return= false;
    cp5Approve= false;
    cp5Reject= false;
    cp5Edit=false;



    cp6View=false;
    cp6Submit=false;
    cp6Return= false;
    cp6Approve= false;
    cp6Reject= false;
    cp6Edit=false;

    cp7View=false;
    cp7Submit=false;
    cp7Return= false;
    cp7Approve= false;
    cp7Reject= false;
    cp7Edit=false;

    cp8View=false;
    cp8Submit=false;
    cp8Return= false;
    cp8Approve= false;
    cp8Reject= false;
    cp8Edit=false;

    cp9View=false;
    cp9Submit=false;
    cp9Return= false;
    cp9Approve= false;
    cp9Reject= false;
    cp9Edit=false;

    cp10View=false;
    cp10Submit=false;
    cp10Return= false;
    cp10Approve= false;
    cp10Reject= false;
    cp10Edit=false;

    cp11View=false;
    cp11Submit=false;
    cp11Return= false;
    cp11Approve= false;
    cp11Reject= false;
    cp11Edit=false;

    cp12View=false;
    cp12Submit=false;
    cp12Return= false;
    cp12Approve= false;
    cp12Reject= false;
    cp12Edit=false;

    refresh_token:any;
//deferral
public deferralBasicDetails = true;
public deferralApproval = false;
nextBasicDeferral(){
          this.deferralApproval = true;
          this.deferralBasicDetails = false;
}
deferralPrevious(){
            this.deferralApproval = false;
            this.deferralBasicDetails = true;
  }
    //workflow
    keyPersonArray: Array<any> = [];
    limitEligibilityMultipleArray:Array<any>=[];
    termSheetMultipleArray:Array<any>=[];
    DeferralDocArray: Array<any> = [];
    AuthorisedArray :Array<any> = [];
    newKeyPersonAttribute: any = {};
    newAuthorisedAttribute : any = {};
    newAnchorProgramNorms :any = {};
    newDeferralAttribute:any ={};
    DocListEntity:any;
    status:any;
    documentName:any;
    addDeferralDocArray(){
    this.DeferralDocArray.push(this.newDeferralAttribute)
    this.newDeferralAttribute = {};
    }
    addKeyPersonValue() {
      this.keyPersonArray.push(this.newKeyPersonAttribute)
      this.newKeyPersonAttribute = {};
    }

    addOtherDeferralDocArray(){
     this.OtherDeferralDocArray.push(this.newOtherDeferralAttribute)
     this.newOtherDeferralAttribute = {};
     }
     deleteOtherDeferralDocArray(index){
     this.OtherDeferralDocArray.splice(index,1);
     }

     //Others Document
         otherDocumentArray: Array<any> = [];
         newOtherDocumentAttribute: any = {};
         addOtherDocument(docListId,deferral) {
           this.newOtherDocumentAttribute ={
             appId : this.cpId,
             status : -1,
             docListId : docListId,
             deferral : deferral
           };
           this.otherDocumentArray.push(this.newOtherDocumentAttribute)
           console.log("otherDocumentArray",this.otherDocumentArray);
         }
         deleteOtherDocument(index,id) {
            if(id != undefined){
                this.deleteOtherDocRecord(index,id);
            }else{
                this.otherDocumentArray.splice(index, 1);
            }
           console.log("otherDocumentArray",this.otherDocumentArray);
         }

    ngOnInit(): void {
        console.log("this.historyData",this.historyData);
    this.getRMNames();
            this.proposalArray.push(this.newProposalAttribute)
            this.newProposalAttribute = {};
//             this.limitEligibilityArray.push(this.newLimitAttribute)
//             this.newLimitAttribute = {};
            this.debtProfileArray.push(this.newDebtAttribute)
            this.newDebtAttribute = {};
//             this.termSheetArray.push(this.newTermSheetAttribute)
//             this.newTermSheetAttribute = {};

//             this.commercialArray.push(this.newCommercialAttribute)
//             this.newCommercialAttribute = {};


        this.getCollateral();
        this.roleBasedFun();
        this.getBankDetails();
        this.getUnderWriterDetails();
        this.getFundRequirementQuestion();
        this.getDocumentType();
        this.getCustomerInfoDetails();

        this.emailId=localStorage.getItem('email')
        if(this.emailId==null){
            localStorage.clear();
            this.router.navigate(['/auth/login']);
        }
          if(this.historyData){
            this.currentStepperFun(this.historyData);
            this.cpId=this.historyData;
        }


        this.getPending();
        this.getAnchorBeneficiaryById();
        this.getProgramNormsByProduct();
        this.getCpTermSheetById('1');
        this.getLimitEligibilityById();
//         this.getDebtProfileById();
        this.getCounterPartyById();
//         this.getProposedProductDetails(true);
        this.getCollateralById();
        this.getFundRequirement();
        this.getMultipleLimitEligibility();








    }
ngOnDestroy() {
    this.requestapi.clearData();
  }

  //Deferral
   remarksNext() {

  for (let item of this.DeferralDocArray){
           if (item.status == 0)
              {
               console.log(item.status, "this.status***********************************************")
               this.defStatus = true;
              }
           }
   for(let doc of this.OtherDeferralDocArray){
       if (doc.status == 0)
             {
              console.log(doc.status, "this.status***********************************************")
              this.defStatus = true;
             }
       }

       if(this.defStatus){
       this.toaster.error("Please make Decision for all documents");
       this.defStatus = false;
       }
       else{

            this.workFlowRemarks= true;
            this.deferralApproval =false;

            }
  }
   previousDeferralApproval(){

             this.workFlowRemarks = false;
             this.deferralApproval = true;
             }

    ///Next & Previous///
    //Business Stage(deDupeNp,proposalNp,)




    nextDeDupe(){
        this.deDupeNp = false;
        this.proposalNp = true;
        window.scrollTo(0, 0);
    }
    previousProposal(){
        this.proposalNp = false;
        this.deDupeNp = true;
        window.scrollTo(0, 0);
    }
    nextProposal(){
        this.proposalNp = false;
        this.uploadDataNp = true;
        window.scrollTo(0, 0);
    }
    previousUploadData(){
        this.uploadDataNp = false;
        this.proposalNp = true;
        window.scrollTo(0, 0);
    }
    nextBusinessRemarks(){
         this.uploadDataNp = false;
         this.businessRemarks = true;
         window.scrollTo(0, 0);
    }
    previousBusinessRemarks(){
         this.uploadDataNp = true;
         this.businessRemarks = false;
         window.scrollTo(0, 0);
    }
    //Under Writer(cPDetailsNp,assignUnderwriterNp)
    nextCpDetails(){
        this.cPDetailsNp = false;
        this.assignUnderwriterNp = true;
        window.scrollTo(0, 0);
    }
    previousAssignUnderwriter(){
        this.assignUnderwriterNp = false;
        this.cPDetailsNp = true;window.scrollTo(0, 0);
    }
     nextAssignUnderwriterRemarks(){
        this.assignUnderwriterNp = false;
        this.assignUnderwriterRemarks = true;window.scrollTo(0, 0);
    }
     previousAssignUnderwriterRemarks(){
        this.assignUnderwriterNp = true;
        this.assignUnderwriterRemarks = false;window.scrollTo(0, 0);
    }

    //Credit CPA (creditCpaCPDetailsNP,proposaDetailsNp ,docChecklistNp ,softPolicyNp )
    nextCreditCpaCpDetails(){
        this.creditCpaCPDetailsNP = false;
        this.proposaDetailsNp = true;window.scrollTo(0, 0);
    }
    previousProposalDetails(){
        this.proposaDetailsNp = false;
        this.creditCpaCPDetailsNP = true;window.scrollTo(0, 0);
    }
    nextProposalDetails(){
        this.proposaDetailsNp = false;
        this.docChecklistNp = true;window.scrollTo(0, 0);
    }
    previousDocChecklist(){
        this.docChecklistNp = false;
        this.proposaDetailsNp = true;window.scrollTo(0, 0);
    }
    nextDocChecklist(){
        this.docChecklistNp = false;
        this.softPolicyNp = true;window.scrollTo(0, 0);
    }
    previousSoftPolicy(){
        this.softPolicyNp = false;
        this.docChecklistNp = true;window.scrollTo(0, 0);
    }
    nextCreditCpaRemarks(){
            this.creditCpaRemarks = true;
            this.softPolicyNp = false;window.scrollTo(0, 0);
    }
    previousCreditCpaRemarks(){
            this.softPolicyNp = true;
            this.creditCpaRemarks = false;window.scrollTo(0, 0);
     }


     //overrideSoftPolicy(nextOverride,previousOverride)
     nextOverrideCPBasic(){
          this.overridePolicyCPDetailsNp = false;
          this.overridePolicy = true;window.scrollTo(0, 0);
      }
      previousOverrideCPBasic(){
        this.overridePolicy = false;
        this.overridePolicyCPDetailsNp = true;window.scrollTo(0, 0);
    }
     nextOverrideRemarks(){
         this.overridePolicy = false;
         this.overrideSoftPolicyRemarks = true;window.scrollTo(0, 0);
     }
      previousOverrideRemarks(){
          this.overridePolicy = true;
          this.overrideSoftPolicyRemarks = false;window.scrollTo(0, 0);
      }


    //CAM Upload (camCPDetailsNp,camOverrideSoftPolicyNp,camDebtProfileNp ,CAMUploadNp)
    nextCamCpDetails(){
        this.camCPDetailsNp = false;
        this.camOverrideSoftPolicyNp = true;window.scrollTo(0, 0);
    }
    previousOverrideSoftPolicy(){
        this.camOverrideSoftPolicyNp = false;
        this.camCPDetailsNp = true;window.scrollTo(0, 0);
    }
    nextOverrideSoftPolicy(){
        this.camOverrideSoftPolicyNp = false;
        this.camDebtProfileNp = true;window.scrollTo(0, 0);
    }
    previousDebtProfile(){
        this.camDebtProfileNp = false;
        this.camOverrideSoftPolicyNp = true;window.scrollTo(0, 0);
    }
    nextDebtProfile(){
        this.camDebtProfileNp = false;
        this.CAMUploadNp = true;window.scrollTo(0, 0);
    }
    previousCamUpload(){
        this.CAMUploadNp = false;
        this.camDebtProfileNp = true;window.scrollTo(0, 0);
    }
    nextCamUpload(){
        this.CAMUploadNp = false;
        this.CAMRemarks = true;window.scrollTo(0, 0);
    }
    previousCamRemarks(){
        this.CAMUploadNp = true;
        this.CAMRemarks = false;window.scrollTo(0, 0);
    }
    //Under Writer Review (uwrCPDetailsNp,uwrViewCamNp,uwrLimitEligibilityNp,uwrTermSheetNp,uwrCollateralNp,uwrCreditPolicyNp)
    nextUWRCpDetails(){
        this.uwrCPDetailsNp = false;
        this.uwrViewCamNp = true;window.scrollTo(0, 0);
    }
    previousUWRViewCam(){
        this.uwrViewCamNp = false;
        this.uwrCPDetailsNp = true;window.scrollTo(0, 0);
    }
    nextUWRViewCam(){
        this.uwrViewCamNp = false;
        this.uwrLimitEligibilityNp = true;window.scrollTo(0, 0);
    }
    previousUWRLimitEligibility(){
        this.uwrLimitEligibilityNp = false;
        this.uwrViewCamNp = true;window.scrollTo(0, 0);
    }
    nextUWRLimitEligibility(){
        this.uwrLimitEligibilityNp = false;
        this.uwrTermSheetNp = true;window.scrollTo(0, 0);
    }
    previousUWRTermSheet(){
        this.uwrTermSheetNp = false;
        this.uwrLimitEligibilityNp = true;window.scrollTo(0, 0);
    }
    nextUWRTermSheet(){
        this.uwrTermSheetNp = false;
        this.uwrCollateralNp = true;window.scrollTo(0, 0);
    }
    previousUWRCollateral(){
        this.uwrCollateralNp = false;
        this.uwrTermSheetNp = true;window.scrollTo(0, 0);
    }
    nextUWRCollateral(){
        this.uwrCollateralNp = false;
        this.uwrCreditPolicyNp = true;window.scrollTo(0, 0);
    }
    previousUWRCreditPolicy(){
        this.uwrCreditPolicyNp = false;
        this.uwrCollateralNp = true;window.scrollTo(0, 0);
    }

    nextUWRCreditPolicy(){
        this.uwrCreditPolicyNp = false;
        this.uwrRemarks = true;window.scrollTo(0, 0);
    }
    previousUWRRemarks(){
        this.uwrCreditPolicyNp = true;
        this.uwrRemarks = false;window.scrollTo(0, 0);
    }
    //Under Writer PD Review (uwPdRCpDetailsNp,uwPdRDueDiligenceNp)
    nextUwPdRCpDetails(){
        this.uwPdRCpDetailsNp = false;
        this.uwPdRDueDiligenceNp = true;window.scrollTo(0, 0);
    }
    previousDueDiligence(){
        this.uwPdRDueDiligenceNp = false;
        this.uwPdRCpDetailsNp = true;window.scrollTo(0, 0);
    }
    nextDueDiligence(){
        this.uwPdRDueDiligenceNp = false;
        this.uwPdRemarks = true;window.scrollTo(0, 0);
    }
    previousUwRemarks(){
         this.uwPdRDueDiligenceNp = true;
         this.uwPdRemarks = false;window.scrollTo(0, 0);
    }


    // Commercial/Credit (commercialCreditCpDetailsNp,commercialCCApprovalNp,creditCCApprovalNp)
    nextCommercialCreditCPDetails(){
        this.commercialCreditCpDetailsNp = false;
        this.commercialCCApprovalNp = true;window.scrollTo(0, 0);
    }

    nextCommercialCreditApproval(){
            this.commercialCreditCpDetailsNp = false;
            this.commercialCCApprovalNp = false;
            this.creditCCApprovalNp = false;
            this.commercialCreditNp=true;
            window.scrollTo(0, 0);
        }

     previousCommercialCreditCPDetails(){
                 this.commercialCreditCpDetailsNp = true;
                 this.commercialCCApprovalNp = false;
                 this.creditCCApprovalNp = false;
                 this.commercialCreditNp=false;
                 window.scrollTo(0, 0);
             }
             nextCommercialCreditRemApproval(){
              this.commercialCreditCpDetailsNp = false;
                              this.commercialCCApprovalNp = false;
                              this.creditCCApprovalNp = false;
                              this.commercialCreditNp=false;
                              this.CommercialCreditRemarks = true;
                              window.scrollTo(0, 0);
             }
             prevCommercialCreditApproval(){
             this.commercialCreditCpDetailsNp = false;
                                           this.commercialCCApprovalNp = false;
                                           this.creditCCApprovalNp = false;
                                           this.commercialCreditNp=true;
                                           this.CommercialCreditRemarks = false;
                                           window.scrollTo(0, 0);
             }
    nextCreditCPDetails(){
        this.commercialCreditCpDetailsNp = false;
        this.creditCCApprovalNp = true;window.scrollTo(0, 0);
    }
    previousCreditCPDetails(){
        this.commercialCreditCpDetailsNp = true;
        this.creditCCApprovalNp = false;window.scrollTo(0, 0);
    }
    previousCommercialApproval(){
        this.commercialCCApprovalNp = false;
        this.commercialCreditCpDetailsNp = true;window.scrollTo(0, 0);
    }
    nextCommercialApproval(){
        this.commercialCCApprovalNp = false;
        this.creditCCApprovalNp = true;window.scrollTo(0, 0);
    }
    previousCreditApproval(){
        this.creditCCApprovalNp = false;
        this.commercialCCApprovalNp = true;window.scrollTo(0, 0);
    }
    nextCreditApproval(){
        this.creditCCApprovalNp = false;
        this.CommercialCreditRemarks = true;window.scrollTo(0, 0);
    }
    previousCreditRemarks(){
        this.creditCCApprovalNp = true;
        this.CommercialCreditRemarks = false;window.scrollTo(0, 0);
    }
    nextCommerciaApproval(){
        this.commercialCCApprovalNp = false;
        this.CommercialCreditRemarks = true;window.scrollTo(0, 0);
    }
    previousCommercialRemarks(){
        this.commercialCCApprovalNp = true;
        this.CommercialCreditRemarks = false;window.scrollTo(0, 0);
    }

    //RM Customer Acceptance (rmCustCpDetailsNp,rmCustCommercialCC)
    nextRmCustCpDetails(){

        this.rmCustCpDetailsNp = false;
        this.rmCustCommercialCCNp = true;window.scrollTo(0, 0);
    }
    previousRmCustCommercialCC(){
        this.rmCustCommercialCCNp = false;
        this.rmCustCpDetailsNp = true;window.scrollTo(0, 0);
    }
    nextRmCustCommercialCC(){
        this.rmCustRemarksCCNp = true;
        this.rmCustCommercialCCNp = false;window.scrollTo(0, 0);
   }
   previousRmCustRemarksCC(){
           this.rmCustRemarksCCNp = false;
           this.rmCustCommercialCCNp = true;window.scrollTo(0, 0);
      }
    //Operation Maker (OpsMakerCpDetailsNp,OpsMakerBeneficiaryNp,OpsMakerDocUploadNp)
    nextOpsMakerCpDetails(){
        this.OpsMakerCpDetailsNp = false;
        this.OpsMakerBeneficiaryNp = true;window.scrollTo(0, 0);
    }
    previousOpsMakerBeneficiary(){
    this.OpsMakerBeneficiaryNp = false ;
    this.OpsMakerCpDetailsNp = true;window.scrollTo(0, 0);
    }
    nextOpsMakerBeneficiary(){
    this.OpsMakerBeneficiaryNp = false;
    this.OpsMakerDocUploadNp = true;window.scrollTo(0, 0);
    }
    previousOpsMakerDocUpload(){
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerBeneficiaryNp = true;window.scrollTo(0, 0);
    }
    nextOpsMakerDocUpload(){
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerRemarksNp = true;window.scrollTo(0, 0);
    }
    previousOpsMakerRemarks(){
        this.OpsMakerDocUploadNp = true;
        this.OpsMakerRemarksNp = false;window.scrollTo(0, 0);
    }


viewApproval(){


     let response;
               let fileName="dms/deferralReport/"+this.cpId+"/0";
               response = this.requestapi.getData(fileName);
               response.subscribe((res: any) => {
                    console.log("Response::",res);
                   if(res.length>0){
                   this.DeferralDocArray = [];
                   this.deferrDoc =  true;
                   }
                   for(var i=0;i<res.length;i++){
                   this.addDeferralDocArray();
                   this.DeferralDocArray[i].id = res[i].id;
                   this.DeferralDocArray[i].appId = res[i].applicationEntity.id;
                   this.DeferralDocArray[i].docListId = res[i].documentListEntity.id;
                   this.DeferralDocArray[i].documentName = res[i].documentListEntity.displayName;
                   this.DeferralDocArray[i].initialTime = res[i].initialTime;
                   this.DeferralDocArray[i].revisedTime = res[i].revisedTime;
                   this.DeferralDocArray[i].deferralType = res[i].documentListEntity.deferralType;
                   this.DeferralDocArray[i].deferral = res[i].documentListEntity.deferral;
                   this.DeferralDocArray[i].status = res[i].status
                   if(res[i].rmName != null){
                   this.rmName = res[i].rmName;
                   this.docRMName = res[i].rmName;
                   this.rmNames = res[i].rmName;
}

           }

     })
  }

  getDeferralOtherDocuments(): void{

  //       this.cpId = id;
        let response;
        console.log("***************************************************************************")
                  let fileName="dms/othersDeferralDocuments/"+this.cpId+"/0";
                  response = this.requestapi.getData(fileName);
                  response.subscribe((res: any) => {
                      console.log(res,"OtherDocuments");
                      this.otherDefDocRes = res;
                      console.log("this.otherDefDocRes::::::::::::::::::::::::::::::",this.otherDefDocRes)
                      this.showLoader = false;

                      if(res.length>0){
//                       this.OtherDeferralDocArray = [];
                      this.OtherDoc = true
                      }
                      for(var i=0;i<res.length;i++){
  //                     this.rmNames = res[i].rmName;
                      this.addOtherDeferralDocArray();
                      this.OtherDeferralDocArray[i].id = res[i].id;
                      this.OtherDeferralDocArray[i].appId = res[i].applicationEntity.id;
                      this.OtherDeferralDocArray[i].docListId = res[i].documentListEntity.id;
                      this.OtherDeferralDocArray[i].displayName = res[i].displayName;

                      var dateString = res[i].initialTime[0]+"-"+res[i].initialTime[1]+"-"+res[i].initialTime[2];
                      var date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                      this.OtherDeferralDocArray[i].initialTime = date;

                      this.OtherDeferralDocArray[i].revisedTime = res[i].revisedTime;
                      this.OtherDeferralDocArray[i].status = res[i].status
                       if(res[i].rmName != null){
                         this.docRMName = res[i].rmName;
                         this.rmNames = res[i].rmName;
                       }

    }
  })
    }


    updateEta(){
    this.showLoader=true;
        let response;
        let fileName="dms/saveNewDeferralDate";
        let data={ appId: this.cpId,deferralReportsDataList:this.DeferralDocArray,rmName : this.rmName, docProductCheck : true, constitution : 'Private Company',}
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
//         for(let DeferralDoc of this.DeferralDocArray)
//         this.toaster.success("Successfully Submitted");
//         this.popupFun(this.twelveId,2,12,'CP_DEFERRAL_COMMITTEE_LEAD');
        },error=>{
        this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status == 400){
             if(error.error.message == null || error.error.message == ''){
             this.toaster.error('Some Technical Error');
             }else{
             this.toaster.error(error.error.message);
             }
           }

        })
    }
    storeDecision(id,item:any,key){

      this.DocListEntity=id;
       if(Event.prototype.isPrototypeOf(item)){
                  this.status=item.target.value;
                  if(key == 1){
                  this.status = 1;
                  this.toaster.success("Decision made as Approve")
                  }else if(key == 3){
                  this.status = 3;
                  this.toaster.success("Decision made as WaiveOff")
                  }
              }else{
                  this.status=item.toString();
              }
      for(let deferralDoc of this.DeferralDocArray){
      if(deferralDoc.id == this.DocListEntity)
        {
        let index = this.DeferralDocArray.indexOf(deferralDoc);
        console.log("index*************",index);
        const c = {
                  id:deferralDoc.id,
                  appId: this.cpId,
                  docListId : deferralDoc.docListId,
                  documentName : deferralDoc.documentName,
                  initialTime: deferralDoc.initialTime,
                  revisedTime: deferralDoc.revisedTime,
                  status: this.status
        }
        this.DeferralDocArray[index] = c;

        let lengthStore =this.DeferralDocArray.length-1;

        if(lengthStore == index){

        this.approveHide=false;

        }
        break;
        }
       }
      }

      otherDeferralStoreDecision(id,item:any,key){

         this.DocListEntity=id;
         if(Event.prototype.isPrototypeOf(item)){
                    this.status=item.target.value;
                    if(key == 1){
                    this.status = 1;
                    this.toaster.success("Decision made as Approve")
                    }else if(key == 3){
                    this.status = 3;
                    this.toaster.success("Decision made as WaiveOff")
                    }else{
                    this.status = 0;
                    }
                }else{
                    this.status=item.toString();
                }
        for(let otherDefDoc of this.OtherDeferralDocArray){
        if(otherDefDoc.id == this.DocListEntity)
          {
          let index = this.OtherDeferralDocArray.indexOf(otherDefDoc);
          console.log("index*************",index);
          const c = {
                    id:otherDefDoc.id,
                    appId: this.cpId,
                    docListId : otherDefDoc.docListId,
                    displayName : otherDefDoc.displayName,
                    initialTime: otherDefDoc.initialTime,
                    revisedTime: otherDefDoc.revisedTime,
                    status: this.status
          }
          this.OtherDeferralDocArray[index] = c;

          let lengthStore =this.OtherDeferralDocArray.length-1;

          if(lengthStore == index){

          this.approveHide=false;

          }
          break;
          }
         }
        }
updateOtherDef(){

//     this.showLoader = true;
    let response;
    let fileName="dms/updateOtherDeferralStatus";
    let data={ appId: this.cpId,otherDocumentDataList:this.OtherDeferralDocArray,rmName : this.rmNames}
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
//     window.location.reload();
//         this.router.navigate(['/email/worflow']);
        this.showLoader = false;
//         console.log("Working ?????")
    this.toaster.success("Successfully Submitted")
    },error=>{
    this.showLoader = false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
    if(error.status == 400){
         if(error.error.message == null || error.error.message == ''){
         this.toaster.error('Some Technical Error');
         }else{
         this.toaster.error(error.error.message);
         }
       }

    })
}

ApprovalOtherDeferralStatus(key): void {
    for(let otherDeferralDocArr of this.OtherDeferralDocArray){
             if (key == 2)
             {
             otherDeferralDocArr.status = 2;
             }
             else if(key == 1)
             {
             otherDeferralDocArr.status = 1;
             }
    }

}
    //Operation Checker (OpsCheckerCpDetailsNp,OpsCheckerDocDownloadNp)
    nextOpsCheckerCpDetails(){
        this.OpsCheckerCpDetailsNp = false;
        this.OpsCheckerDocDownloadNp = true;window.scrollTo(0, 0);
    }
    previousOpsCheckerDocDownload(){
        this.OpsCheckerDocDownloadNp = false;
        this.OpsCheckerCpDetailsNp = true;window.scrollTo(0, 0);
    }

    nextOpsCheckerRemarks(){
        this.OpsCheckerDocDownloadNp = false;
        this.OpsCheckerRemarksNp = true;window.scrollTo(0, 0);
    }

    previousOpsCheckerRemarks(){
        this.OpsCheckerDocDownloadNp = true;
        this.OpsCheckerRemarksNp = false;window.scrollTo(0, 0);
    }
    rbacArray: Array<any> = [];

roleBasedFun(): void{

    let roles=localStorage.getItem('roles');
    let response;
    let fileName="permission/permission/";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        for(let i=0;i<res.length;i++){
            if(res[i].stageId.clientType==2){
                this.rbacArray.push(res[i]);
            }
        }
        for(let j=0;j<this.rbacArray.length;j++){
            if(this.rbacArray[j].stageId.stageId=='CP1' && this.firstWip=='allow'){
                if(this.rbacArray[j].role=='BUSINESS' ){
                    this.cp1Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP1' && this.firstComplete=='allow'){
                if(this.rbacArray[j].role=='BUSINESS' ){
                    this.cp1View= true;
                    this.cp1Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP2' && this.secondWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT HEAD' || this.rbacArray[j].role=='RISK HEAD'){
                    this.cp2Submit = true;
                    this.cp2Return = true;
                    this.cp2Reject = true;
                    this.cp1View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP2' && this.secondComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT HEAD' || this.rbacArray[j].role=='RISK HEAD'){
                    this.cp2View= true;
                    this.cp2Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP3' && this.thirdWip=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp3Submit=true;
                    this.cp1View= true;
                    this.cp2View= true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP3' && this.thirdComplete=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp2View=true;
                    this.cp3View= true;
                    this.cp3Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP4' && this.fourthWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp4Submit = true;
                    this.cp4Reject = true;
                    this.cp4Return = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP4' && this.fourthComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp4Submit=false;
                    this.cp4Reject=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP5' && this.fifthWip=='allow'){
                if(this.rbacArray[j].role=='CPA'){
                    this.cp5Submit = true;
                    this.cp5Return = true;
                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP5' && this.fifthComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT COMMITTEE'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp5Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP6' && this.sixWip=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp6Submit=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                }
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp6Return = true;
                    this.cp6Reject = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP6' && this.sixComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp6Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP7' && this.sevenWip=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp7Submit=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                }
                if(this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp7Return = true;
                    this.cp7Reject = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP7' && this.sevenComplete=='allow'){
                if(this.rbacArray[j].role=='CPA' || this.rbacArray[j].role=='CREDIT UNDERWRITER' || this.rbacArray[j].role=='RISK UNDERWRITER'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp7Submit=false;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP8' && this.eightWip=='allow'){
                if(this.rbacArray[j].role=='CREDIT APPROVAL COMMITTEE'){
                    this.cp8Submit = true;
                    this.cp8Return = true;
                    this.cp8Reject = true;

                    this.creditCC = true;

                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                }
                if(this.rbacArray[j].role=='COMMERCIAL APPROVAL COMMITTEE'){
                    this.cp8Submit = true;
                    this.cp8Return = true;
                    this.cp8Reject = true;

                    this.commercialCC = true;

                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP8' && this.eightComplete=='allow'){
                if(this.rbacArray[j].role=='CREDIT APPROVAL COMMITTEE' || this.rbacArray[j].role=='COMMERCIAL APPROVAL COMMITTEE'){
                    this.cp2View = true;
                    this.cp3View = true;
                    this.cp4View = true;
                    this.cp5View = true;
                    this.cp6View = true;
                    this.cp7View = true;
                    this.cp8View = true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP9' && this.nineWip=='allow'){
                if(this.rbacArray[j].role=='BUSINESS'){
                    this.cp9Reject=false;
                    this.cp9Return=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9Submit=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP9' && this.nineComplete=='allow'){
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                this.cp9View=true;
            }
            if(this.rbacArray[j].stageId.stageId=='CP10' && this.tenWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.cp10Submit=true;
                    this.cp10Return = true;
                    this.cp10Reject= true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP10' && this.tenComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION MAKER'){
                    this.cp10Submit=false;
                    this.cp10Return=false;
                    this.cp10Reject=false;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                    this.cp10View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP11' && this.elevenWip=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.cp11Submit=true;
                    this.cp11Return =true;
                    this.cp11Reject=true;
                    this.cp11Approve=true;
                    this.cp2View=true;
                    this.cp3View=true;
                    this.cp4View= true;
                    this.cp5View= true;
                    this.cp6View= true;
                    this.cp7View= true;
                    this.cp8View= true;
                    this.cp9View=true;
                    this.cp10View=true;
                }
            }
            if(this.rbacArray[j].stageId.stageId=='CP11' && this.elevenComplete=='allow'){
                if(this.rbacArray[j].role=='OPERATION CHECKER'){
                    this.cp11Submit=false;
                    this.cp11Return=false;
                    this.cp11Reject=false;
                    this.cp11View=true;
                }
            }


            if(this.rbacArray[j].stageId.stageId=='CP12' && this.twelveWip=='allow'){
                            if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                                this.cp12Submit=true;
                                this.cp12Return =true;
                                this.cp12Reject=true;
                                this.cp12Approve=true;
                                this.cp2View=true;
                                this.cp3View=true;
                                this.cp4View= true;
                                this.cp5View= true;
                                this.cp6View= true;
                                this.cp7View= true;
                                this.cp8View= true;
                                this.cp9View=true;
                                this.cp10View=true;
                                this.cp11View=true;
                            }
                        }
                        if(this.rbacArray[j].stageId.stageId=='CP12' && this.twelveComplete=='allow'){
                            if(this.rbacArray[j].role=='DEFERRAL COMMITTEE'){
                                this.cp12Submit=false;
                                this.cp12Return=false;
                                this.cp12Reject=false;
                                this.cp12View=true;
                            }
                        }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

currentStepperFun(id){

    let response;
    let fileName="wfApprovalStatus/getHistoryOfWFStatus/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.getDocumentType();
        this.viewData=res;
        for(let i=0;i<res.length;i++){
            if(res[i].stage.stageId=='CP1' && res[i].status==2){
                this.firstComplete='allow';
                this.isFirstActive = true;

            }
            else if(res[i].stage.stageId=='CP1' && res[i].status==0){
                this.firstComplete='';
                this.firstWip='allow';
                this.wizard.goToStep(0);
            }
            else if(res[i].stage.stageId=='CP1' && res[i].status==null){
                this.firstComplete='';
                this.firstWip='';
            }
            else if(res[i].stage.stageId=='CP2' && res[i].status==2){
                this.secondComplete='allow';
                this.isSecActive = true;
                this.getProposedProductDetails(false);
                 this.getProposalById();
            }
            else if(res[i].stage.stageId=='CP2' && res[i].status==0){
                this.secondWip='allow';
                this.getProposalById();
                 this.getCounterPartyById();
                 this.getProposedProductDetails(false);
                this.wizard.goToStep(1);
            }
            else if(res[i].stage.stageId=='CP2' && res[i].status==-1){
                this.firstComplete='';
                this.firstWip='allow';
            }
            else if(res[i].stage.stageId=='CP2' && res[i].status==null){
                this.secondComplete='';
                this.secondWip='';
            }
            else if(res[i].stage.stageId=='CP3' && res[i].status==2){
                this.thirdComplete='allow';
                this.isThirdActive = true;
                this.getDocumentsReports();
            }
            else if(res[i].stage.stageId=='CP3' && res[i].status==0){
                this.thirdComplete='';
                 this.thirdWip='allow';
                 this.getSoftPolicyMaster();
                 this.getProposedProductDetails(false);
                 this.getCounterPartyById();
                this.getDocumentsReports();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
            }
            else if(res[i].stage.stageId=='CP3' && res[i].status==null){
                this.thirdComplete='';
                this.thirdWip='';
            }
            else if(res[i].stage.stageId=='CP4' && res[i].status==2){
                this.firstComplete='allow';
                this.secondComplete='allow';
                this.thirdComplete='allow';
                this.fourthComplete='allow';
                this.isFourthActive = true;

            }
            else if(res[i].stage.stageId=='CP4' && res[i].status==0){
                this.fourthWip='allow';
                this.getProposalById();
                 this.getCounterPartyById();
                 this.getSoftPolicyDetails();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
            }
            else if(res[i].stage.stageId=='CP4' && res[i].status==-1){
                this.fourthComplete='';
                this.fourthWip='allow';
            }
            else if(res[i].stage.stageId=='CP4' && res[i].status==null){
                this.fourthComplete='';
                this.fourthWip='';
            }
            else if(res[i].stage.stageId=='CP5' && res[i].status==2){
                this.fifthComplete='allow';
                this.getSoftPolicyMaster();
                this.getSoftPolicyDetails();
                this.getDocumentsReports();
                this.getCollateral();
                this.getCreditPolicy();
            }
            else if(res[i].stage.stageId=='CP5' && res[i].status==0){
                this.fifthWip='allow';
                this.getCounterPartyById();
                this.getSoftPolicyDetails();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
            }
            else if(res[i].stage.stageId=='CP5' && res[i].status==-1){
                this.fourthComplete='';
                this.fourthWip='allow';
            }
            else if(res[i].stage.stageId=='CP5' && res[i].status==null){
                this.fifthComplete='';
                this.fifthWip='';
            }
            else if(res[i].stage.stageId=='CP6' && res[i].status==2){
                this.sixComplete='allow';
                this.getLimitEligibilityById();
                this.getDueDiligence();
            }
            else if(res[i].stage.stageId=='CP6' && res[i].status==0){
                this.sixWip='allow';
                this.getCollateral();
                this.getDocumentsReports();
                this.getCreditPolicy();
                this.getDebtProfileById();
                this.getProposalById();
                this.getCounterPartyById();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
            }
            else if(res[i].stage.stageId=='CP6' && res[i].status==-1){
                this.fifthComplete='';
                this.fifthWip='allow';
            }
            else if(res[i].stage.stageId=='CP6' && res[i].status==null){
                this.sixComplete='';
                this.sixWip='';
            }
            else if(res[i].stage.stageId=='CP7' && res[i].status==2){
                this.getDueDiligenceById();
                this.sevenComplete='allow';
            }
            else if(res[i].stage.stageId=='CP7' && res[i].status==0){
                this.sevenWip='allow';
                this.getDueDiligence();
                this.getCounterPartyById();
                this.getDebtProfileById();
                this.getLimitEligibilityById();
                this.getTermSheet();
                this.getCollateralById();
                this.getFundRequirement();
                this.getCreditPolicyDetails();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
            }
            else if(res[i].stage.stageId=='CP7' && res[i].status==-1){
                this.sixComplete='';
                this.sixWip='allow';
            }
            else if(res[i].stage.stageId=='CP7' && res[i].status==null){
                this.sevenComplete='';
                this.sevenWip='';
            }
            else if(res[i].stage.stageId=='CP8' && res[i].status==2){
                this.eightComplete='allow';
                this.getDocumentsReports();
                this.getTermSheet();
            }
            else if(res[i].stage.stageId=='CP8' && res[i].status==0){
                this.eightWip='allow';
                this.eightComplete='';
                this.getDueDiligenceById();
                this.getCounterPartyById();
                this.getDebtProfileById();
                this.getLimitEligibilityById();
                this.getTermSheet();
                this.getCollateralById();
                this.getFundRequirement();
                this.getCreditPolicyDetails();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
            }
            else if(res[i].stage.stageId=='CP8' && res[i].status==-1){
                this.sevenComplete='';
                this.sevenWip='allow';
            }
            else if(res[i].stage.stageId=='CP8' && res[i].status==null){
                this.eightComplete='';
                this.eightWip='';
            }
            else if(res[i].stage.stageId=='CP9' && res[i].status==2){
                this.nineComplete='allow';
            }
            else if(res[i].stage.stageId=='CP9' && res[i].status==0){
                this.nineWip='allow';
                this.getDueDiligenceById();
                this.getCounterPartyById();
                this.getDebtProfileById();
                this.getLimitEligibilityById();
                this.getTermSheet();
                this.getCollateralById();
                this.getFundRequirement();
                this.getCreditPolicyDetails();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
            }
            else if(res[i].stage.stageId=='CP9' && res[i].status==-1){
                this.nineComplete='';
                this.nineWip='allow';
            }
            else if(res[i].stage.stageId=='CP9' && res[i].status==null){
                this.nineComplete='';
                this.nineWip='';
            }
            else if(res[i].stage.stageId=='CP10' && res[i].status==2){
                this.tenComplete='allow';
                this.getDocumentsReports();
            }
            else if(res[i].stage.stageId=='CP10' && res[i].status==0){
                this.tenWip='allow';
                this.getDueDiligenceById();
                this.getCounterPartyById();
                this.getDebtProfileById();
                this.getLimitEligibilityById();
                this.getTermSheet();
                this.getCollateralById();
                this.getFundRequirement();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
            }
            else if(res[i].stage.stageId=='CP10' && res[i].status==null){
                this.tenComplete='';
                this.tenWip='';
            }
            else if(res[i].stage.stageId=='CP11' && res[i].status==2){
                this.elevenComplete='allow';
                this.wizard.goToStep(11);
            }
            else if(res[i].stage.stageId=='CP11' && res[i].status==0){
                this.elevenWip='allow';
                this.getDocumentsReports();
                this.getDueDiligenceById();
                this.getCounterPartyById();
                this.getDebtProfileById();
                this.getLimitEligibilityById();
                this.getTermSheet();
                this.getCollateralById();
                this.getFundRequirement();
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
            }
            else if(res[i].stage.stageId=='CP11' && res[i].status==-1){
                this.tenComplete='';
                this.tenWip='allow';
            }
            else if(res[i].stage.stageId=='CP11' && res[i].status==null){
                this.elevenComplete='';
                this.elevenWip='';
            }

            else if(res[i].stage.stageId=='CP12' && res[i].status==2){
                this.twelveComplete='allow';

                this.wizard.goToStep(12);
            }
            else if(res[i].stage.stageId=='CP12' && res[i].status==0){
                this.twelveWip='allow';
                this.wizard.goToStep(1);
                this.wizard.goToStep(2);
                this.wizard.goToStep(3);
                this.wizard.goToStep(4);
                this.wizard.goToStep(5);
                this.wizard.goToStep(6);
                this.wizard.goToStep(7);
                this.wizard.goToStep(8);
                this.wizard.goToStep(9);
                this.wizard.goToStep(10);
                this.wizard.goToStep(11);
                this.viewApproval();
                this.getDebtProfileById();
                this.getFundRequirement();
                this.getDeferralOtherDocuments();
            }
            else if(res[i].stage.stageId=='CP12' && res[i].status==-1){
                this.elevenComplete='';
                this.elevenWip='allow';
            }
            else if(res[i].stage.stageId=='CP12' && res[i].status==null){
                this.twelveComplete='';
                this.twelveWip='';
            }
        }
        this.roleBasedFun();
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

public finish() {
    this.toaster.success('Successfully Registered')
}

onChange(event) {
    this.file = event.target.files[0];
}

allowAlphabetNumbersWithSpace(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}

allowAlphabetNumbersWithSpecialChar(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 32 && k < 47) || (k > 58 && k < 64) || (k > 91 && k < 96) || (k > 123 && k < 126) || k == 8 || k == 32 || (k >= 48 && k <= 57));
}

allowNumbersWithDot(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k >= 48 && k <= 57) || k == 46)
}

allowNumbers(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k >= 48 && k <= 57))
}

allowAlphabetWithSpace(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 32);
}

blockSpecialChar(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8  || (k >= 48 && k <= 57));
}

empty(e){
    let k;
    document.all ? k = e.keyCode : k = e.which;
    return
}

getRMNames():void{

    let response;
    let fileName="group/getKeycloakUserByGroupName/RELATIONSHIP_MANAGER";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.rmNameArray=res.userDetails[0];
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }

    })
}

saveWf(stageId,status,remarkWf,leadGrp) : void {
    this.showLoader=true;
    this.stageId = stageId;
    this.wfStatus = status;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ appId:this.cpId,stageId:this.stageId,status:this.wfStatus,approverInfo:this.emailId,remarks:this.remarkWf,nextApproverInfo:leadGrp}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            if(leadGrp=='CP_OPERATION_CHECKER_LEAD' && this.indexTemp==11){
            if(!this.deferralStatus){
                this.storeProcedure();
            }
            else{
                this.showLoader=false;
                window.location.reload();
            }
                
            }else{
            this.showLoader=false;
                        window.location.reload();
            }

//                 else{
//                 window.location.reload();
//                 }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
      this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
                       }

    })
}

saveWfd(stageId,status,remarkWf,leadGrp) : void {

    this.showLoader=true;
    this.updateEta();
    this. updateOtherDef();
    this.stageId = stageId;
    this.wfStatus = status;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ appId:this.cpId,stageId:this.stageId,status:this.wfStatus,approverInfo:this.emailId,remarks:this.remarkWf,nextApproverInfo:leadGrp}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.storeProcedure();
            let saveDef=false;
                for(let item of this.DeferralDocArray){
                    if(item.status == 1){
                        saveDef = true;
                    }
                }

                for(let item of this.OtherDeferralDocArray){
                    if(item.status == 1){
                        saveDef = true;
                    }
                }

                if(!saveDef){
//                window.location.reload();
//                                this.router.navigate(['/dashboard/inbox']);
                        this.showLoader = false;
                }
                if(saveDef){


                let response1;
                let fileName1="deferralWorkflow/saveDeferralWorkflow";
                let data1={ appId:this.cpId,stageId:32,status:this.wfStatus,approverInfo:this.emailId,remarks:this.remarkWf,nextApproverInfo:leadGrp}
                        response1 = this.requestapi.postData(fileName1,JSON.stringify(data1));
                        response1.subscribe((res: any) => {
//                         this.storeProcedure();
                },error=>{
                this.showLoader=false;
                if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                          this.authService.SignOut(this.refresh_token);
                                   }

                })
                }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
      this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
                       }

    })
}

returnWf(StageId,remark,nextAppInfo){
    this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-1, approverInfo : this.emailId, appId : this.cpId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
        this.showLoader=false;
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

returnPopupFun(stageId,nextAppInfo){
    Swal.fire({
        title: 'Are you sure to return?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
    if (result.value) {
        this.returnWf(stageId,result.value,nextAppInfo);
    }
    })
}

rejectWf(StageId,remark,nextAppInfo){
  this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-2, approverInfo : this.emailId, appId : this.cpId, remarks : remark, nextApproverInfo : nextAppInfo }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

        window.location.reload();
        this.router.navigate(['/dashboard/inbox']);
        this.showLoader=false;
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

rejectPopupFun(stageId,nextAppInfo){
    Swal.fire({
        title: 'Are you sure to reject?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
    if (result.value) {
        this.rejectWf(stageId,result.value,nextAppInfo);
    }
    })
}
cinChange(){
this.flag=0;
}
    //////////////////////////De-Dupe//////////////////////////
    public submit(){
        this.validate = false;
        this.validate = !this.validate;
        const cusPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
        const custCin = /([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;
                var flag = true;
         if (this.cinMand)
         {
                if(!custCin.test(this.cinNumber2)){
                this.toaster.error('Please Enter Valid CIN Number')
                    flag = false;
                }
                if(this.cinNumber2 == "" || this.cinNumber2 == null){
                    flag = false;
                }
        }

        else if (!this.cinMand)
        {
            if(this.cinNumber2 == null || this.cinNumber2 == "")
                    {
                       flag = true;
                    }
            else
               {
                  if(!custCin.test(this.cinNumber2))
                   {
                     this.toaster.error('Please Enter valid CIN Number');
                     flag = false;
                   }
               }
        }

        if(!cusPan.test(this.panNumber2)){
            this.toaster.error('Please Enter Valid PAN Number')
            flag = false;
        }
        if(this.panNumber2==""  || this.panNumber2==null){
            flag = false;
        }
        if(this.validate==true && flag == true ){
            this.deDupeCpBasicDetails();
        }
    }
    constitutionVal:any;
    cinMand=false;
     checkContitution(event):void{
            this.deDupeEnable = true;
            this.panNumber2 = "";
            this.cinNumber2 = "";
            this.flag=0;
            this.constitutionVal=event.target.value;
            if (this.constitutionVal =='Public Company Listed' || this.constitutionVal == 'Private Company' || this.constitutionVal == 'Public Company Unlisted'){
                this.cinMand=true;
            }
            else{
                this.cinMand=false;
            }
        }

    deDupeCpBasicDetails(): void {
    this.showLoader=true;
    this.customerDeDupeList=[];
        let response;
        let fileName="anchor/customerInfoDetail";
        let data={
            customerName:"Sri AmmanAgency",product:"SCF",cin:this.cinNumber2,
            pan:this.panNumber2,dedupeStatus:"In-Active",status:false,
        }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
           this.customerInfoCusId = res.customerInfoEntity;
                if(!res.customerInfoEntity){

                        this.proposalArray = [];
                        this.proposalArray.push(this.newProposalAttribute)
                        this.newProposalAttribute = {};
                        this.cpProposed2=false;
                        this.cpProposed1=true;

                        this.toaster.success('New CounterParty')
                        this.panNumber2 = this.panNumber2,this.companyName="",this.gstNumber1="",this.cinNumber2=this.cinNumber2,this.activity="",this.cpCIty="",this.cpState="",this.source="",this.subSource="",this.rmName="",this.custContactName="",this.custContactMobile="",this.custContactEmail="",this.constitution = this.constitution,
                        this.cPBasicFlag2 =false;
                        this.cPBasicFlag1 = true;
                        this.flag=1;
                }else{
                    for(var i=0;i <res.customerInfoEntity.length;i++){
                        this.customerStatus1  = res.customerInfoEntity[i].dedupeStatus;
                        this.customerCin = res.customerInfoEntity[i].cin;
                        this.cusId = res.customerInfoEntity[i].custId;
                        this.cpCustId = res.customerInfoEntity[i].custId;
//                         this.custFacilityTenure = res.customerInfoEntity[i].facilityTenure;

                        if(this.customerStatus1 == "In-Active" ){
                            this.getCpbasicDetailsById(this.cusId);
                            this.flag=1;
                        }else {
                            this.customerDeDupeList=res.customerInfoEntity;
                            this.flag =0;
                       }
                    }
               }

        },error=>{
            this.showLoader=true;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                 this.authService.SignOut(this.refresh_token);
            }
        })
    }

    getCpbasicDetailsById(cusId): void{

        let response;
        let fileName="counterParty/cpBasic/"+this.cusId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {

            this.assignUwList=res;
            if(res.length != 0){
                this.panNumber2 = res.panNumber;
                this.companyName = res.companyName;
                this.gstNumber1 = res.gstNumber;
                this.cinNumber23 = res.cinNumber;
                this.activity = res.activity;
                this.cpCIty = res.city;
                this.cpState = res.state;
                this.source = res.source;
                this.subSource = res.subSource;
                this.rmName = res.rmName;
                this.docRMName = res.rmName;
                this.custContactName = res.cusContName;
                this.custContactMobile = res.cusContactNumber;
                this.custContactEmail = res.cusContactEmail;
//                 this.custFacilityTenure = res.applicationEntity.customerInfoEntity.facilityTenure;
                this.cpId = res.applicationEntity.id;
                this.cpPkId = res.id;
            }
            if(this.cinNumber23 == null){
            this.cinNumber2 = this.cinNumber2;
            } else{
            this.cinNumber2 = this.cinNumber23;
            }
            this.constitution = this.constitution;

            this.cPBasicFlag2 = true;
            this.cPBasicFlag1 = false;
//             this.getProposedProductDetails(true);
        },error=>{

            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }














    updateCustomerInfo(stageId,status,value,leadGrp): void {
        let response;
        let fileName="anchor/customerInfo";
        let data={
            customerName:this.companyName,product:"SCF",cin:this.cinNumber2,
            pan:this.panNumber2,dedupeStatus:"Active",id:this.cpCustId,status:false}

        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
            this.saveWf(stageId,status,value,leadGrp);
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400){
                 if(error.error.message == null || error.error.message == " "){
                  this.toaster.error('Some Technical Error')
                }else{
                 this.toaster.error(error.error.message)
                }
            }
        })
    }






    /////////////Counter Party Basic Details///////////////////
    public cpBasicDetailsSubmit(key) {
        this.cpBasicValidate = false;
        this.cpBasicValidate = !this.cpBasicValidate;

        const cusname =/^[a-zA-Z ]*$/;
        const cusPan =/([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$/;
        const cuscin = /([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$/;

        const gst = /([0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1})$/;
        const email =/^([a-zA-Z0-9]+[a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\.(com|co.in|in)$)/;

        var flag = true;


        if(this.cinNumber2 == null || this.cinNumber2 == "")
        {
           flag = true;
        }else
           {

              if(!cuscin.test(this.cinNumber2))
               {
                 this.toaster.error('Please Enter valid CIN Number');
                 this.cPBasicFlag1 = true;
                 flag = false;
               }
           }
         if(!cusname.test(this.constitution)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.cpCIty)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.cpState)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.source)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.subSource)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
         if(!cusname.test(this.custContactName)){
         this.cPBasicFlag1 = true;
           flag = false;
         }
        if(this.companyName=="" || this.companyName==null){
        this.toaster.error('Please Enter Valid Company Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.gstNumber1 == null || this.gstNumber1 == "")
                {
                   flag = true;
                }
        else
           {
              if(!gst.test(this.gstNumber1))
               {
                 this.toaster.error('Please Enter valid GST Number');
                 this.cPBasicFlag1 = true;
                 flag = false;
               }
           }

        if(this.constitution=="" || this.constitution==null){
        this.toaster.error('Please Enter Valid Constitution');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.cpCIty=="" || this.cpCIty==null){
        this.toaster.error('Please Enter Valid Cp City');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.cpState=="" || this.cpState==null){
        this.toaster.error('Please Enter Valid State');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.source=="" || this.source==null){
        this.toaster.error('Please Enter Valid Source');
        this.cPBasicFlag1 = true;
            flag=false;
            this.toaster.error("Please Select Source");
        }
        if(this.subSource=="" || this.subSource==null){
        this.toaster.error('Please Enter Valid Sub Source');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.rmName=="" || this.rmName==null){
        this.toaster.error('Please Enter Valid RM Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.custContactName=="" || this.custContactName==null){
        this.toaster.error('Please Enter Valid Name');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.custContactMobile=="" || this.custContactMobile==null){
        this.toaster.error('Please Enter Valid Mobile Number');
        this.cPBasicFlag1 = true;
            flag=false;
        }
        if(this.custContactEmail=="" || this.custContactEmail==null){
        this.cPBasicFlag1 = true;
        this.toaster.error('Please Enter Valid Email Id');
            flag=false;
        }
        if(!email.test(this.custContactEmail)){
                this.cPBasicFlag1 = true;
                this.toaster.error('Please Enter Valid Email Id');
                flag = false;
                }
        if(this.cpBasicValidate==true && flag == true )
        {
            if(key == 'save'){
                this.saveCPBasicDetails();
            }else if(key == 'update'){
                this.updateCPBasicDetails(true);
            }

        }
    }

    getRemarks():void{
   this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/getRemarks/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
    this.remarkArray=res;

    },error=>{
    this.showLoader=true;
    if(error.status==401){
    this.refresh_token=localStorage.getItem('refresh_token')
    this.authService.SignOut(this.refresh_token);
    }

    })
    }

    getCreditPolicyFilter(): void{
                this.creditNormDetails = [];
                let response;
                let fileName="counterParty/creditPolicyFilters?id="+this.cpId;
                response = this.requestapi.getData(fileName);
                response.subscribe((res: any) => {
                this.creditPolicyFilterList=res;
                this.getUpdateCreditPolicyDetails();
                if(this.creditPolicyFilterList == null){
                this.toaster.error('Credit Policy Tab Not Applicable');
                }else{
                this.nextUWRCollateral();
                }

                },error=>{
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                            this.authService.SignOut(this.refresh_token);
                                   }
                    console.log("error");
                })

            }

    saveCPBasicDetails(): void {

this.showLoader=true;
        if(this.cinNumber2 == null || this.cinNumber2 == ""){
                 this.cinNumber2 = null;
             }
        let response;
        let fileName="counterParty/cpBasicDetails/";
        let data={ panNumber:this.panNumber2,companyName:this.companyName,gstNumber:this.gstNumber1,cinNumber:this.cinNumber2,constitution:this.constitution,
            city:this.cpCIty,state:this.cpState,source:this.source,subSource:this.subSource,rmName:this.rmName,cusContName:this.custContactName,
            cusContactNumber:this.custContactMobile,cusContactEmail:this.custContactEmail,createdBy:"",updatedBy:"",activity:this.activity,counterPartyType:"New"
        }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.cpId=res.id;
            this.cusId = res.customerInfoEntity.id;
            this.customerName1 = res.customerInfoEntity.customerName;
            this.cpPan = res.customerInfoEntity.pan;
            this.cpCin = res.customerInfoEntity.cin;
            this.cPBasicFlag1 = false;
            this.cPBasicFlag2 = true;
//             this.CinNumbermad = true;
//             this.ConstitutionFlag = true;
//             this.PanNumber = true;
            this.toaster.success('Successfully Saved')
            this.getCounterPartyById();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                             this.refresh_token=localStorage.getItem('refresh_token')
                                     this.authService.SignOut(this.refresh_token);
                           }
         if(error.status==400)
         		{
         			if(error.error.message == null || error.error.message == ''){
         			this.toaster.error('Some Technical Error');
         			this.cPBasicFlag1 = true;
                    this.cPBasicFlag2 = false;
         			}else{
         			this.toaster.error(error.error.message);
         			this.cPBasicFlag1 = true;
                    this.cPBasicFlag2 = false;
         			}
         		}

        })
    }

saveAssignUnderwriter():void {
   this.showLoader=true;
   this.assignUnderwriterList = null;
    let response;
    let fileName = "counterParty/assignUnderwriter";
    let data={ appId:this.cpId,assignTo:this.assignTo,createdBy:"",updatedBy:""}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
                this.assignUwFlag1 = false;
                this.assignUwFlag2 = true;
                this.toaster.success('Successfully Saved')
                this.assignUnderwriterList = res.assignUnderwriterDetailsList;
                  this.getAssignUnderwriterGetById();


            },error=>{
            this.showLoader=false;
            if(error.status==401){
                            this.refresh_token=localStorage.getItem('refresh_token')
                                    this.authService.SignOut(this.refresh_token);
                               }
            if(error.status==400){
                       if(error.error.message == null || error.error.message == " "){
                       this.assignUwFlag1  = true;
                         this.toaster.error('Some Technical Error')
                       }else{
                        this.toaster.error(error.error.message)
                        this.assignUwFlag1  = true;
                       }
                    }
                this.assignUwFlag1  = true;

                })
                }

getAssignUnderwriterGetById(): void{
this.showLoader=true;
    let response;
    let fileName="counterParty/assignUnderwriter/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.assignUwList=res;
            if(this.assignUwList == null)
            {

                    this.assignUwFlag2 = false;
           }
           else{

                this.assignUwPkId = this.assignUwList.id;
                this.assignUwAppId= this.assignUwList.applicationEntity.id;
                this.assignTo =this.assignUwList.assignTo;
                this.assignUwFlag2 = true;
                this.assignUwFlag1 = false;
             }

    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


updateAssignUnderwriter(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/assignUnderwriter/";
    let data={id:this.assignUwPkId, appId:this.assignUwAppId,assignTo:this.assignTo,createdBy:"",updatedBy:"" }

    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.assignUwFlag1 = false;
        this.assignUwFlag2 = true;
        this.getAssignUnderwriterGetById()
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
        {
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error');
            this.assignUwFlag1 = true;
            }else{
            this.toaster.error(error.error.message);
            this.assignUwFlag1 = true;
            }
        }


    })
}


getBankDetails():void {

    let response;
    let fileName = "anchor/bankDetails";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        this.bankList = res.bankDetailsList;
    },error=>{


    })
}

getBranchDetails(id):void{

    this.bankBranchName = null;
    this.bankIfscCode = null;
    this.branchList = [];
    let response;
    let fileName = "anchor/branchDetails/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        this.branchList = res.branchDetailsList;
    },error=>{


    })
}

getBankDetailsWithIFSC(event):void{

    let ifsc = event.target.value;
    let response;
    let fileName = "anchor/bankDetails/"+ifsc+"/"+this.bankCode;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        let result = res.bankDetailsList;
        if (result){
            this.bankBranchName = result[0].BBM_DESC;
            this.bankBranchCode = result[0].BBM_CD;
        }else{
            this.toaster.error("Enter Valid IFSC Code");
            this.bankBranchName = null;
        }
    },error=>{


    })
}

updateCPBasicDetails(key): void {
    this.showLoader=true;
     if(this.cinNumber2 == null || this.cinNumber2 == ""){
          this.cinNumber2 = null;
      }
    let response;
    let fileName="counterParty/cpBasicDetails";
    let data={id:this.cpPkId, appId:this.cpId, custId:this.cpCustId, panNumber:this.panNumber2,companyName:this.companyName,gstNumber:this.gstNumber1,cinNumber:this.cinNumber2,constitution:this.constitution,
    city:this.cpCIty,state:this.cpState,source:this.source,subSource:this.subSource,rmName:this.rmName,cusContName:this.custContactName,
    cusContactNumber:this.custContactMobile,cusContactEmail:this.custContactEmail,createdBy:"",updatedBy:"",activity:this.activity,assessmentType:this.assessmentType,counterPartyType:"New",
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        if(key){
            this.toaster.success('Successfully Updated');
        }
        this.flag=1;
        this.cPBasicFlag1 = false;
//         this.softFlag =false;
        this.getUpdateSoftPolicyDetails();
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);

        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				this.cPBasicFlag1 = true;
        				}else{
        				this.toaster.error(error.error.message);
        				this.cPBasicFlag1 = true;
        				}
        			}


    })
}

    //////////////////////Post Proposal////////////////////////
getCustomerInfoDetails(): void {
    let response;
    let fileName="wfApprovalStatus/onBoardedCustomers/1";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.anchorList=res;

    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getUnderWriterDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="group/getCreditUnderWriterUsers";
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
        for(let i=0;i<res.userDetails.length;i++){
            for(let j=0;j<res.userDetails[i].length;j++){
                 this.name = res.userDetails[i].firstName;
                this.UnderWriterList.push(res.userDetails[i][j]);
            }
        }
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

proposalAnchorValidation():void{
    var check = true;
    for(var i = 0 ; i<this.proposalArray.length;i++){
        for(var j = i+1; j<this.proposalArray.length;j++){
            if(this.proposalArray[i].custId == this.proposalArray[j].custId && this.proposalArray[i].custId != undefined){
                if(this.proposalArray[i].product == this.proposalArray[j].product && this.proposalArray[i].product != undefined){
                    this.toaster.error("Same anchor with same product not allowed...");
                    check = false;
                }
            }
        }
    }
    if(check){
        let response;
        let fileName="dms/assessmentCheck?constitution="+this.constitution+"&appId="+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
            console.log("res -- >",res);
            if(res.status){
                this.assessmentType = res.assessmentType;
                this.anchorProduct = res.productType;
                this.enableUploadDataCredit = false;
                this.enableUploadDataKyc = false;
                this.assessmentKycFlag = res.assessmentKycFlag;
                this.deleteCreditDocPopUp();
            }else{
                this.assessmentKycFlag = res.assessmentKycFlag;
                this.anchorProduct = res.productType;
                if(res.assessmentType == "none" || res.assessmentType == ""){
                    this.assessmentType = "none";
                    this.enableUploadDataCredit = false;
                    this.enableUploadDataKyc = false;
                }else{
                    this.assessmentType = res.assessmentType;
                    this.enableUploadDataCredit = true;
                    this.enableUploadDataKyc = true;
                }
                this.getDocumentsReports();
                this.nextProposal();
            }
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
        })
    }
}

deleteCreditDocPopUp(){
    Swal.fire({
      title: 'Are you sure to submit?',
      text: 'The chosen Assessment Type does not match the criteria, so the uploaded documents will be deleted.Do you want to proceed?',
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Submit'
    }).then((result) => {
      if (result.value) {
            this.deleteCreditDocuments();
      }
    })
}

deleteCreditDocuments(){
    let response;
    let fileName="dms/deleteCreditDocuments?appId="+this.cpId;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res: any) => {
        console.log("res -- >",res);
        this.getDocumentsReports();
        this.assessmentType = "none";
        this.enableUploadDataCredit = false;
        this.enableUploadDataKyc = false;
        this.proposalNp = false;
        this.uploadDataNp = true;
        window.scrollTo(0, 0);
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getAnchorProducts(anchorId,i,product,key):void{
    let response;
    let fileName="anchor/programsDetailsByCustId/"+anchorId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        console.log("res:::",res);
        if(key){
            this.proposalArray[i].product = null;
            this.cpProposed1 = true;
        }
        const optionsArray = res.programNormsArray;
        const selectElement = document.getElementById("product"+i) as HTMLSelectElement;
        while (selectElement.options.length > 1) {
            selectElement.remove(1);
        }
        optionsArray.forEach(option => {
            const optionElement = document.createElement("option");
            optionElement.value = option.subProduct;
            optionElement.textContent = option.subProduct;
            selectElement.appendChild(optionElement);
        });
        (<HTMLInputElement>document.getElementById("product"+i)).value = product;
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

//////////////Proposed //////////////
getProposedProductDetails(key): void {
    this.creditProposalList = [];
    this.ProposalList = [];
    let response;
    let fileName="counterParty/proposedProductsById/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.proposalArray = [];
        this.newProposalAttribute = {};
        this.proposalArray.push(this.newProposalAttribute)

        if(res.length > 1 && res.length != this.proposalArray.length){
            for(var j=1;j<res.length;j++){
//              for(let anchorNameValidation of res.appId){
//                       this.anchorStatus = anchorNameValidation.customerInfoEntity.status
                       this.addProposalValue();
//                      }
            }
        }
        this.creditProposalList = res;
        this.ProposalList=res;
        if(res.length != 0){
            this.cpProposed2=true;
            this.cpProposed1 = false;
            for(var i=0;i<res.length;i++){
                this.proposalArray[i].id = res[i].id;
                this.proposalArray[i].appId = res[i].applicationEntity.id;
                this.proposalArray[i].custId = res[i].customerInfoEntity.id;
                if(key){
                    this.getAnchorProducts(res[i].customerInfoEntity.id,i,res[i].product,false);
                }
                this.proposalArray[i].product = res[i].product;
                this.proposalArray[i].type = res[i].type;
                this.proposalArray[i].proposed = res[i].proposed;
                if(res[i].vintageWithAnchor != null ||res[i].minMonthlyAnchor != null ||res[i].anchorRelationship != null){
                    this.creditCheck = true;
                }
            }
            this.gotoRiskOrCredit();
        }else{
            this.proposalArray = [];
            this.newProposalAttribute = {};
            this.proposalArray.push(this.newProposalAttribute)
            this.cpProposed2=false;
            this.cpProposed1=true;
//             this.cpProposed1 = true;
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getProProductDetails(): void {
    this.ProposalList = [];
    let response;
    let fileName="counterParty/proposedProductsById/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
        this.ProposalList=res;
        if(res.length != 0){
            this.cpProposed2=true;
            this.cpProposed1 = false;
            for(var i=0;i<res.length;i++){
                this.proposalArray[i].id = res[i].id;
                this.proposalArray[i].appId = res[i].applicationEntity.id;
                this.proposalArray[i].custId = res[i].customerInfoEntity.id;
                this.proposalArray[i].product = res[i].product;
                this.proposalArray[i].type = res[i].type;
                this.proposalArray[i].proposed = res[i].proposed;
                if(res[i].vintageWithAnchor != null ||res[i].minMonthlyAnchor != null ||res[i].anchorRelationship != null){
                    this.creditCheck = true;
                }
            }
        }else{
             this.cpProposed1 = true;
        }
    },error=>{
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}



    public proposalSubmit(key) {
        this.proposalValidate = false;
        this.proposalValidate = !this.proposalValidate;
        var proposalFlag = true;
        for(var i = 0 ; i<this.proposalArray.length;i++){
            for(var j = i+1; j<this.proposalArray.length;j++){
                if(this.proposalArray[i].custId == this.proposalArray[j].custId && this.proposalArray[i].custId != undefined){
                    if(this.proposalArray[i].product == this.proposalArray[j].product && this.proposalArray[i].product != undefined){
                        this.toaster.error("Same anchor with same product not allowed...");
                        proposalFlag = false;
                    }
                }
            }
        }
        if(proposalFlag){
            for(let proposals of this.proposalArray){
                this.anchorId = proposals.custId;
                if(proposals.custId=="" || proposals.custId==null){
                    this.toaster.error("Please Select Anchor");
                    proposalFlag=false;
                    break;
                }
                if(proposals.product=="" || proposals.product==null){
                    this.toaster.error("Please Select Product");
                    proposalFlag=false;
                    break;
                }
                if(proposals.proposed=="" || proposals.proposed==null){
                    this.toaster.error("Please Select Proposed Amount");
                    proposalFlag=false;
                    break;
                }
            }
        }
        this.gotoRiskOrCredit();
        if(this.proposalValidate==true && proposalFlag == true ){
            if(key == 'save'){
                this.saveProposal();
            }else if(key == 'update'){
                this.updateProposal();
            }
        }
    }

    saveProposal(): void{
        this.showLoader=true;
        let response;
        let fileName="counterParty/proposedProductDetails/"+this.cpId;
        let data={ proposedProductsDataList:this.proposalArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
            this.cpProposed1 = false;
            this.cpProposed2 = true;
            this.toaster.success('Successfully Saved')
            this.getProProductDetails();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                            this.refresh_token=localStorage.getItem('refresh_token')
                                    this.authService.SignOut(this.refresh_token);
                           }
             	if(error.status==400)
             			{
             				if(error.error.message == null || error.error.message == ''){
             				this.toaster.error('Some Technical Error')
             				this.cpProposed1 = true;
             				}else{
             				this.toaster.error(error.error.message);
             				this.cpProposed1 = true;
             				}
             			}


        })
    }

    updateProposal(): void {
        this.showLoader=true;
        let response;
        let fileName="counterParty/proposedProductDetails/"+this.cpId;
        let data={ proposedProductsDataList:this.proposalArray }
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.toaster.success('Successfully Updated');
            this.getProProductDetails();
        },error=>{
        this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}

        })
    }

deleteProposedProduct(index,id,clearKey): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/proposedProductDetails/"+id;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res:any) => {
        if(clearKey){
            this.proposalArray.splice(index, 1);
        }else{
            this.proposalArray.splice(index, 1);
            this.newProposalAttribute = { appId : this.cpId };
            this.proposalArray.push(this.newProposalAttribute)
            this.proposalValidate = false;
            this.cpProposed1 = true;
        }
        console.log("this.proposalArray.length",this.proposalArray.length);
        if(this.proposalArray.length == 1 && !clearKey){
            console.log("this.proposalArray== 1",this.proposalArray.length);
            for(let proposals of this.proposalArray){
                if(proposals.custId=="" || proposals.custId==null){
                    this.cpProposed2 = false;
                    break;
                }
                if(proposals.product=="" || proposals.product==null){
                    this.cpProposed2 = false;
                    break;
                }
                if(proposals.type=="" || proposals.type==null){
                    this.cpProposed2 = false;
                    break;
                }
                if(proposals.proposed=="" || proposals.proposed==null){
                    this.cpProposed2 = false;
                    break;
                }
            }
        }else{
            for(let proposals of this.proposalArray){
                if(proposals.custId=="" || proposals.custId==null){
                    this.cpProposed1 = true;
                    break;
                }
                if(proposals.product=="" || proposals.product==null){
                    this.cpProposed1 = true;
                    break;
                }
                if(proposals.type=="" || proposals.type==null){
                    this.cpProposed1 = true;
                    break;
                }
                if(proposals.proposed=="" || proposals.proposed==null){
                    this.cpProposed1 = true;
                    break;
                }
            }
        }
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            this.toaster.error(error.error.message);
        }
    })
}

gotoRiskOrCredit(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/gotoRiskOrCredit/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        console.log("Res-->",res);
        for(let item of res.riskOrCredit){
            this.goToRisk = item.goto;
            console.log("item.goto-->",item.goto);
        }
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

showLoader: boolean = false;
////////////////////DMS///////////////
getDocumentType(): void{
this.showLoader=true;
    let response;
    let fileName="dms/documentType";
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader=false;
    this.documentMaster = res;
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}


setDocDate(): void{
    this.deferralDocumentArray = [];
    for(let docType of this.documentMaster){
        if(docType.type == 5){
            for(let docCategory of docType.documentCategoryEntities){
                for(let docList of docCategory.documentListEntities){
                    if(docList.deferral == 2){
                        var date = new Date();
                        var days = docList.deferralTime+1;
                        date.setDate(date.getDate() + days);
                        var date1 = (<HTMLInputElement>document.getElementById(docList.id+'C'));
                        var dateString = date.toISOString().split('T')[0];
                        date1.value = dateString;
                        (<HTMLInputElement>document.getElementById(docList.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(docList.id+"F")).style.display = "none";
                        const b = { appId: this.cpId ,docListId : docList.id,initialTime : dateString,status : 0,deferralType : docList.type}
                        this.deferralDocumentArray.push(b);
                    }
                }
            }
        }
    }
}

storeDeferral(event,docListId,deferral,deferralType):void{
    let status = 0;
    let flag = 1;
        console.log("event.target.value",event.target.value);
    if(deferral == 2){
        if(event.target.value == 'No'){
            (<HTMLInputElement>document.getElementById(docListId+"E")).style.display = "block";
            (<HTMLInputElement>document.getElementById(docListId+"F")).style.display = "block";
            (<HTMLInputElement>document.getElementById(docListId)).style.display = "block";
        }else if(event.target.value == 'Yes'){
              (<HTMLInputElement>document.getElementById(docListId+"E")).style.display = "none";
              (<HTMLInputElement>document.getElementById(docListId+"F")).style.display = "none";
              (<HTMLInputElement>document.getElementById(docListId)).style.display = "none";
        }
    }
    if(event.target.value == 'Yes'){
        (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "block";
        status = 0;
    }else if(event.target.value == 'No'){
        (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "none";
        status = 2;
    }else if (event.target.value == -1){
        (<HTMLInputElement>document.getElementById(docListId+"C")).style.display = "none";
        status = -1;
    }
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.status = status;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.cpId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.cpId ,docListId : docListId,status : status,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
}
onKeyPressEvent(event: any){
event.preventDefault();
   console.log(event.target.value);
}

storeDeferralDate(event,docListId,deferralType):void{
event.preventDefault();
    let flag = 1;
    let date = event.target.value;
    if(this.deferralDocumentArray.length != 0){
        for(let itemId of this.deferralDocumentArray){
            if(itemId.docListId == docListId){
                let index = this.deferralDocumentArray.indexOf(itemId);
                itemId.initialTime = date;
                this.deferralDocumentArray[index] = itemId;
               flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const b = { appId: this.cpId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
    if(flag==0){
        const b = { appId: this.cpId ,docListId : docListId,initialTime : date,deferralType : deferralType,}
        this.deferralDocumentArray.push(b);
    }
}

saveDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
    let data={ appId : this.cpId, deferralReportsDataList : this.deferralDocumentArray, customerType : 2, rmName : this.docRMName, docProductCheck : true, constitution : 'Private Company',};
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.getDeferralDocReports();
        this.updateOtherDocuments();
//             this.OpsMakerDocUploadNp = false;
//             this.OpsMakerRemarksNp = true;
        this.docValidationCheck = false;
    },error=>{
    this.showLoader=false;
        this.docValidationCheck = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
    })
}

updateDeferralDocumentReports(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReport";
    let data={ appId : this.cpId, deferralReportsDataList : this.deferralDocumentArray, customerType : 2, rmName : this.docRMName, docProductCheck : true, constitution : 'Private Company',};
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader=false;
        this.getDeferralDocReports();
        this.updateOtherDocuments();
        //             this.OpsMakerDocUploadNp = false;
        //             this.OpsMakerRemarksNp = true;
        this.docValidationCheck = false;
    },error=>{
        this.showLoader=false;
        this.docValidationCheck = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }
    })
}

getDeferralDocumentReports(): void{
    if(this.deferralDocumentArray.length == 0){
        let response;
        let fileName="dms/deferralReport/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            this.deferralDocumentArray = [];
            if(res.length==0){
                this.setDocDate();
            }else{
                this.deferralCheck = true;
            }
            for(var item of res){
                this.docRMName = item.rmName;
                if(item.documentListEntity.deferral == 1){
                    if(item.status == 0){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"A")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "block";
                    }else if(item.status == 2){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"B")).checked = true;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).style.display = "none";
                    }
                }
                if(item.documentListEntity.deferral == 2){
                    if(item.status == 0){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"D")).value = "Yes";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).style.display = "none";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).style.display = "none";
                    }else if(item.status == 2){
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"D")).value = "No";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).value = item.documentId;

                        var dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                        var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).value = date;
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"E")).style.display = "block";
                        (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"F")).style.display = "block";
                    }
                }
                if(item.initialTime != null){
                    var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                    (<HTMLInputElement>document.getElementById(item.documentListEntity.id+"C")).value = date;
                }
                const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
                this.deferralDocumentArray.push(a);
            }
            console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
        },error=>{

                this.setDocDate();
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }
}

getDeferralDocReports(): void{

    let response;
    let fileName="dms/deferralReport/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {

        this.deferralDocumentArray = [];
        if(res.length==0){
            this.setDocDate();
        }else{
            this.deferralCheck = true;
        }
        for(var item of res){
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,initialTime : item.initialTime,status : item.status, documentId : item.documentId, docCompletionDate : item.docCompletionDate,deferralType : item.documentListEntity.type,}
            this.deferralDocumentArray.push(a);
        }
        console.log("this.deferralDocumentArray1",this.deferralDocumentArray);
    },error=>{

        this.setDocDate();
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

uploadDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key){

    this.loading = true;
    const file = event.target.files[0];
    if(this.assessmentType=='KYC' || this.assessmentType=='Financial' || this.assessmentType=='Cashflow'){
        this.assessmentTypeFlag=true;
    }
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            this.document=file;
            let url="dms/uploadFile";
            const data={ appId:this.cpId, docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, key:key
                        }
            const documentReports = JSON.stringify(data);
            formData.append('file',this.document);
            formData.append('documentReportsData',documentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {

                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                this.getDocumentsReports();
            },error=>{

                this.loading = false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                if(error.status==400){
                   if(error.error.message == null || error.error.message == ""){
                   this.toaster.error('Some Technical Error');
                   }else{
                    this.toaster.error(error.error.message);
                    }
                }

                (<HTMLInputElement>document.getElementById(docListId)).value = null;
            })
        }else{
            this.loading = false;
            this.toaster.error('Supported File Size is 100MB');
            (<HTMLInputElement>document.getElementById(docListId)).value = null;
        }
    }else{
        this.loading = false;
    }
}

deleteUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,key,fileName):void{

    let response;
    let url="dms/deleteFile";
    let data ={ appId:this.cpId, docTypeId:docTypeId, docTypeName:docTypeName, docCategoryId:docCategoryId,
                docCategoryName:docCategoryName, docListId:docListId, docListName:docListName, fileName:fileName, key:key }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {

        this.toaster.success('Successfully Deleted');
        this.getDocumentsReports();
        (<HTMLInputElement>document.getElementById(docListId)).value = null;
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }

    })
}

docCheckbox(event,docTypeId,docCategoryId,docListId): void{
    let mandatory = event.target.checked;
    let flag = 1;
    if(this.docValidationDataArray.length != 0){
        for(let item of this.docValidationDataArray){
            if(item.docTypeId == docTypeId && item.docCategoryId == docCategoryId && item.docListId == docListId){
                let index = this.docValidationDataArray.indexOf(item);
                const a = { appId:this.cpId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
                this.docValidationDataArray[index] = a;
                flag=1;
                break;
            }else{
                flag=0;
            }
        }
    }else{
        const a = { appId:this.cpId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
    if(flag==0){
        const a = { appId:this.cpId, mandatory:mandatory, docTypeId:docTypeId, docCategoryId:docCategoryId , docListId:docListId }
        this.docValidationDataArray.push(a);
    }
}

firstStageDocValidation(key): void{
    this.showLoader=true;
    this.docValidationCheck = true;
    this.updateCPBasicDetails(false);
    let response;
    let fileName="dms/docValidation";
    let data={ appId : this.cpId, customerType :key, docValidationData:this.docValidationDataArray,constitution : this.constitution, anchorType:this.anchorProduct, assessmentType:this.assessmentType }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {
    this.showLoader=false;
        this.docValidationCheck = false;
        if(key==2){
            if(this.assessmentType != null){
                if(this.goToRisk){

                    this.popupFun(6,2,1,"CP_RISK_HEAD_LEAD");
                }else{
                    this.popupFun(6,2,1,"CP_CREDIT_HEAD_LEAD");
                }
            }else{
                this.toaster.error("Please Select Assessment Type")
            }
        }
    },error=>{
    this.showLoader=false;
        this.docValidationCheck = false;
        if(error.status==500){
            if(this.assessmentType=='KYC' || this.assessmentType=='Financial' || this.assessmentType=='Cashflow'){
            }else{
                this.toaster.error("Kindly select assessment type");
            }
        }
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			}else{
        			this.toaster.error(error.error.message);
        			}
        		}

    })
}

documentValidation(key,val): void{
    this.showLoader=true;
    this.docValidationCheck = true;
    if(key == 4 || key == 5){
        for(var i=0;i<this.docReports.length;i++){
            if(this.docReports[i].customerType == 3){
                this.assessmentType = this.docReports[i].docListType;
                break;
            }else{
                this.assessmentType="KYC";
            }
        }
    }

    let response;
    let fileName="dms/docValidation";
    let data={ appId : this.cpId, customerType :key, docValidationData:this.docValidationDataArray,constitution : this.constitution, anchorType:this.anchorProduct, assessmentType:this.assessmentType }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res:any) => {

        this.showLoader=false;
        this.docValidationCheck=false;
        if(key==2){
            this.updateCPBasicDetails(false);
            this.uploadDataNp = false;
            this.businessRemarks = true;
            this.getRemarks();
            this.nextBusinessRemarks();
        }
        if(key==4){
            this.getRemarks();
            this.CAMUploadNp = false;
            this.CAMRemarks = true;
        }
        if(key==5){
            if(val == 'save'){
                this.saveDeferralDocumentReports();
            }else if(val == 'update'){
                this.updateDeferralDocumentReports();
            }
        }
    },error=>{
    this.showLoader=false;
        this.docValidationCheck=false;
        if(this.assessmentType=='KYC' || this.assessmentType=='Financial' || this.assessmentType=='Cashflow'){
        }else{
            this.toaster.error("Kindly select assessment type");
        }
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
       if(error.status==400)
       		{
       			if(error.error.message == null || error.error.message == ''){
       			this.toaster.error('Some Technical Error')
       			}else{
       			this.toaster.error(error.error.message);
       			}
       		}

    })
}


editUploadDataDocument():void{
    this.uploadDataEdit=false;
    this.getDocumentsReports();
}
saveUploadDataDocument():void{
    this.uploadDataEdit=true;
    this.getDocumentsReports();
}

editOpsDocument():void{
    this.opsMakerEdit=false;
    this.getDocumentsReports();
}
saveOpsDocument():void{
    this.opsMakerEdit=true;
    this.getDocumentsReports();
}

getDeferralReport(): void{
    this.showLoader=true;
    let response;
    let fileName="dms/deferralReports/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        console.log("Deferral Report :",res);
        if(res.deferralReports.length != 0){
            this.deferralDocReportArray = res.deferralReports;
            for(let item of res.deferralReports){
                if(item.initialTime != null){
                    let dateString = item.initialTime;
//                     let dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                    let date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.initialTime = date;
                }
                if(item.docCompletionDate != null){
                    let dateString = item.docCompletionDate ;
//                     let dateString = item.docCompletionDate[0]+"-"+item.docCompletionDate[1]+"-"+item.docCompletionDate[2];
                    let date = this.datePipe.transform(dateString, 'dd-MM-yyyy');
                    item.docCompletionDate = date;
                }
            }
            for(let item of res.deferralReports){
                if(item.rmName != null){
                    this.rmNames = item.rmName;
                }
                this.deferralDocView = true;
            }
        }else{

        }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }

    })
}

getDocumentsReports(): void{
    this.showLoader=true;
    let response;
    let fileName="dms/documentReports?appId="+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader=false;
        if(res.documentReports.length != 0){
            this.docReports = res.documentReports;
            for(let doc of res.documentReports){
                if(doc.customerType == 3){
                    this.assessmentTypeFlag=true;
                    break;
                }else{
                    this.assessmentTypeFlag=false;
                }
            }
        }else{
            this.docReports = null;
            this.assessmentTypeFlag=false;
        }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
                  this.authService.SignOut(this.refresh_token);
        }
    if(error.error.text=="No Value present"){
        this.assessmentTypeFlag=false;
    }

        this.docReports = null;
    })
}

downloadDocument(docMainName,docSubMainName,fileName){
    let response;
    let customerId = this.cpId;
    let url="dms/download/"+this.cpId+"/"+docMainName+"/"+docSubMainName+"/"+fileName;
    $.ajax({
        type: "GET",
        url: environment.baseUrl+url,
        headers: {
            'Authorization': localStorage.getItem('access_token')
        },
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            var windowUrl = window.URL || window.webkitURL;
            var url = windowUrl.createObjectURL(blob);
            var anchor = document.createElement('a');
            anchor.href = url;
            anchor.download = fileName;
            anchor.click();
        },
        error: function (error) {

        }
    });
}


// DMS for Other Document

saveOtherDocuments(){
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocumentMaster";
    let data={ appId : this.cpId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.docRMName, constitution : this.constitution }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerRemarksNp = true;
        this.getOtherDocMaster();
        console.log("Response:::",res);
    },error=>{
    this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error Contact admin.");
        }
    })
}

updateOtherDocuments(): void {
    this.showLoader=true;
    let response;
    let fileName="dms/otherDocumentMaster";
    let data={ appId : this.cpId, otherDocumentDataList : this.otherDocumentArray, customerType : 1, rmName : this.docRMName, constitution : this.constitution }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.showLoader = false;
        this.OpsMakerDocUploadNp = false;
        this.OpsMakerRemarksNp = true;
        this.getOtherDocMaster();
        console.log("Response:::",res);
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
        if(error.status==500){
            this.toaster.error("Some Technical Error Contact admin.");
        }
    })
}


getOtherDocMaster(): void{
    this.showLoader = true;
    let response;
    let fileName="dms/otherDocumentMaster/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        this.showLoader = false;
        this.otherDocumentArray = [];
        if(res.length != 0){
            for(let val of res){
                if(val.deferralType == 1){
                    this.otherDocDefLabelView = true;
                }else if(val.deferralType == 0){
                    this.otherDocLabelView = true;
                }
            }
        }
        for(let item of res){
            this.docRMName = item.rmName;
            if(item.initialTime != null){
                var dateString = item.initialTime[0]+"-"+item.initialTime[1]+"-"+item.initialTime[2];
                var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
                item.initialTime = date;
            }
            const a = { id : item.id, appId: item.applicationEntity.id ,docListId : item.documentListEntity.id,displayName : item.displayName,initialTime : item.initialTime,status : item.status.toString(),deferralType : item.deferralType,deferral : item.documentListEntity.deferral,}
            this.otherDocumentArray.push(a);
        }
        this.getOtherDocDownload();
        console.log("otherDocumentArray",this.otherDocumentArray);
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

getOtherMasterWithPId(id,index): void{
    this.showLoader = true;
    let response;
    let fileName="dms/getOtherMaster/"+id;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
        if(res.initialTime != null){
            var dateString = res.initialTime[0]+"-"+res.initialTime[1]+"-"+res.initialTime[2];
            var date = this.datePipe.transform(dateString, 'yyyy-MM-dd');
            res.initialTime = date;
        }
        const a = { id : res.id, appId: res.applicationEntity.id ,docListId : res.documentListEntity.id,displayName : res.displayName,initialTime : res.initialTime,status : res.status.toString(),deferralType : res.deferralType,deferral : res.documentListEntity.deferral,}
        this.otherDocumentArray[index] = a;
        console.log("otherDocumentArray",this.otherDocumentArray);
        this.showLoader = false;
    },error=>{
        this.showLoader = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
    })
}

uploadOtherDocument(event,docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key,index){
    console.log("otherDocMasterId--->",otherDocMasterId);
    console.log("otherDisplayName--->",otherDisplayName);
    this.loading = true;
    const file = event.target.files[0];
    if(file != undefined){
        if(file.size <= 105906176){
            let response;
            let formData = new FormData();
            this.document=file;
            let url="dms/otherUploadFile";
            const data={ appId:this.cpId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                         docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName, key:key, rmName : this.rmName
                        }
            const otherDocumentReports = JSON.stringify(data);
            formData.append('file',this.document);
            formData.append('otherDocumentReportsData',otherDocumentReports);
            response = this.requestapi.postFileData(url,formData);
            response.subscribe((res: any) => {
                this.loading = false;
                this.toaster.success('Successfully Uploaded');
                const jsonString = res.FileName;
                const json = JSON.parse(jsonString);
                console.log("json--->",json);
                console.log("otherDocMasterId",json[0].otherDocMasterId);
                if(otherDocMasterId == undefined){
                    this.getOtherMasterWithPId(json[0].otherDocMasterId,index);
                }
                this.getOtherDocDownload();
            },error=>{

                this.loading = false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }
                if(error.status==400){
                   if(error.error.message == null || error.error.message == ""){
                   this.toaster.error('Some Technical Error');
                   }else{
                    this.toaster.error(error.error.message);
                    }
                }

                (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
            })
        }else{
            this.loading = false;
            this.toaster.error('Supported File Size is 100MB');
            (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
        }
    }else{
        this.loading = false;
    }
}

deleteOtherUploadedFile(docTypeId,docTypeName,docCategoryId,docCategoryName,docListId,docListName,otherDocMasterId,otherDisplayName,key,fileName,index):void{

    let response;
    let url="dms/deleteOtherFile";
    const data={ appId:this.cpId, otherDocumentDataList : this.otherDocumentArray,docTypeId:docTypeId, docTypeName:docTypeName,docCategoryId:docCategoryId,
                 docCategoryName:docCategoryName,docListId:docListId,docListName:docListName, otherDocMasterId:otherDocMasterId, otherDisplayName:otherDisplayName,fileName:fileName, key:key, rmName : this.rmName
                }
    response = this.requestapi.postData(url,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.toaster.success('Successfully Deleted');
        this.getOtherDocDownload();
        (<HTMLInputElement>document.getElementById(docListId+"F"+index)).value = null;
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
            this.toaster.error('Some Technical Error')
            }else{
            this.toaster.error(error.error.message);
            }
        }
//         this.toaster.error('File Already Deleted');

    })
}

deleteOtherDocRecord(index,id): void{
    this.showLoader=true;
    let response;
    let fileName="dms/deleteOtherDocRecord/"+this.cpId+"/"+id;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res:any) => {
        this.otherDocumentArray.splice(index, 1);
//         this.getOtherDocMaster();
        this.getOtherDocDownload();
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            this.toaster.error(error.error.message);
        }
    })
}

getOtherDocDownload(): void{
// this.showLoader=true;
    let response;
    let fileName="dms/customerOtherDocReports?appId="+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
//         this.showLoader=false;
        this.OtherDocReports = res.otherDocReports;
        console.log("this.OtherDocReports",this.OtherDocReports);
    },error=>{
//         this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        this.OtherDocReports = null;
    })
}

downloadOtherDocument(docMainName,docSubMainName,otherDocName,fileName){
    let response;
    let customerId = this.cpId;
    let url="dms/otherDownload/"+this.cpId+"/"+docMainName+"/"+docSubMainName+"/"+otherDocName+"/"+fileName;
    $.ajax({
        type: "GET",
        url: environment.baseUrl+url,
        headers: {
            'Authorization': localStorage.getItem('access_token')
        },
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            var windowUrl = window.URL || window.webkitURL;
            var url = windowUrl.createObjectURL(blob);
            var anchor = document.createElement('a');
            anchor.href = url;
            anchor.download = fileName;
            anchor.click();
        },
        error: function (error) {

        }
    });
}

/////////////Upload Data////////////
selectAssessmentType(event):void{
    console.log("anchorProduct--->",this.anchorProduct);
    this.assessmentType=event.target.value;
    console.log("assessmentType--->",this.assessmentType);
    this.enableUploadDataCredit = true;
    this.enableUploadDataKyc = true;
}



    ///////////////////////////////Get Proposal////////////////
    getTermSheet(): void {

        let response;
        let fileName="counterParty/termSheetGet/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {

        this.termSheetList=res
        },error=>{;

        if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    ///////////////////Get Credit Norms or Soft policy/////////////////////////
    getSoftPolicyMaster(): void{

        let response;
        let fileName="counterParty/softPolicyMasterType";
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {

        this.SoftPolicyMasterList=res;
        },error=>{

        if(error.status==401){
               this.refresh_token=localStorage.getItem('refresh_token')
                       this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    storeSoftPolicy(id,item: any,dataType):void{
        this.creditNormDetails = [];
        this.softPolicyResult = [];
        this.softPolicyDealerResult = [];
        this.softPolicyVendorResult = [];
        this.saveSoftPolicyFlag=true;
        this.softPolicyValue=item.target.value;
        this.softPolicyId=id;
        this.enableSoftPolicy=1;
        if(this.softPolicyArray.length != 0){
            for(let itemId of this.softPolicyArray){
                if(itemId.softPolicyId == this.softPolicyId){
                    if(this.updateSoftPolicyFlag){
                        let index = this.softPolicyArray.indexOf(itemId);
                        let pId = itemId.id;
                        const a = { id:pId, appId: this.cpId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
                        this.softPolicyArray[index] = a;
                        this.enableSoftPolicy=1;
                    }else{
                        let index = this.softPolicyArray.indexOf(itemId);
                        const a = { appId: this.cpId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
                        this.softPolicyArray[index] = a;
                        this.enableSoftPolicy=1;
                    }
                    break;
                }else{
                    this.enableSoftPolicy=0;
                }
            }
        }else{
            const a = { appId: this.cpId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
            this.softPolicyArray.push(a);
            const b = { appId: this.cpId ,softPolicyId : 164,value : this.cpState}
            this.softPolicyArray.push(b);
        }
        if(this.enableSoftPolicy==0){
            const a = { appId: this.cpId ,softPolicyId : this.softPolicyId,value : this.softPolicyValue}
            this.softPolicyArray.push(a);
        }
    }

    validateSoftPolicy(): void{
        let response;
        let fileName="counterParty/validateSoftPolicy/";
        let data={ softPolicyDetailsDataList : this.softPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
            this.runSoftPolicy();
        },error=>{
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}
        })
    }

    runSoftPolicy(): void {
        this.showLoader=true;
        let response;
        let fileName="counterParty/runSoftPolicy/"+this.cpId;
        let data={ softPolicyDetailsDataList : this.softPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        console.log("res.softPolicyResult",res.softPolicyResult);
        if(res.flag){
            this.anchorType = true;
            this.softPolicyDealerResult = res.softPolicyResult[0].dealer;
            this.softPolicyVendorResult = res.softPolicyResult[1].vendor;
        }else{
            this.anchorType = false;
            this.softPolicyResult = res.softPolicyResult;
        }
        this.saveSoftPolicyFlag= false;
        },error=>{
        this.showLoader=false;
          if(error.status==401){
          this.refresh_token=localStorage.getItem('refresh_token')
          this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}
        })
    }

    getSoftPolicyDetails(): void{

        let response;
        let fileName="counterParty/softPolicyDetails/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            this.updateSoftPolicyFlag = true;
            this.softPolicyArray = [];
            this.softPolicyArray = res.softPolicyDetailsDataList;
            this.runSoftPolicy();
        },error=>{

            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

getUpdateSoftPolicyDetails(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/softPolicyDetails/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader=false;
        if(res != 'empty'){
            this.softPolicyArray = res.softPolicyDetailsDataList;
            this.updateSoftPolicyFlag = true;
            this.saveSoftPolicyFlag = false;
            this.softPolicyArray = [];
            for(var i=0;i<this.SoftPolicyMasterList.length;i++){
                for(let subItem of this.SoftPolicyMasterList[i].softPolicyMasterSubTypeEntities){
                    for(let itemValue of res.softPolicyDetailsDataList){
                        if(subItem.id == itemValue.softPolicyId){
                            (<HTMLInputElement>document.getElementById(subItem.name)).value = itemValue.value;
                            const a1 = { id:itemValue.id, appId: itemValue.appId ,softPolicyId : itemValue.softPolicyId,value : itemValue.value}
                            this.softPolicyArray.push(a1);
                        }
                    }
                }
            }
        }else{
        }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
   }
   })
}

softPolicyPopUp(stageId,status,index,leadGrp){
    Swal.fire({
        title: 'Are you sure to submit?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                    `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        this.saveWf(stageId,status,result.value,"CREDIT_HEAD_LEAD");
        this.wizard.goToStep(index);
        this.router.navigate(['/dashboard/inbox']);
    })
}

eleventhSubmit(eleventhSubmit,i,j,k){
    if(!this.deferralStatus){
        this.popupFun(eleventhSubmit,i,j,'CP_OPERATION_CHECKER_LEAD');

    }else{
        this.popupFun(eleventhSubmit,i,j,'CP_DEFERRAL_COMMITTEE_LEAD');
    }
}

 creditCpaSubmit(){
    if(this.goToRisk){
        this.popupFun(9,2,3,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupFun(9,2,3,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}

softPolicyDetailsSubmit(key){
        this.saveSoftPolicyFlag = true;
        if(key == 'save'){
            this.saveSoftPolicy();
        }else if(key == 'update'){
            this.updateSoftPolicy();
        }
}
creditPolicyDetailsSubmit(key){
 let flag = false;
    let flagvalues = true;
    for (let items of this.creditProposalList){
          if(items.creditPolicyCheck == true){
            flagvalues = false;
          }
    }
     console.log(flagvalues,'flagvalues1')
    for(let item of this.creditProposalList){
       this.creditPolicyCheckResults = item.creditPolicyCheck;
       console.log(this.creditPolicyCheckResults,'this.creditPolicyCheckResults')
       if(item.creditPolicyCheck != null && (!flagvalues)){
           flag = true;
        }else{
           flag = false;
            break;
        }
    }
    for (let items of this.creditPolicyResults){
              if(items.status == true){
                flag = true;
              }
        }
    if(flag){
            if(key == 'save'){
                this.saveCreditPolicy();
                this.updateCreditProposal('NA');
            }else if(key == 'update'){
                this.updateCreditPolicy();
                this.updateCreditProposal('NA');
             }
        }else{
              this.toaster.error("Kindly take action for all result & minimum one should be approved");
             }
}



saveSoftPolicy(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/softPolicyDetails";
    let data={ softPolicyDetailsDataList : this.softPolicyArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Saved');
        this.getSoftPolicyDetails();
         this.softPolicyNp = false;
         this.creditCpaRemarks = true;
         this.saveSoftPolicyFlag = false;
    },error=>{
    this.showLoader=false;
        this.saveSoftPolicyFlag = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
        				}else{
        				this.toaster.error(error.error.message);
        				}
        			}

    })
}

updateSoftPolicy(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/softPolicyDetails";
    let data={ softPolicyDetailsDataList : this.softPolicyArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.softPolicyNp = false;
        this.creditCpaRemarks = true;
        this.saveSoftPolicyFlag = false;
        this.getSoftPolicyDetails();
    },error=>{
    this.showLoader=false;
        this.saveSoftPolicyFlag = false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                this.toaster.error(error.error.message);
            }
        }

    })
}

    //////////////////////////DebtProfile//////////////////////
    getDebtProfileById(): void {
       this.showLoader=true;
        let response
        let fileName="counterParty/cpDebtProfile/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.debtProfileList=res;
            if(res.length > 1 && this.debtProfileArray.length != res.length){
                for(var j=1;j<res.length;j++){
                    this.addDebtProfileValue();
                }
            }
            if(res.length != 0){
                this.DebtProfile2=true;
                for(var i=0;i<res.length;i++){
                    this.debtProfileArray[i].id = res[i].id;
                    this.debtProfileArray[i].appId = res[i].applicationEntity.id;
                    this.debtProfileArray[i].bankFI = res[i].bankFI;
                    this.debtProfileArray[i].facilityType = res[i].facilityType;
                    this.debtProfileArray[i].tenure = res[i].tenure;
                    this.debtProfileArray[i].sanctionDate1 = this.datePipe.transform(res[i].sanctionDate, 'yyyy-MM-dd');
                    this.debtProfileArray[i].sanctionLimit = res[i].sanctionLimit;
                    this.debtProfileArray[i].outstandingOnDate = res[i].outstandingOnDate;
                    this.debtProfileArray[i].emi = res[i].emi;
                    this.debtProfileArray[i].interestRate = res[i].interestRate;
                    this.debtProfileArray[i].security = res[i].security;
                    this.debtProfileArray[i].specificLimit = res[i].specificLimit;
                    this.debtProfileArray[i].remarks = res[i].remarks;
                }
            }
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }


    public debtProfileSubmit(key) {
        this.debtProfileValidate = false;
        this.debtProfileValidate = !this.debtProfileValidate;
        var debtProfileFlag = true;
        for(let debtProfileArr of this.debtProfileArray){
        if(debtProfileArr.bankFI=="" || debtProfileArr.bankFI==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.facilityType=="" || debtProfileArr.facilityType==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.tenure=="" || debtProfileArr.tenure==null){
            debtProfileFlag=false;
        }
       if(debtProfileArr.sanctionLimit=="" || debtProfileArr.sanctionLimit==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.outstandingOnDate=="" || debtProfileArr.outstandingOnDate==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.emi=="" || debtProfileArr.emi==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.interestRate=="" || debtProfileArr.interestRate==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.security=="" || debtProfileArr.security==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.specificLimit=="" || debtProfileArr.specificLimit==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.remarks=="" || debtProfileArr.remarks==null){
            debtProfileFlag=false;
        }
        if(debtProfileArr.sanctionDate1!='' || debtProfileArr.sanctionDate1!=null){
            debtProfileArr.sanctionDate=this.datePipe.transform(debtProfileArr.sanctionDate1, 'MM-dd-yyyy');
        }
        if(debtProfileArr.sanctionDate1=='' || debtProfileArr.sanctionDate1==null){
            debtProfileFlag = false;
        }
        }
        if(this.debtProfileValidate==true && debtProfileFlag == true ){
            if(key == 'save'){
                this.saveDebtProfile();
            }else if(key == 'update'){
                this.updateDebtProfile();
            }

        }
    }

    getDebtProfile(): void {
        this.showLoader=true;
        let response
        let fileName="counterParty/cpDebtProfile/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.debtProfileList=res;
            if(res.length != 0){
                this.DebtProfile2=true;
                for(var i=0;i<res.length;i++){
                    this.debtProfileArray[i].id = res[i].id;
                    this.debtProfileArray[i].appId = res[i].applicationEntity.id;
                    this.debtProfileArray[i].bankFI = res[i].bankFI;
                    this.debtProfileArray[i].facilityType = res[i].facilityType;
                    this.debtProfileArray[i].tenure = res[i].tenure;
                    this.debtProfileArray[i].sanctionDate1 = this.datePipe.transform(res[i].sanctionDate, 'yyyy-MM-dd');
                    this.debtProfileArray[i].sanctionLimit = res[i].sanctionLimit;
                    this.debtProfileArray[i].outstandingOnDate = res[i].outstandingOnDate;
                    this.debtProfileArray[i].emi = res[i].emi;
                    this.debtProfileArray[i].interestRate = res[i].interestRate;
                    this.debtProfileArray[i].security = res[i].security;
                    this.debtProfileArray[i].specificLimit = res[i].specificLimit;
                    this.debtProfileArray[i].remarks = res[i].remarks;
                }
            }
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

saveDebtProfile(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/cpDebtProfile/"+this.cpId;
    let data={ cpDebtProfileDataList:this.debtProfileArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
//         this.DebtProfile1 = false;
        this.DebtProfile2 = true;
        this.toaster.success('Successfully Saved')
        this.getDebtProfile();
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        	if(error.status==400)
        			{
        				if(error.error.message == null || error.error.message == ''){
        				this.toaster.error('Some Technical Error')
//         				this.DebtProfile1 = true;
        				}else{
                            var msg =error.error.message;
                            for(let i=0 ; i<this.debtProfileArray.length;i++){
                                // Use of String replace() Method
                                msg = msg.replaceAll("cpDebtProfileDataList["+i+"]", i+1);
                            }
                            this.toaster.error(msg);
//         				this.DebtProfile1 = true;
        				}
        			}
    })
}

updateDebtProfile(): void {
    this.showLoader=true;
    let response;
    let fileName="counterParty/cpDebtProfile/"+this.cpId;
    let data={ cpDebtProfileDataList : this.debtProfileArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
        this.getDebtProfile();
        this.showLoader=false;
        this.toaster.success('Successfully Updated');
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
            }else{
                var msg =error.error.message;
                for(let i=0 ; i<this.debtProfileArray.length;i++){
                    // Use of String replace() Method
                    msg = msg.replaceAll("cpDebtProfileDataList["+i+"]", i+1);
                }
                this.toaster.error(msg);
            }
        }

    })
}

deleteDebtProfile(index,id,clearKey): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/cpDebtProfile/"+id;
    response = this.requestapi.deleteData(fileName);
    response.subscribe((res:any) => {
        if(clearKey){
            this.debtProfileArray.splice(index, 1);
        }else{
            this.debtProfileArray.splice(index, 1);
            this.debtProfileValidate = false;
            this.debtProfileArray.push(this.newDebtAttribute)
            this.newDebtAttribute = {};
        }
        console.log("this.debtProfileArray.length",this.debtProfileArray.length);
        if(this.debtProfileArray.length == 1){
            console.log("this.debtProfileArray== 1",this.debtProfileArray.length);
            for(let debtProfileArr of this.debtProfileArray){
                if(debtProfileArr.bankFI=="" || debtProfileArr.bankFI==null){
                    this.DebtProfile2=false;
                    break;
                }
                if(debtProfileArr.facilityType=="" || debtProfileArr.facilityType==null){
                    this.DebtProfile2=false;
                    break;
                }
                if(debtProfileArr.tenure=="" || debtProfileArr.tenure==null){
                    this.DebtProfile2=false;
                    break;
                }
                if(debtProfileArr.sanctionLimit=="" || debtProfileArr.sanctionLimit==null){
                    this.DebtProfile2=false;
                    break;
                }
                if(debtProfileArr.outstandingOnDate=="" || debtProfileArr.outstandingOnDate==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.emi=="" || debtProfileArr.emi==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.interestRate=="" || debtProfileArr.interestRate==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.security=="" || debtProfileArr.security==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.specificLimit=="" || debtProfileArr.specificLimit==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.remarks=="" || debtProfileArr.remarks==null){
                   this.DebtProfile2=false;
                   break;
                }
                if(debtProfileArr.sanctionDate1=='' || debtProfileArr.sanctionDate1==null){
                   this.DebtProfile2=false;
                   break;
                }
            }
        }
        this.showLoader=false;
    },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400){
            this.toaster.error(error.error.message);
        }
    })
}

camUploadSubmit(){
    if(this.goToRisk){
        this.popupFun(this.fifthId,2,5,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupFun(this.fifthId,2,5,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}


//     saveCommercialCc(): void {
//        this.showLoader=true;
//         let response;
//         let fileName="counterParty/commercialCc";
//         let data={ appId:this.cpId,productRemarks:this.productRemarks,anchorNameRemarks:this.nameRemarks,regularLimitRemarks:this.regularLimitRemarks,adhocLimitRemarks:this.adhocRemarks,creditPeriodRemarks:this.creditPeriodRemarks,
//             doorRemarks:this.DoorRemarks,invoiceAgeingRemarks:this.invoiceRemarks,marginRemarks:this.marginRemarks,interestRateRemarks:this.interestRemarks,pfRemarks:this.pfRemarks,renewalRemarks:this.renewalRemarks,
//             interestTypeRemarks:this.interestTypeRemarks,renewalPeriodRemarks:this.renewalPeriodRemarks,createdBy:"",updatedBy:""
//         }
//         response = this.requestapi.postData(fileName,JSON.stringify(data));
//         response.subscribe((res: any) => {
//         this.showLoader=false;
//            this.commercial1  = false;
//             this.commercial2  = true;
//             this.toaster.success('Successfully Saved')
//             this.getCommercialCcGetById();
//         },error=>{
//         this.showLoader=false;
//         if(error.status==401){
//                         this.refresh_token=localStorage.getItem('refresh_token')
//                                 this.authService.SignOut(this.refresh_token);
//                            }
//             this.commercial1  = true;
//
//         })
//     }


saveCommercialCc(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/commercialCc/"+this.cpId;
        let data={commercialDataList:this.commercialArray}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
           this.commercial1  = false;
            this.commercial2  = true;
            this.toaster.success('Successfully Saved')
            this.getCommercialCcGetById();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                        this.refresh_token=localStorage.getItem('refresh_token')
                                this.authService.SignOut(this.refresh_token);
                           }
						   if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			this.commercial1 = true;
        			}else{
                        var msg =error.error.message;
                        for(let i=0 ; i<this.commercialArray    .length;i++){
                            // Use of String replace() Method
                            msg = msg.replaceAll("commercialDataList["+i+"]", i+1);
                        }
                        this.toaster.error(msg);
                      this.commercial1  = true;
					  }
}
        })
    }

    updateCommercialCc():void{

    this.showLoader=true;
            let response;
            let fileName="counterParty/commercialCc";
           let data={commercialDataList:this.commercialArray}
            response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
               this.commercial1  = false;
                this.commercial2  = true;
                this.toaster.success('Successfully Updated')
            },error=>{
            this.showLoader=false;
            if(error.status==401){
                            this.refresh_token=localStorage.getItem('refresh_token')
                                    this.authService.SignOut(this.refresh_token);
                               }
                this.commercial1  = true;

            })

    }

getCommercialCcGetById(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/commercialCc/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
    console.log("1")
        this.commercialCcList=res;
//       var len = res.length-1
//       for(var j=0;j< len;j++){
//        this.addCommercialCcValue()
//       }
       console.log("Yes he is here")
        if(res.length != 0){
        console.log("Yes he is here")
          this.commercial2 = true
            for(var i=0;i< res.length;i++){
                this.commercialArray[i].id = res[i].id;
                this.commercialArray[i].appId= res[i].applicationEntity.id;
                this.commercialArray[i].productRemarks = res[i].productRemarks;
                this.commercialArray[i].anchorNameRemarks = res[i].anchorNameRemarks;
                this.commercialArray[i].regularLimitRemarks = res[i].regularLimitRemarks;
                this.commercialArray[i].adhocLimitRemarks = res[i].adhocLimitRemarks;
                this.commercialArray[i].creditPeriodRemarks = res[i].creditPeriodRemarks;
                this.commercialArray[i].doorRemarks = res[i].doorRemarks;
                this.commercialArray[i].invoiceAgeingRemarks = res[i].invoiceAgeingRemarks;
                this.commercialArray[i].marginRemarks = res[i].marginRemarks;
                this.commercialArray[i].interestRateRemarks = res[i].interestRateRemarks;
                this.commercialArray[i].pfRemarks = res[i].pfRemarks;
                this.commercialArray[i].renewalRemarks = res[i].renewalRemarks;
                this.commercialArray[i].interestTypeRemarks = res[i].interestTypeRemarks;
                this.commercialArray[i].renewalPeriodRemarks = res[i].renewalPeriodRemarks;
            }
        }
        console.log("this.commercialArray ::::",this.commercialArray)
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


    //////////////////////////Limit Eligibility////////////////
    getFundRequirementQuestion(): void{

        let response;
        let fileName="counterParty/fundReqMaster";
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

        this.fundQuestion = res;
        },error=>{

        if(error.status==401){
                       this.refresh_token=localStorage.getItem('refresh_token')
                               this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    getFundRequirement(): void{

        let response;
        let fileName="counterParty/fundReqDetails/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            if(res.length !=0){
                this.fundReqDetails = res;
            }
        },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    getUpdateFundRequirement(): void{
this.showLoader=true;
            let response;
            let fileName="counterParty/fundReqDetails/"+this.cpId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res:any) => {
            this.showLoader=false;
                if(res.length !=0){
                    this.saveFundReqFlag=true;
                    this.fundReqArray = [];
                    for(let item of this.fundQuestion){
                        for(var i=0; i<res.length; i++){
                            if(item.id == res[i].fundRequirementMasterEntity.id){
                                (<HTMLInputElement>document.getElementById(item.id)).value = res[i].value;
                                const b = { id:res[i].id, appId: this.cpId ,cpFrmId : res[i].fundRequirementMasterEntity.id,value : res[i].value}
                                this.fundReqArray.push(b);
                            }
                        }
                    }
                }
            },error=>{
            this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                               }

            })
        }

    storeAnswer(item: any,id):void{
        if(Event.prototype.isPrototypeOf(item)){
            this.fundReqValue=item.target.value;
        }else{
            this.fundReqValue=item.toString();
        }
        this.fundReqId=id;
        this.enableFundReq=1;
        if(this.fundReqArray.length != 0){
            for(let itemId of this.fundReqArray){
                if(itemId.cpFrmId == this.fundReqId){
                    let index = this.fundReqArray.indexOf(itemId);
                    if(this.saveFundReqFlag){
                        var pId = itemId.id;
                        const b = { id:pId, appId: this.cpId ,cpFrmId : this.fundReqId,value : this.fundReqValue}
                        this.fundReqArray[index] = b;
                    }else{
                        const b = { appId: this.cpId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
                        this.fundReqArray[index] = b;
                    }
                    this.enableFundReq=1;
                    break;
                }else{
                    this.enableFundReq=0;
                }
            }
        }else{
            const b = { appId: this.cpId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
            this.fundReqArray.push(b);
        }
        if(this.enableFundReq==0){
            const b = { appId: this.cpId ,cpFrmId : this.fundReqId,value : this.fundReqValue,}
            this.fundReqArray.push(b);
        }
    }

    public fundRequirementSubmit(key) {
        this.fundRequirementControl = true;
        var fundRequirementFlag = true;
        if(this.fundReqArray.length == this.fundQuestion.length){
            fundRequirementFlag=true;
        }else{
            fundRequirementFlag=false;
            this.fundRequirementControl = false;
        }
        if(fundRequirementFlag == true ){
            if(key == 'save'){
                this.saveFundRequirement();
            }else if(key == 'update'){
                this.updateFundRequirement();
            }
        }else{
            this.fundRequirementControl = false;
            this.toaster.error('Kindly fill all field');
        }
    }

    saveFundRequirement(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/fundReqDetails";
        let data={ fundReqDetailsDataList : this.fundReqArray}
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.saveFundReqFlag=true;
            this.fundRequirementControl = false;
            this.getUpdateFundRequirement();
            this.toaster.success('Successfully Saved')
        },error=>{
        this.showLoader=false;
        this.fundRequirementControl = false;
        if(error.status==401){
                     this.refresh_token=localStorage.getItem('refresh_token')
                             this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    updateFundRequirement(): void {
            this.showLoader=true;
            let response;
            let fileName="counterParty/fundReqDetails";
            let data={ fundReqDetailsDataList : this.fundReqArray}
            response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
            this.getUpdateFundRequirement();
            this.fundRequirementControl = false;
                this.toaster.success('Successfully Updated');
            },error=>{
            this.showLoader=false;
            this.fundRequirementControl = false;
            if(error.status==401){
                         this.refresh_token=localStorage.getItem('refresh_token')
                                 this.authService.SignOut(this.refresh_token);
                               }

            })
        }


    public limitEligibilitySubmit(key) {
    console.log("Coming............")
    console.log(this.limitEligibilityArray,"Looking for")
        this.limitValidate = false;
        this.limitValidate = !this.limitValidate;
        var limitFlag = true;
        for(let limitEligibility of this.limitEligibilityArray){
            if(limitEligibility.custId=="" || limitEligibility.custId==null){
                limitFlag=false;
                console.log("1")
            }
            if(limitEligibility.product=="" || limitEligibility.product==null){
             console.log("2")
                limitFlag=false;
            }
            if(limitEligibility.currentLimit=="" || limitEligibility.currentLimit==null){
             console.log("3")
                limitFlag=false;
            }
            if(limitEligibility.proposedLimit=="" || limitEligibility.proposedLimit==null){
             console.log("4")
                limitFlag=false;
            }
            if(limitEligibility.eligibleLimit=="" || limitEligibility.eligibleLimit==null){
             console.log("5")
                 limitFlag=false;
            }
            if(limitEligibility.adhocLimit=="" || limitEligibility.adhocLimit==null){
             console.log("6")
                limitFlag=false;
            }
            if(limitEligibility.creditPeriod=="" || limitEligibility.creditPeriod==null){
             console.log("7")
                limitFlag=false;
            }
//             if(limitEligibility.doortoDoor=="" || limitEligibility.doortoDoor==null){
//                 limitFlag=false;
//             }
//             if(limitEligibility.invoiceAgeing=="" || limitEligibility.invoiceAgeing==null){
//                 limitFlag=false;
//             }
//             if(limitEligibility.margin=="" || limitEligibility.margin==null){
//                 limitFlag=false;
//             }
             if(limitEligibility.expectedGrowth=="" || limitEligibility.expectedGrowth==null){
             console.log("8")
                limitFlag=false;
             }
             if(limitEligibility.monthlyAverage=="" || limitEligibility.monthlyAverage==null){
             console.log("9")
                limitFlag=false;
             }
//             if(limitEligibility.calculatedLimitWoSetOff=="" || limitEligibility.calculatedLimitWoSetOff==null){
//                 limitFlag=false;
//             }
            if(limitEligibility.approtionedLimits=="" || limitEligibility.approtionedLimits==null){
             console.log("10")
                limitFlag=false;
            }
            if(limitEligibility.existingScfLimits=="" || limitEligibility.existingScfLimits==null){
             console.log("11")
                limitFlag=false;
            }
            if(limitEligibility.modelLimit=="" || limitEligibility.modelLimit==null){
             console.log("12")
                limitFlag=false;
            }
//             if(limitEligibility.customerRequestedAmount=="" || limitEligibility.customerRequestedAmount==null){
//              console.log("13")
//                 limitFlag=false;
//             }
            if(limitEligibility.anchorRecommendedAmount=="" || limitEligibility.anchorRecommendedAmount==null){
             console.log("14")
                limitFlag=false;
            }
//             if(limitEligibility.receivables=="" || limitEligibility.receivables==null){
//                  limitFlag=false;
//                         }
//             if(limitEligibility.inventory=="" || limitEligibility.inventory==null){
//                             limitFlag=false;
//                         }
//             if(limitEligibility.creditor=="" || limitEligibility.creditor==null){
//                             limitFlag=false;
//                         }
            if(limitEligibility.expectedMonthlyTurnOverWithAnchor==null){
             console.log("15")
                            limitFlag=false;
             }
            if(limitEligibility.modelAdhocLimit==null){
            console.log("16")
                            limitFlag=false;
             }
           }
        if(this.limitValidate==true && limitFlag == true ){
            if(key == 'save'){
                this.saveLimitEligibility();
            }else if(key == 'update'){
                this.updateLimitEligibility();
            }
        }
    }

    saveLimitEligibility(): void{

       this.showLoader=true;
        let response;
        let fileName="counterParty/limitEligibility/"+this.cpId;
        let data={ limitEligibilityDetailsData:this.limitEligibilityArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
         this.cpLimitEligibility1 = false;
         this.cpLimitEligibility2 = true;
         this.getLimitEligibilityById();
            this.toaster.success('Successfully Saved')
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                   this.refresh_token=localStorage.getItem('refresh_token')
                           this.authService.SignOut(this.refresh_token);
                           }
           if(error.status==400)
           		{
           			if(error.error.message == null || error.error.message == ''){
           			this.toaster.error('Some Technical Error')
           			this.cpLimitEligibility1 = true;
           			}else{
                        var msg =error.error.message;
                        for(let i=0 ; i<this.limitEligibilityArray.length;i++){
                            // Use of String replace() Method
                            msg = msg.replaceAll("limitEligibilityDetailsData["+i+"]", i+1);
                        }
                        this.toaster.error(msg);
           			this.cpLimitEligibility1 = true;
           			}
           		}


        })
    }

//     multiSaveLimitEligibility(): void{
//
//     for(i=0;i<this.limitEligibilityMultipleArray.length;i++)
//
//     }

    updateLimitEligibility(): void {
        this.showLoader=true;
        let response;
        let fileName="counterParty/limitEligibility";
        let data={ limitEligibilityDetailsData : this.limitEligibilityArray }
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.getLimitEligibilityById();
            this.toaster.success('Successfully Updated');
        },error=>{
        this.showLoader=false;
            if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
            {
                if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                }else{
                    var msg =error.error.message;
                    for(let i=0 ; i<this.limitEligibilityArray.length;i++){
                        // Use of String replace() Method
                        msg = msg.replaceAll("limitEligibilityDetailsData["+i+"]", i+1);
                    }
                    this.toaster.error(msg);
                }
            }

        })
    }

    ///////////////////////////Term Sheet/////////////////////////////////////
    public termSheetSubmit(key) {
        this.termSheetValidate = false;
        this.termSheetValidate = !this.termSheetValidate;


        const invoiceFund =/^\d{1,3}(\.\d+)?$/;
        const graceDay = /^[0-9]{1,4}$/;



        var termSheetFlag = true;

        for(let termSheet of this.termSheetArray){
//         termSheet.interestRateType='Fixed';
        console.log("termSheet.graceDays",termSheet.graceDays);

       if(!invoiceFund.test(termSheet.invoiceFunding)){
          this.toaster.error('Please Enter valid Invoice Funding');
          termSheetFlag = false;
          }

        if(!graceDay.test(termSheet.graceDays))
        {    this.toaster.error('Please Enter valid graceDays');
             termSheetFlag = false;
        }

        if(termSheet.product=="" || termSheet.product==null){
        this.toaster.error('Please Enter valid Product');
            termSheetFlag=false;
        }
        if(termSheet.regularLimit=="" || termSheet.regularLimit==null){
        this.toaster.error('Please Enter valid Regular Limit');
            termSheetFlag=false;
        }
        if(termSheet.adhocLimit=="" || termSheet.adhocLimit==null){
        this.toaster.error('Please Enter valid Adhoc Limit');
            termSheetFlag=false;
        }
        if(termSheet.creditPeriod=="" || termSheet.creditPeriod==null){
        this.toaster.error('Please Enter valid Credit Period');
            termSheetFlag=false;
        }
//         if(termSheet.doorToDoor=="" || termSheet.doorToDoor==null){
//             termSheetFlag=false;
//         }
//         if(termSheet.invoiceAgeing=="" || termSheet.invoiceAgeing==null){
//             termSheetFlag=false;
//         }
        if(termSheet.margin=="" || termSheet.margin==null){
        this.toaster.error('Please Enter valid Margin');
            termSheetFlag=false;
        }
        if(termSheet.interestRate=="" || termSheet.interestRate==null){
        this.toaster.error('Please Enter valid Interest Rate');
            termSheetFlag=false;
        }
        if(termSheet.pf=="" || termSheet.pf==null){
        this.toaster.error('Please Enter valid PF');
            termSheetFlag=false;
        }
        if(termSheet.renewal=="" || termSheet.renewal==null){
        this.toaster.error('Please Enter valid Renewal');
            termSheetFlag=false;
        }
        if(termSheet.renewalPeriod=="" || termSheet.renewalPeriod==null){
        this.toaster.error('Please Enter valid Renewal Period');
            termSheetFlag=false;
        }
        if(termSheet.invoiceFunding=="" || termSheet.invoiceFunding==null){
        this.toaster.error('Please Enter valid Invoice Funding');
            termSheetFlag=false;
        }
        }

        if(this.termSheetValidate==true && termSheetFlag == true ){
        if(key == 'save'){
            this.saveTermSheet();
        }else if(key == 'update'){
            this.updateTermSheet();
        }
     }
    }

saveTermSheet(): void {


    this.showLoader=true;
    let response;
    let fileName="counterParty/termSheet/"+this.cpId;
    let data={ termSheetDataList:this.termSheetArray }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.cpTermSheet1 = false;
        this.cpTermSheet2 = true;
        this.toaster.success('Successfully Saved')
        this.getCpTermSheetById('1');
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			this.cpTermSheet1 = true;
        			}else{
                        var msg =error.error.message;
                        for(let i=0 ; i<this.termSheetArray.length;i++){
                            // Use of String replace() Method
                            msg = msg.replaceAll("termSheetDataList["+i+"]", i+1);
                        }
                        this.toaster.error(msg);
        			this.cpTermSheet1 = true;
        			}
        		}

    })
}

updateTermSheet(): void {


   this.showLoader=true;

    let response;
    let fileName="counterParty/updateTermSheet";
    let data={ termSheetDataList:this.termSheetArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
    },error=>{
    this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
            {
                if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                }else{
                    var msg =error.error.message;
                    for(let i=0 ; i<this.termSheetArray.length;i++){
                        // Use of String replace() Method
                        msg = msg.replaceAll("termSheetDataList["+i+"]", i+1);
                    }
                    this.toaster.error(msg);
                }
            }
    })
}

     getCpTermSheetById(key): void{
            this.showLoader=true;
               this.termSheetList = [];
               let response;
               let fileName="counterParty/termSheetGet/"+this.cpId;
               response = this.requestapi.getData(fileName);
               response.subscribe((res: any) => {
               this.showLoader=false;
                   this.termSheetList=res;
                   console.log("3")
                   console.log("Answer",this.commercial2)
                   if(key == '0'){
                    for(var j=0;j<res.length;j++){
                          this.addCommercialCcValue()
                         }
                    this.getCommercialCcGetById();
                    }

               if(res.length != 0){
               this.cpTermSheet2=true;
                   for(var i=0;i< res.length;i++){
                       this.termSheetArray[i].id = res[i].id;
                       this.termSheetArray[i].appId = res[i].applicationEntity.id;
                       this.termSheetArray[i].custId = res[i].customerInfoEntity.id;
                       this.termSheetArray[i].anchorName = res[i].customerInfoEntity.customerName;
                       this.termSheetArray[i].product = res[i].product;
                       this.termSheetArray[i].regularLimit = res[i].regularLimit;
                       this.termSheetArray[i].adhocLimit = res[i].adhocLimit;
                       this.termSheetArray[i].creditPeriod = res[i].creditPeriod;
                       this.termSheetArray[i].doorToDoor = res[i].doorToDoor;
                       this.termSheetArray[i].invoiceAgeing = res[i].invoiceAgeing;
                       this.termSheetArray[i].margin = res[i].margin;
                       this.termSheetArray[i].interestRate = res[i].interestRate;
                       this.termSheetArray[i].pf = res[i].pf;
                       this.termSheetArray[i].renewal = res[i].renewal;
                       this.termSheetArray[i].interestRateType = res[i].interestRateType;
                       this.termSheetArray[i].renewalPeriod = res[i].renewalPeriod;
                       this.termSheetArray[i].applyOfInterest = res[i].applyOfInterest;
                       this.termSheetArray[i].interestBorneBy = res[i].interestBorneBy;
                       this.termSheetArray[i].penaltyBorneBy = res[i].penaltyBorneBy;
                       this.termSheetArray[i].invoiceFunding = res[i].invoiceFunding;
                       this.termSheetArray[i].graceDays = res[i].graceDays;
                   }
               }
               },error=>{
               this.showLoader=false;
                   if(error.status==401){
                       this.refresh_token=localStorage.getItem('refresh_token')
                       this.authService.SignOut(this.refresh_token);
                   }

               })
           }


    ///////////////////////Collateral//////////////////////////////
    getCollateral(): void{

        this.creditNormDetails = [];
        let response;
        let fileName="counterParty/collateralMaster";
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

        this.collateralList = res;
        },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    storeCollateral(id,event):void{
        this.collateralId=id;
        this.collateralValue=event.target.value;
        this.enableCollateral=1;
        if(this.collateralArray.length != 0){
            for(let items of this.collateralArray){
                if(items.cmId == this.collateralId){
                    let index = this.collateralArray.indexOf(items);
                    if(this.cpCollateral2){
                        var pId = items.id;
                        const a1 = { id: pId, appId: this.cpId ,cmId : this.collateralId,value : this.collateralValue,}
                        this.collateralArray[index] = a1;
                    }else{
                        const a1 = { appId: this.cpId ,cmId : this.collateralId,value : this.collateralValue,}
                        this.collateralArray[index] = a1;
                    }
                   this.enableCollateral=1;
                    break;
                }else{
                    this.enableCollateral=0;
                }
            }
        }else{
            const a1 = { appId: this.cpId ,cmId : this.collateralId,value : this.collateralValue,}
            this.collateralArray.push(a1);
        }
        if(this.enableCollateral==0){
            const a1 = { appId: this.cpId ,cmId : this.collateralId,value : this.collateralValue,}
            this.collateralArray.push(a1);
        }
    }

    public collateralSubmit(key) {
        this.collateralValidate = false;
        this.collateralValidate = !this.collateralValidate;
        var collateralFlag = true;
        if(this.collateralArray.length == this.collateralList.length){
            collateralFlag=true;
        }else{
            collateralFlag=false;
        }
        if(this.collateralValidate==true && collateralFlag == true ){
            if(key == 'save'){
                this.saveCollateral();
            }else if(key == 'update'){
                this.updateCollateral();
            }
        }
    }


public beneficiarySubmit(key) {
    this.beneficiaryValidate = false;
    this.beneficiaryValidate = !this.beneficiaryValidate;
    const benefiType =/^[a-zA-Z ]*$/;
    const benefiName =/^[a-zA-Z ]*$/;
    const benefiBankAccnt =/^[a-zA-Z0-9]+$/;
    var flag =true;
    if(this.beneficiaryName==""  || this.beneficiaryName==null){
        flag=false;
    }
    if(this.bankName==""  || this.bankName==null){
        flag=false;
    this.toaster.error("Please Enter Required Fields")
    }
    if(this.bankAccountNumber==""  || this.bankAccountNumber==null){
        flag=false;
    }
    if(this.bankIfscCode==""  || this.bankIfscCode==null){
        flag=false;
    }

    if(this.bankBranchName==""  || this.bankBranchName==null){
        flag=false;
    }
    if(!benefiBankAccnt.test(this.bankAccountNumber)){
                   this.cPBasicFlag1 = true;
                   this.toaster.error('Please Enter Valid bank Account Number');
                   flag = false;
                   this.beneficiaryValidate = false;
                   }
    if(this.beneficiaryValidate==true && flag == true){
        if(key == 'save'){
            setTimeout(() => {
                this.saveBeneficiaryDetails();
            }, 1000);
        }else if(key == 'update'){
            setTimeout(() => {
            this.updateBeneficiaryDetails();
            }, 1000);
        }

    }
}

getBankNameId(event):void{
    var values = event.split(",", 2);

    this.bankCode = values[0];
    this.bankName = values[1];
    this.getBranchDetails( this.bankCode );
}

      saveBeneficiaryDetails(): void {
      this.showLoader=true;
          let response;
          let fileName="anchor/anchorBeneficiary";
          let data={ appId:this.cpId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,bankName:this.bankName,
                    bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,bankBranchCode : this.bankBranchCode,
                    bankBranchName:this.bankBranchName,
          }
          response = this.requestapi.postData(fileName,JSON.stringify(data));
          response.subscribe((res: any) => {
          this.showLoader=false;
              this.beneficiary1 = false;
              this.beneficiary2 = true;
              this.toaster.success('Successfully Saved')
              this.editBeneficiaryIcon = true;
              this.getAnchorBeneficiaryById();
          },error=>{
          this.showLoader=false;
              if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
          }
          if(error.status==400)
          		{
          			if(error.error.message == null || error.error.message == ''){
          			this.toaster.error('Some Technical Error')
          			this.beneficiary1 = true;
                    this.beneficiary2 = false;
          			}else{
          			this.toaster.error(error.error.message);
          			this.beneficiary1 = true;
                    this.beneficiary2 = false;
          			}
          		}
        if(error.status==500){
//             if(error.error.message == "Duplicate Bank Account No"){
                this.toaster.error("Some Technical Error");
//             }
        }


          })
      }

    updateBeneficiaryDetails(): void {
    this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiary";
    let data={ id:this.benPkId,appId:this.cpId,benType:"Dealer",benName:this.beneficiaryName,bankCode : this.bankCode,
               bankName:this.bankName,bankAcctNumber:this.bankAccountNumber,bankIfscCode:this.bankIfscCode,
               bankBranchCode : this.bankBranchCode,bankBranchName:this.bankBranchName,
    }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated')
        this.getAnchorBeneficiaryById();
    },error=>{
    this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
    if(error.status==400)
    		{
    			if(error.error.message == null || error.error.message == ''){
    			this.toaster.error('Some Technical Error')
    			}else{
    			this.toaster.error(error.error.message);
    			}
    		}
        if(error.status==500){
//             if(error.error.message == "Duplicate Bank Account No"){
                this.toaster.error("Some Technical Error");
//             }
        }

    })
}

getAnchorBeneficiaryById():void {
   this.showLoader=true;
    let response;
    let fileName="anchor/anchorBeneficiaryFile/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.fileUploadAnchorBeneficiaryList=res;
        if(this.fileUploadAnchorBeneficiaryList.length != 0){
            this.beneficiary2 = true;
            for(var i=0;i<res.length;i++){
                this.benPkId = res[i].id;
                this.beneficiaryName = res[i].benName;
                this.bankCode = res[i].bankCode;
                this.bankDetails = res[i].bankCode+","+res[i].bankName;
                this.bankName = res[i].bankName;
                this.getBranchDetails(this.bankCode);
                this.bankIfscCode = res[i].bankifscCode;
                this.bankAccountNumber = res[i].bankAcctNumber;
                this.bankBranchName = res[i].bankBranchName;
                this.bankBranchCode = res[i].bankBranchCode;
            }
        }
    },error=>{
    this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}


    saveCollateral(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/collateralDetails";
        let data={ collateralDetailsDataList : this.collateralArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.cpCollateral1 = false;
        this.cpCollateral2 = true;
        this.toaster.success('Successfully Saved');
        this.getCollateralDetails();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
                      this.authService.SignOut(this.refresh_token);
                           }
        if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			this.cpCollateral1 = true;
        			}else{
        			this.toaster.error(error.error.message);
        			this.cpCollateral1 = true;
        			}
        		}

        })
    }

    updateCollateral(): void {
         this.showLoader=true;
          let response;
          let fileName="counterParty/collateralDetails";
          let data={ collateralDetailsDataList : this.collateralArray }
          response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
          response.subscribe((res: any) => {
          this.showLoader=false;
         this.toaster.success('Successfully Updated');
          this.getCollateralDetails();
          },error=>{
          this.showLoader=false;
          if(error.status==401){
                 this.refresh_token=localStorage.getItem('refresh_token')
                 this.authService.SignOut(this.refresh_token);
           }
           if(error.status==400)
           		{
           			if(error.error.message == null || error.error.message == ''){
           			this.toaster.error('Some Technical Error')
           			}else{
           			this.toaster.error(error.error.message);
           			}
           		}
          })
        }

    getCollateralDetails(): void{
        this.showLoader=true;
        let response;
        let fileName="counterParty/collateral/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {
        this.showLoader=false;
        this.collateralDetailsList = res;
        if(res.length != 0){
            this.cpCollateral2 = true;
            this.collateralArray = [];
            for(let item of this.collateralList){
                for(var i=0; i<res.length; i++){
                    if(item.id == res[i].collateralMasterEntity.id){
                        (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                        const a1 = { id:res[i].id, appId: this.cpId ,cmId : res[i].collateralMasterEntity.id,value : res[i].value }
                        this.collateralArray.push(a1);
                    }
                }
            }
        }
        },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

        })
    }

    //////////////////////Due Diligence//////////////////////////
    getDueDiligence(): void{

        let response;
        let fileName="counterParty/dueDiligenceMaster";
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

        this.dueDiligenceList = res;
        },error=>{

        if(error.status==401){
             this.refresh_token=localStorage.getItem('refresh_token')
                     this.authService.SignOut(this.refresh_token);
                           }

        })
    }

    storeDueDiligence(id,event,dataType):void{
        this.dueDiligenceId=id;
        this.dueDiligenceValue=event.target.value;
        this.dueDiligenceDataType = dataType;
        this.enableDueDiligence=1;
        if(this.dueDiligenceArray.length != 0){
            for(let itemId of this.dueDiligenceArray){
                if(itemId.ddId == this.dueDiligenceId){
                    let index = this.dueDiligenceArray.indexOf(itemId);
                    if(this.dueDiligenceDataType == 'TimeStamp'){
                        this.dueDiligenceValue =this.datePipe.transform(this.dueDiligenceValue, 'MM-dd-yyyy');
                    }
                    if(this.dueDiligence2){
                        var pId = itemId.id;
                        const a1 = { id: pId, appId: this.cpId ,ddId : this.dueDiligenceId,value : this.dueDiligenceValue,}
                        this.dueDiligenceArray[index] = a1;
                    }else{
                        const a1 = { appId: this.cpId ,ddId : this.dueDiligenceId,value : this.dueDiligenceValue,}
                        this.dueDiligenceArray[index] = a1;
                    }
                   this.enableDueDiligence=1;
                    break;
                }else{
                    this.enableDueDiligence=0;
                }
            }
        }else{
            if(this.dueDiligenceDataType == 'TimeStamp'){
                this.dueDiligenceValue =this.datePipe.transform(this.dueDiligenceValue, 'MM-dd-yyyy');
            }
            const a1 = { appId: this.cpId ,ddId : this.dueDiligenceId,value : this.dueDiligenceValue,}
            this.dueDiligenceArray.push(a1);
        }
        if(this.enableDueDiligence==0){
            if(this.dueDiligenceDataType == 'TimeStamp'){
                this.dueDiligenceValue =this.datePipe.transform(this.dueDiligenceValue, 'MM-dd-yyyy');
            }
            const a1 = { appId: this.cpId ,ddId : this.dueDiligenceId,value : this.dueDiligenceValue,}
            this.dueDiligenceArray.push(a1);
        }
    }

    public dueDiligenceSubmit(key) {
        this.dueDiligenceValidate = false;
        this.dueDiligenceValidate = !this.dueDiligenceValidate;
        var dueDiligenceFlag = true;
        if(this.dueDiligenceArray.length == this.dueDiligenceList.length){
            dueDiligenceFlag=true;
        }else{
            dueDiligenceFlag=false;
        }
        if(this.dueDiligenceValidate==true && dueDiligenceFlag == true ){
            if(key == 'save'){
                this.saveDueDiligence();
            }else if(key == 'update'){
                this.updateDueDiligence();
            }
        }
    }

    saveDueDiligence(): void {
       this.showLoader=true;
        let response;
        let fileName="counterParty/dueDiligenceDetails";
        let data={ dueDiligenceDetailsDataList : this.dueDiligenceArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.dueDiligence1 = false;
            this.dueDiligence2 = true;
            if(res.message === 'Validation Success'){
                this.toaster.success('Successfully Saved');
                this.getDueDiligenceById();
                this.getUpdateDueDiligence();
            }else{
                this.toaster.error(res.message)
                this.dueDiligence1 = true;
            }
        },error=>{
        this.showLoader=false;
        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }
         if(error.status==400)
         		{
         			if(error.error.message == null || error.error.message == ''){
         			this.toaster.error('Some Technical Error')
         			this.dueDiligence1 = true;
         			}else{
         			this.toaster.error(error.error.message);
         			this.dueDiligence1 = true;
         			}
         		}


        })
    }

    updateDueDiligence(): void {
    this.showLoader=true;
        let response;
        let fileName="counterParty/dueDiligenceDetails";
        let data={ dueDiligenceDetailsDataList : this.dueDiligenceArray };
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.getDueDiligenceById();
        },error=>{
        this.showLoader=false;
        if(error.status==401){
             this.refresh_token=localStorage.getItem('refresh_token')
             this.authService.SignOut(this.refresh_token);
        }
        if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error')
        			}else{
        			this.toaster.error(error.error.message);
        			}
        		}
        })
    }

    getUpdateDueDiligence(): void {
      this.showLoader=true;
      let response
      let fileName="counterParty/dueDiligenceDetails/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {
        this.showLoader=false;
        if(res.length != 0){
            this.dueDiligence2 = true;
            this.dueDiligenceArray = [];
            for(let item of this.dueDiligenceList){
                for(var i=0; i<res.length; i++){
                    if(item.id == res[i].dueDiligenceMasterEntity.id){
                        if(item.datatype == 'TimeStamp'){
                            res[i].value =this.datePipe.transform(res[i].value, 'yyyy-MM-dd');
                        }
                        (<HTMLInputElement>document.getElementById(item.name)).value = res[i].value;
                        if(item.datatype == 'TimeStamp'){
                            res[i].value =this.datePipe.transform(res[i].value, 'yyyy-MM-dd');
                        }
                        const a1 = { id:res[i].id, appId: this.cpId ,ddId : res[i].dueDiligenceMasterEntity.id,value : res[i].value }
                        this.dueDiligenceArray.push(a1);
                    }
                }
            }
            this.dueDiligenceValues=res;
        }
        },error=>{
        this.showLoader=false;
        if(error.status==401){
           this.refresh_token=localStorage.getItem('refresh_token')
                   this.authService.SignOut(this.refresh_token);
                           }

        })
      }

      getDueDiligenceById(): void {

          let response
          let fileName="counterParty/dueDiligenceDetails/"+this.cpId;
            response = this.requestapi.getData(fileName);
            response.subscribe((res: any) => {

            for(var i=0; i<res.length; i++){
               if(res[i].dueDiligenceMasterEntity.datatype == 'TimeStamp'){
                  res[i].value =this.datePipe.transform(res[i].value, 'dd-MM-yyyy');
               }
            }
            this.dueDiligenceValues=res;
            },error=>{

            if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                          this.authService.SignOut(this.refresh_token);
                }

            })
          }
    ///////////////////////////////////Credit Policy//////////////////////////////////////////
    getCreditPolicy(): void{

        this.creditNormDetails = [];
        let response;
        let fileName="counterParty/creditPolicyFilters?id="+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res: any) => {

        this.creditPolicyMasterList=res;
        if(this.creditPolicyMasterList.length>0){
        this.creditPolicyView = true;
        }
        console.log("creditPolicyMasterList -- : ", this.creditPolicyMasterList);
        },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                           }

        })
    }

getUpdateCreditPolicyDetails(): void{
    this.showLoader=true;
    let response;
    let fileName="counterParty/creditPolicyDetails/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res:any) => {
    this.showLoader=false;
    if(res.creditPolicyArray != 'empty'){
        this.creditPolicyArray = res.creditPolicyArray;
        this.updateCreditPolicyFlag = true;
        this.creditPolicyArray = [];
//         for(let master of this.creditPolicyFilterList){
                            for(let itemValue of res.creditPolicyArray){
//                                 if(master.creditPolicyMasterEntity.id == itemValue.creditPolicyId){
                                    (<HTMLInputElement>document.getElementById(itemValue.scpDisplayName)).value = itemValue.value;
                                    const a1 = { id:itemValue.id, appId: itemValue.appId ,cpMasterId : itemValue.creditPolicyId,value : itemValue.value}
                                    this.creditPolicyArray.push(a1);
//                                 }
                            }
                            this.runCreditPolicy();
//                         }
    }else{
    }
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
   }

   })
}

    storeCreditPolicy(id,item: any,dataType):void{
        this.creditNormDetails = [];
        this.creditPolicyResults = [];
        this.saveCreditPolicyFlag=true;
        if(Event.prototype.isPrototypeOf(item)){
            this.creditPolicyValue=item.target.value;
        }else{
            this.creditPolicyValue=item.toString();
        }
        this.creditPolicyId=id;
        this.enableCreditPolicy=1;
        if(this.creditPolicyArray.length != 0){
            for(let itemId of this.creditPolicyArray){
                if(itemId.cpMasterId == this.creditPolicyId){
                    if(this.updateCreditPolicyFlag){
                        let index = this.creditPolicyArray.indexOf(itemId);
                        let pId = itemId.id
                        const c = { id:pId, appId: this.cpId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
                        this.creditPolicyArray[index] = c;
                        this.enableCreditPolicy=1;
                    }else{
                        let index = this.creditPolicyArray.indexOf(itemId);
                        const c = { appId: this.cpId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
                        this.creditPolicyArray[index] = c;
                        this.enableCreditPolicy=1;
                    }

                    break;
                }else{
                    this.enableCreditPolicy=0;
                }
            }
        }else{
            const c = { appId: this.cpId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
            this.creditPolicyArray.push(c);
        }
        if(this.enableCreditPolicy==0){
            const c = { appId: this.cpId ,cpMasterId : this.creditPolicyId,value : this.creditPolicyValue,}
            this.creditPolicyArray.push(c);
        }
    }
decision:any;
    validateCreditPolicy(): void{
//        this.decision=desc;
        this.saveProCreditPolicy('NA');
        let response;
        let fileName="counterParty/validateCreditPolicy/";

        console.log(this.creditPolicyArray,'this.creditPolicyArray##')

        let data={ creditPolicyDetailsData : this.creditPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
         setTimeout(()=>{
         this.runCreditPolicy();
         },500)
        },error=>{
        this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}

        })
    }

    //Credit Policy
    saveProCreditPolicy(key): void{
        let proposalFlag = true;
        for(let proposals of this.creditProposalList){
            if(proposals.vintageWithAnchor=="" || proposals.vintageWithAnchor==null){
                proposalFlag=false;
            }
            if(proposals.minMonthlyAnchor=="" || proposals.minMonthlyAnchor==null){
                proposalFlag=false;
            }
            if(proposals.anchorRelationship=="" || proposals.anchorRelationship==null){
                proposalFlag=false;
            }
        }
        if(proposalFlag){
            this.updateCreditProposal(key);
        }else{
            this.toaster.error("Please enter all fields");
        }
    }

    updateCreditProposal(key): void {
        this.showLoader=true;
        let response;
        let fileName="counterParty/updateCreditProposedProduct";
        let data={ proposedProductsDataList:this.creditProposalList }
        response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
            this.showLoader=false;
            if(key == 'save'){
                this.toaster.success('Successfully saved');
                this.creditCheck = true;
                this.getCreditPolicy();
            }else if (key == 'update'){
                this.toaster.success('Successfully Updated');
                this.creditCheck = true;
            }

        },error=>{
            this.showLoader=false;
            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400){
                if(error.error.message == null || error.error.message == ''){
                this.toaster.error('Some Technical Error')
                }else{
                this.toaster.error(error.error.message);
                }
            }

        })
    }

    runCreditPolicy(): void {
    this.showLoader=true;
        if(this.proposalArray[0].product == "Dealer Invoice Finance" || this.proposalArray[0].product == "Dealer Purchase Order Finance" || this.proposalArray[0].product == "Anchor Sales Bill Discounting"){
            this.cpType = "Dealer";
        }else if(this.proposalArray[0].product == "Vendor Invoice Finance" || this.proposalArray[0].product == "Vendor Purchase Order Finance" || this.proposalArray[0].product == "Anchor Purchase Bill Discounting"){
            this.cpType = "Vendor";
        }
            for(let item of this.creditProposalList){
               console.log(item,item.creditPolicyCheck,'item.creditPolicyCheck')
               item.creditPolicyCheck = null;
               console.log(item,item.creditPolicyCheck,'item.creditPolicyCheck')
            }
    if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
            let response;
            let fileName="counterParty/creditPolicyMaster?apppId="+this.cpId;
            console.log(this.creditPolicyArray,'this.creditPolicyArray##')
            let data={ creditPolicyDetailsData : this.creditPolicyArray }
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
//             this.creditPolicyDetailsSubmit(this.decision);
            this.creditPolicyResults = res.creditPolicyResults;
            for(let item of this.creditProposalList){
             item.status = null;
             console.log(item.status,item,'item')
            }
            console.log(this.creditPolicyResults,'this.creditPolicyResults')
            if(this.creditPolicyResults == null){
            this.toaster.error('Some Technical Error33');
            this.saveCreditPolicyFlag= true;
            }else{
            this.saveCreditPolicyFlag= false;
            }
            },error=>{
            this.showLoader=false;
              if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}

            })
        }else{
        this.showLoader=false;
            this.toaster.error("Kindly fill all Field");
        }
    }
runCreditPolicyseven(): void {
    this.showLoader=true;
        if(this.proposalArray[0].product == "Dealer Invoice Finance" || this.proposalArray[0].product == "Dealer Purchase Order Finance" || this.proposalArray[0].product == "Anchor Sales Bill Discounting"){
            this.cpType = "Dealer";
        }else if(this.proposalArray[0].product == "Vendor Invoice Finance" || this.proposalArray[0].product == "Vendor Purchase Order Finance" || this.proposalArray[0].product == "Anchor Purchase Bill Discounting"){
            this.cpType = "Vendor";
        }

//         if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
            let response;
            let fileName="counterParty/creditPolicyMaster?apppId="+this.cpId;
            this.creditPolicyArrayss = [];
            for(let itemValue of this.creditPolicyArray)
            {
            const a1 = { id:itemValue.id, appId: itemValue.appId ,cpMasterId : itemValue.creditPolicyId,value : itemValue.value}
            this.creditPolicyArrayss.push(a1);
            }

            let data={ creditPolicyDetailsData : this.creditPolicyArrayss }

            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            this.showLoader=false;
//             this.creditPolicyDetailsSubmit(this.decision);
            console.log(res.creditPolicyResults,'creditPolicyResults')
            this.creditPolicyResults = res.creditPolicyResults;
            if(this.creditPolicyResults == null){
            this.toaster.error('Some Technical Error44');
            this.saveCreditPolicyFlag= true;
            }else{
            this.saveCreditPolicyFlag= false;
            }
            },error=>{
            this.showLoader=false;
              if(error.status==401){
              this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
            }
            if(error.status==400)
            		{
            			if(error.error.message == null || error.error.message == ''){
            			this.toaster.error('Some Technical Error22')
            			}else{
            			this.toaster.error(error.error.message);
            			}
            		}

            })
//         }else{
//         this.showLoader=false;
//         console.log("this.creditPolicyAr---",this.creditPolicyArray);
// //             this.toaster.error("Kindly fill all Field");
//         }
    }

     getProgramNormsByProduct(): void{
          this.showLoader=true;
            let response;
            let fileName="anchor/anchorPrograms/"+this.proposalArray[0].custId+"?productName="+this.proposalArray[0].product;
            response = this.requestapi.getData(fileName);
            response.subscribe((res:any) =>{
            this.showLoader=false;
            this.programNormsProduct = res;
            },error=>{
            this.showLoader=false;
                if(error.status==401){
                    this.refresh_token=localStorage.getItem('refresh_token')
                    this.authService.SignOut(this.refresh_token);
                }

            })
        }

    getCreditPolicyDetails(): void{

        let response;
        let fileName="counterParty/creditPolicyDetails/"+this.cpId;
        response = this.requestapi.getData(fileName);
        response.subscribe((res:any) => {

            this.creditPolicyArray = res.creditPolicyArray;
            setTimeout(() => {
            if(this.creditPolicyArray.length==this.creditPolicyMasterList.length){
                this.runCreditPolicyseven();
                }
            }, 1000);
        },error=>{

            if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                this.authService.SignOut(this.refresh_token);
            }

        })
    }

    public creditPolicySubmit() {
        this.creditPolicyValidate = false;
        this.creditPolicyValidate = !this.creditPolicyValidate;
        var creditPolicyFlag = true;
        if(this.creditPolicyArray.length == 11){
            creditPolicyFlag=true;
        }else{
            creditPolicyFlag=false;
        }
        if(this.creditPolicyValidate==true && creditPolicyFlag == true ){
        }else{
            this.toaster.error('Please fill all field')
        }
    }

    saveCreditPolicy(): void {
     this.showLoader=true;
        let response;
        let fileName="counterParty/creditPolicyDetails";
        let data={ creditPolicyDetailsData : this.creditPolicyArray }
        response = this.requestapi.postData(fileName,JSON.stringify(data));
        response.subscribe((res: any) => {
        this.showLoader=false;
            this.toaster.success('Successfully Saved');
            this.getCreditPolicyDetails();
            this.updateCreditPolicyFlag = true
            this.uwrCreditPolicyNp = false;
            this.uwrRemarks = true;window.scrollTo(0, 0);
        },error=>{
        this.showLoader=false;
        if(error.status==401){
                this.refresh_token=localStorage.getItem('refresh_token')
                        this.authService.SignOut(this.refresh_token);
                           }
        if(error.status==400)
        		{
        			if(error.error.message == null || error.error.message == ''){
        			this.toaster.error('Some Technical Error1')
        			}else{
        			this.toaster.error(error.error.message);
        			}
        		}
        })
    }

storeCreditPolicyStatus(id,status){
        for (let item of this.creditProposalList){
            if(item.id == id){
                let index = this.creditProposalList.indexOf(item);
                item.creditPolicyCheck = status;
                this.creditProposalList[index] = item;
                if(status){
                    (<HTMLInputElement>document.getElementById(id+'A')).disabled = true;
                    this.toaster.success('Credit Policy Result has been Successfully Approved');
                    (<HTMLInputElement>document.getElementById(id+'R')).disabled = false;
                }else{
                    (<HTMLInputElement>document.getElementById(id+'A')).disabled = false;
                    this.toaster.error('Credit Policy Result has been Rejected');
                    (<HTMLInputElement>document.getElementById(id+'R')).disabled = true;
                }
            }
        }
        console.log("this.creditProposalList",this.creditProposalList)
    }


updateCreditPolicy(): void {
   this.showLoader=true;
    let response;
    let fileName="counterParty/creditPolicyDetails";
    for(let item of this.creditPolicyArray){
        if(typeof item.value == 'number'){
            item.value = item.value.toString();
        }
    }
    let data={ creditPolicyDetailsData : this.creditPolicyArray }
    response = this.requestapi.putDataParam(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
    this.showLoader=false;
        this.toaster.success('Successfully Updated');
        this.getCreditPolicyDetails();
        this.uwrCreditPolicyNp = false;
        this.uwrRemarks = true;window.scrollTo(0, 0);
    },error=>{
    this.showLoader=false;
        if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }
     if(error.status==400)
		{
			if(error.error.message == null || error.error.message == ''){
			this.toaster.error('Some Technical Error')
			}else{
			this.toaster.error(error.error.message);
			}
		}
    })
}

underWriterReviewSubmit(){
    if(this.goToRisk){
        this.popupFun(this.sixId,2,6,'CP_RISK_UNDERWRITER_LEAD');
    }else{
        this.popupFun(this.sixId,2,6,'CP_CREDIT_UNDERWRITER_LEAD');
    }
}


    secondId:any;
    thirdId:any;
    fourthId:any;
    fifthId:any;
    sixId:any;
    sevenId:any;
    eightId:any;
    nineId:any;
    tenId:any;
    elevenId:any;
twelveId:any;

    getPending():void {

      let response;
      let fileName="wfApprovalStatus/getFinalWFStatus/"+localStorage.getItem('email');
      response = this.requestapi.getData(fileName);
      response.subscribe((res: any) => {

      if(res.status==true){
      for(let i=0;i<res.wfTableDataList.length;i++){
        if(res.wfTableDataList[i].currentStage=='CP2' && res.wfTableDataList[i].appId==this.cpId){
            this.secondId=res.wfTableDataList[i].nextStageId;
        }
        if(res.wfTableDataList[i].currentStage=='CP3' && res.wfTableDataList[i].appId==this.cpId){
                this.thirdId=res.wfTableDataList[i].nextStageId;
            }
        if(res.wfTableDataList[i].currentStage=='CP4' && res.wfTableDataList[i].appId==this.cpId){
                this.fourthId=res.wfTableDataList[i].nextStageId;
            }
        if(res.wfTableDataList[i].currentStage=='CP5' && res.wfTableDataList[i].appId==this.cpId){
                this.fifthId=res.wfTableDataList[i].nextStageId;
            }
        if(res.wfTableDataList[i].currentStage=='CP6' && res.wfTableDataList[i].appId==this.cpId){
                    this.sixId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP7' && res.wfTableDataList[i].appId==this.cpId){
                    this.sevenId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP8' && res.wfTableDataList[i].appId==this.cpId){
                    this.eightId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP9' && res.wfTableDataList[i].appId==this.cpId){
                    this.nineId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP10' && res.wfTableDataList[i].appId==this.cpId){
                    this.tenId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP11' && res.wfTableDataList[i].appId==this.cpId){
                    this.elevenId=res.wfTableDataList[i].nextStageId;
                }
        if(res.wfTableDataList[i].currentStage=='CP12' && res.wfTableDataList[i].appId==this.cpId){
                            this.twelveId=res.wfTableDataList[i].nextStageId;
                        }
      }
    }
      },error=>{

      if(error.status==401){
           this.refresh_token=localStorage.getItem('refresh_token')
                   this.authService.SignOut(this.refresh_token);
                         }

      })
    }

getProposalById(): void {
    this.creditProposalList = [];
    this.ProposalList = [];
    let response;
    let fileName="counterParty/proposedProductsById/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        for(var i=0; i<res.length;i++){
            this.anchorId = res[i].customerInfoEntity.id;
        }
        this.creditProposalList = res;
        this.proposalList=res;

//         if(res.length > 1){
//             for(var j=1;j<res.length;j++){
//                 this.addProposalValue();
//             }
//         }
        if(res.length != 0){
            for(var i=0;i<res.length;i++){
                this.proposalArray[i].appId = res[i].applicationEntity.id;
                this.proposalArray[i].custId = res[i].customerInfoEntity.id;
                this.proposalArray[i].product = res[i].product;
                this.proposalArray[i].type = res[i].type;
                this.proposalArray[i].proposed = res[i].proposed;
                if(res[i].vintageWithAnchor != null ||res[i].minMonthlyAnchor != null ||res[i].anchorRelationship != null){
                    this.creditCheck = true;
                }
            }
            this.gotoRiskOrCredit();
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getLimitEligibilityById():void {

    let response;
    let fileName="counterParty/limitEligibilityById/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

    this.limitEligibilityList=res;
    console.log("this.limitEligibilityMultipleArray::::",this.limitEligibilityMultipleArray)

//     if(res.length > 1){
//         for(var j=1;j<res.length;j++){
//             this.addLimitEligibleValue();
//         }
//     }
    if(res.length != 0){
        this.cpLimitEligibility2=true;
        for(var j=0;j<this.limitEligibilityMultipleArray.length;j++){
         for(var i=0;i<res.length;i++){
            this.limitEligibilityArray[i].id = res[i].id;
            this.limitEligibilityArray[i].custId = res[i].customerInfoEntity.id;
            this.limitEligibilityArray[i].anchorName = res[i].customerInfoEntity.customerName;
            this.limitEligibilityArray[i].appId = res[i].applicationEntity.id;
            this.limitEligibilityArray[i].product = res[i].product;
            this.limitEligibilityArray[i].currentLimit = res[i].currentLimit;
            this.limitEligibilityArray[i].proposedLimit = res[i].proposedLimit;
            this.limitEligibilityArray[i].eligibleLimit = res[i].eligibleLimit;
            this.limitEligibilityArray[i].adhocLimit = res[i].adhocLimit;
            this.limitEligibilityArray[i].creditPeriod = res[i].creditPeriod;
            this.limitEligibilityArray[i].doortoDoor = res[i].doorToDoor;
            this.limitEligibilityArray[i].invoiceAgeing = res[i].invoiceAgeing;
            this.limitEligibilityArray[i].margin = res[i].margin;
            this.limitEligibilityArray[i].expectedGrowth = res[i].expectedGrowth;
            this.limitEligibilityArray[i].monthlyAverage = res[i].monthlyAverage;
            this.limitEligibilityArray[i].calculatedLimitWoSetOff = res[i].calculatedLimitWoSetOff;
            this.limitEligibilityArray[i].approtionedLimits = res[i].approtionedLimits;
            this.limitEligibilityArray[i].existingScfLimits = res[i].existingScfLimits;
            this.limitEligibilityArray[i].modelLimit = res[i].modelLimit;
            this.limitEligibilityArray[i].loginLimitAmount = res[i].customerRequestedAmount;
            this.limitEligibilityArray[i].anchorRecommendedAmount = res[i].anchorRecommendedAmount;
            this.limitEligibilityArray[i].receivables = res[i].receivables;
            this.limitEligibilityArray[i].inventory = res[i].inventory;
            this.limitEligibilityArray[i].creditor = res[i].creditor;
            this.limitEligibilityArray[i].expectedMonthlyTurnOverWithAnchor = res[i].expectedMonthlyTurnOverWithAnchor;
            this.limitEligibilityArray[i].modelAdhocLimit = res[i].modelAdhocLimit;
            this.limitEligibilityArray[i].loginLimitAmount = res[i].customerRequestedAmount;

            this.termSheetArray[i].custId = this.limitEligibilityMultipleArray[i].customerId;
            this.termSheetArray[i].anchorName = this.limitEligibilityArray[i].anchorName;
            this.termSheetArray[i].product = this.limitEligibilityArray[i].product;
            this.termSheetArray[i].regularLimit = this.limitEligibilityArray[i].proposedLimit;
           this.termSheetArray[i].adhocLimit = this.limitEligibilityArray[i].adhocLimit;
           this.termSheetArray[i].creditPeriod = this.limitEligibilityArray[i].creditPeriod;
           this.termSheetArray[i].doorToDoor = this.limitEligibilityArray[i].doortoDoor;
           this.termSheetArray[i].invoiceAgeing = this.limitEligibilityArray[i].invoiceAgeing;
           this.termSheetArray[i].applyOfInterest = this.limitEligibilityMultipleArray[j].applicationInterest;
           this.termSheetArray[i].interestBorneBy = this.limitEligibilityMultipleArray[j].interestBorneBy;
           this.termSheetArray[i].penaltyBorneBy = this.limitEligibilityMultipleArray[j].interestBorneBy;
           this.termSheetArray[i].renewalPeriod = 12;

        }
    }

    }
    console.log("limitEligibilityArray ::::",this.limitEligibilityArray)
    },error=>{

    if(error.status==401){
           this.refresh_token=localStorage.getItem('refresh_token')
                   this.authService.SignOut(this.refresh_token);
                       }

    })
}

getTermSheetById():void {

    let response;
    let fileName="counterParty/termSheetGet/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.termSheetList=res;
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getCollateralById():void {

    let response;
    let fileName="counterParty/collateral/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.collateralValueList=res;
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}

getCounterPartyById():void {

    let response;
    let fileName="counterParty/cpBasicDetails/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

        this.counterPartyList=res;
        console.log("counterPartyList ",res)
        for(var i=0;i< res.length;i++){
            this.cPBasicFlag2 = true;
            this.cpPkId = res[i].id;
            this.cpAppId= res[i].applicationEntity.id;
            this.cpCustId=res[i].applicationEntity.customerInfoEntity.id;
            this.panNumber2 = res[i].panNumber;
            this.companyName = res[i].companyName;
            this.gstNumber1 = res[i].gstNumber;
//             this.custFacilityTenure = res[i].applicationEntity.customerInfoEntity.facilityTenure;
            if(this.gstNumber1=="")
            {
            this.gstNumber1=null;
            }
            this.cinNumber2 = res[i].cinNumber;
            this.constitution = res[i].constitution;
            this.cpCIty = res[i].city;
            this.cpState = res[i].state;
            this.source = res[i].source;
            this.subSource = res[i].subSource;
            this.rmName = res[i].rmName;
            if(this.elevenWip !='allow'){
                this.docRMName = res[i].rmName;
            }
            this.custContactName = res[i].cusContName;
            this.custContactMobile = res[i].cusContactNumber;
            this.custContactEmail = res[i].cusContactEmail;
            this.activity = res[i].activity;
            this.totalInwardCheques = res[i].totalInwardCheques;
            this.cpType = "New";
//             this.custFacilityTenure = res[i].applicationEntity.customerInfoEntity.facilityTenure;

//             if(res[i].assessmentType != null){
//                 this.assessmentType = res[i].assessmentType;
//                 console.log("this.assessmentTypeIF",this.assessmentType);
//             }else{
//                 console.log("this.assessmentTypeELSE",this.assessmentType);
//             }
            this.anchorRelationShip= res[i].anchorRelationShip;

//             if(res[i].totalInwardCheques == null && res[i].assessmentType == null && res[i].anchorRelationShip ==null){
//                 this.softFlag = true;
//             }else{
//                 this.softFlag = false;
//
//             }
        }
    },error=>{

        if(error.status==401){
            this.refresh_token=localStorage.getItem('refresh_token')
            this.authService.SignOut(this.refresh_token);
        }

    })
}


checkboxProd(event): void{
    this.eve = event.target.checked;
}

checkboxAnchor(event): void{
    this.eveAnchor = event.target.checked;
}

checkboxRegular(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("regularLimitRemarks"+i)).disabled = false;
    }else{
        (<HTMLInputElement>document.getElementById("regularLimitRemarks"+i)).disabled = true;
    }
    this.eveRegular = event.target.checked;
}

checkboxAdhoc(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("adhocLimitRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("adhocLimitRemarks"+i)).disabled = true;
     }
    this.eveAdhoc = event.target.checked;
}

checkboxCredit(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("creditPeriodRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("creditPeriodRemarks"+i)).disabled = true;
     }
    this.eveCredit = event.target.checked;
}

checkboxDoor(event,i): void{
    if(event.target.checked){
        (<HTMLInputElement>document.getElementById("doorRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("doorRemarks"+i)).disabled = true;
     }
    this.eveDoor = event.target.checked;
}

checkboxInvoice(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("invoiceAgeingRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("invoiceAgeingRemarks"+i)).disabled = true;
     }
    this.eveInvoice = event.target.checked;
}

checkboxMargin(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("marginRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("marginRemarks"+i)).disabled = true;
     }
    this.eveMargin = event.target.checked;
}

checkboxInterest(event,i): void{

if(event.target.checked){
        (<HTMLInputElement>document.getElementById("interestRateRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("interestRateRemarks"+i)).disabled = true;
     }
    this.eveInterest = event.target.checked;
}

checkboxPf(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("pfRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("pfRemarks"+i)).disabled = true;
     }
    this.evePf = event.target.checked;
}


checkboxRenewal(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("renewalRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("renewalRemarks"+i)).disabled = true;
     }
    this.eveRenewal = event.target.checked;
}

checkboxInterestType(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("interestTypeRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("interestTypeRemarks"+i)).disabled = true;
     }
    this.eveInterestType = event.target.checked;
}
checkboxRenewalPeriod(event,i): void{
if(event.target.checked){
        (<HTMLInputElement>document.getElementById("renewalPeriodRemarks"+i)).disabled = false;
    }else{
         (<HTMLInputElement>document.getElementById("renewalPeriodRemarks"+i)).disabled = true;
     }
    this.eveRenewalPeriod = event.target.checked;
}
indexTemp:any;
popupFun(stageId,status,index,leadGrp){
    Swal.fire({
        title: 'Are you sure to submit?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                    `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        this.indexTemp=index;
        if (result.value) {
        if(index == 1){
            this.updateCustomerInfo(stageId,status,result.value,leadGrp);
        }else if(index == 11 && !this.deferralStatus){
            this.wizard.goToStep(index);
            this.saveWf(stageId,status,result.value,leadGrp);
        }else if(index == 12 && this.deferralStatus){
             this.wizard.goToStep(index);
             this.saveWfd(stageId,status,result.value,leadGrp);
        }else{
            this.saveWf(stageId,status,result.value,leadGrp);
        }
    }
    })
}


popupFunCredCom(stageId,status,index){
    Swal.fire({
        title: 'Are you sure to submit?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                    `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
        this.indexTemp=index;
        if (result.value) {
        this.credComSubmit(stageId,status,result.value);
    }
    })
}

credComSubmit(stageId,status,remarkWf) : void {
    this.showLoader=true;
    this.stageId = stageId;
    this.wfStatus = status;
    this.remarkWf = remarkWf;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ appId:this.cpId,stageId:this.stageId,status:this.wfStatus,approverInfo:this.emailId,remarks:this.remarkWf,nextApproverInfo:'CP_CREDIT_APPROVAL_COMMITTEE_LEAD'}
            response = this.requestapi.postData(fileName,JSON.stringify(data));
            response.subscribe((res: any) => {
            let response1;
                let fileName1="wfApprovalStatus/saveWFApprovalSatge";
                let data={ appId:this.cpId,stageId:this.stageId,status:this.wfStatus,approverInfo:this.emailId,remarks:this.remarkWf,nextApproverInfo:'CP_COMMERCIAL_APPROVAL_COMMITTEE_LEAD'}
                        response1 = this.requestapi.postData(fileName1,JSON.stringify(data));
                        response1.subscribe((res: any) => {
                        this.showLoader=false;
                                    window.location.reload();
                        },error=>{
                this.showLoader=false;
                if(error.status==401){
                  this.refresh_token=localStorage.getItem('refresh_token')
                          this.authService.SignOut(this.refresh_token);
                                   }

                })
    },error=>{
    this.showLoader=false;
    if(error.status==401){
      this.refresh_token=localStorage.getItem('refresh_token')
              this.authService.SignOut(this.refresh_token);
                       }

    })
}

getMultipleLimitEligibility():void{

    let response;
    let fileName="counterParty/limitEligibilityList/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any) => {

    this.limitEligibilityMultipleArray=res.LimitEligibility;
    console.log("2")

    if(this.limitEligibilityMultipleArray.length > 0){
            for(var j=0;j<this.limitEligibilityMultipleArray.length;j++){
//             if(this.limitEligibilityArray.length >0){
                this.addLimitEligibleValue();
                this.addTermSheetValue();
//                 }
            }

            for(var i=0;i<this.limitEligibilityMultipleArray.length;i++){
                 this.limitEligibilityArray[i].custId = this.limitEligibilityMultipleArray[i].customerId
                 this.limitEligibilityArray[i].product = this.limitEligibilityMultipleArray[i].productName
                 this.limitEligibilityArray[i].anchorName  = this.limitEligibilityMultipleArray[i].anchorName
                 this.limitEligibilityArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
                 this.limitEligibilityArray[i].loginLimitAmount = this.limitEligibilityMultipleArray[i].loginLimitAmount

//                  this.termSheetArray[i].custId = this.limitEligibilityMultipleArray[i].anchorName
//                  this.termSheetArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
//                  this.termSheetArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
//                  this.termSheetArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
//                  this.termSheetArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure
                }

//             for(var i=0;i<this.limitEligibilityMultipleArray.length;i++){
//                            this.termSheetArray[i].appId = undefined
//                            this.termSheetArray[i].custId = this.limitEligibilityMultipleArray[i].anchorName;
//                            this.termSheetArray[i].product = this.limitEligibilityMultipleArray[i].productName;
//                            this.termSheetArray[i].creditPeriod = this.limitEligibilityMultipleArray[i].tenure;
//                            this.termSheetArray[i].applyOfInterest = this.limitEligibilityMultipleArray[i].applicationInterest;
//                            this.termSheetArray[i].interestBorneBy = this.limitEligibilityMultipleArray[i].interestBorneBy;
//                            this.termSheetArray[i].penaltyBorneBy = this.limitEligibilityMultipleArray[i].interestBorneBy;
//
//
//                        }
                       console.log("this.termSheetArray",this.termSheetArray)


        }

        console.log("this.limitEligibilityArray",this.limitEligibilityArray)
        console.log("this.termSheetArray",this.termSheetArray)

    console.log("MultiLimit",this.limitEligibilityMultipleArray);
    },error=>{

    if(error.status==401){
           this.refresh_token=localStorage.getItem('refresh_token')
                   this.authService.SignOut(this.refresh_token);
                       }
    })
}


storeProcedure():void{

    let response;
    let fileName="cpMasterStoreProcedure/"+this.cpId;
    response = this.requestapi.getData(fileName);
    response.subscribe((res: any)=>{

        if(res.P_OUT_MSG == "SUCCESS"){
            this.storeProcedureFlag= true;
            this.showLoader=false;
                window.location.reload();
        }
    },error=>{
        this.showLoader=false;

    })
}

returnCredComWf(StageId,remark){
    this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-1, approverInfo : this.emailId, appId : this.cpId, remarks : remark, nextApproverInfo : 'CP_COMMERCIAL_APPROVAL_COMMITTEE_LEAD' }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {
               window.location.reload();
               this.router.navigate(['/dashboard/inbox']);
               this.showLoader=false;
    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

returnPopupCredComFun(stageId){
    Swal.fire({
        title: 'Are you sure to return?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
    if (result.value) {
        this.returnCredComWf(stageId,result.value);
    }
    })
}


rejectCredComWf(StageId,remark){
  this.showLoader=true;
    let response;
    let fileName="wfApprovalStatus/saveWFApprovalSatge";
    let data={ stageId : StageId, status :-2, approverInfo : this.emailId, appId : this.cpId, remarks : remark, nextApproverInfo : 'CP_COMMERCIAL_APPROVAL_COMMITTEE_LEAD' }
    response = this.requestapi.postData(fileName,JSON.stringify(data));
    response.subscribe((res: any) => {

                window.location.reload();
                this.router.navigate(['/dashboard/inbox']);
                this.showLoader=false;

    },error=>{
    this.showLoader=false;
    if(error.status==401){
        this.refresh_token=localStorage.getItem('refresh_token')
        this.authService.SignOut(this.refresh_token);
    }

    })
}

rejectPopupCredComFun(stageId){
    Swal.fire({
        title: 'Are you sure to reject?',
        text: 'Remark',
        input: 'textarea',
        inputAttributes: {
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Submit',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {
            if(login==''){
                Swal.showValidationMessage(
                `Please enter remarks`
                )
            }
        },
        allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
    if (result.value) {
        this.rejectCredComWf(stageId,result.value);
    }
    })
}
}