package org.vcpl.triton.anchor.page;

import org.springframework.data.domain.Sort;

public class CustomerInfoDetailsPage {
    private int pageNumber=0;
    private int pageSize=10;
    private Sort.Direction sortDirection=Sort.Direction.ASC;
    private String SortByCustomerName="customerName";
    private String  SortByPan="pan";
    private String SortByCin="cin";

    public String getSortByCustomerName() {
        return SortByCustomerName;
    }

    public void setSortByCustomerName(String sortByCustomerName) {
        SortByCustomerName = sortByCustomerName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortByPan() {return SortByPan;}

    public void setSortByPan(String sortByPan) {SortByPan = sortByPan;}

    public String getSortByCin() {return SortByCin;}

    public void setSortByCin(String sortByCin) {SortByCin = sortByCin;}


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

}
