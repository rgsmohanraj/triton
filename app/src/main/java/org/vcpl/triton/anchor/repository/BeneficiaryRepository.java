package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity,Long> {
    @Query(value = "SELECT * FROM anchor_beneficiary WHERE app_id = ?1",nativeQuery = true)
    Collection<BeneficiaryEntity> findByCiId(Long id);

    @Query(value = "SELECT BM_NAME,BM_CD FROM BANK_MASTER",nativeQuery = true)
    List<Object[]> getAllBankDetails();

    @Query(value = "SELECT BBM_DESC,IFSC_CODE,BBM_CD FROM Bank_Branch_Master where BBM_SR_NO = ?1",nativeQuery = true)
    List<Object[]>  getAllBranchDetails(Long id);

    @Query(value = "SELECT * FROM Bank_Branch_Master where IFSC_CODE=:ifsc AND BBM_SR_NO = :bankCode",nativeQuery = true)
    List<Object[]> getBankDetails(String ifsc, String bankCode);

    @Query(value = "SELECT bank_acct_number FROM anchor_beneficiary WHERE bank_acct_number = :acctName",nativeQuery = true)
    String findDuplicateAcctNo(String acctName);
}
