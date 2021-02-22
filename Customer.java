package model;

import javafx.beans.property.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

/** Customer Class */
public class Customer implements Initializable {
    private final IntegerProperty customerID;
    private final StringProperty customersName;
    private final StringProperty customerAddress;
    private final StringProperty customerZip;
    private final StringProperty customerPhone;
    private final ObjectProperty<Timestamp> customerCreatedDate;
    private final StringProperty createdBy;
    private final ObjectProperty<Timestamp> lastUpdate;
    private final StringProperty updatedBy;
    private final IntegerProperty divisionID;


    public Customer(int customerID, String customersName, String customerAddress, String customerZip, String customerPhone,
                    Timestamp customerCreatedDate,String createdBy, Timestamp lastUpdate, String updatedBy,
                    int divisionID ){
                    this.customerID = new SimpleIntegerProperty(customerID);
                    this.customersName = new SimpleStringProperty(customersName);
                    this.customerAddress = new SimpleStringProperty(customerAddress);
                    this.customerZip = new SimpleStringProperty(customerZip);
                    this.customerPhone = new SimpleStringProperty(customerPhone);
                    this.customerCreatedDate = new SimpleObjectProperty<>(customerCreatedDate);
                    this.createdBy = new SimpleStringProperty(createdBy);
                    this.lastUpdate = new SimpleObjectProperty<>(lastUpdate);
                    this.updatedBy = new SimpleStringProperty(updatedBy);
                    this.divisionID = new SimpleIntegerProperty(divisionID);
   }


    public Customer(IntegerProperty customerID, StringProperty customersName, StringProperty customerAddress, StringProperty customerZip, StringProperty customerPhone, ObjectProperty<Timestamp> customerCreatedDate, StringProperty createdBy, ObjectProperty<Timestamp> lastUpdate, StringProperty updatedBy, IntegerProperty divisionID) {
        this.customerID = customerID;
        this.customersName = customersName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerPhone = customerPhone;
        this.customerCreatedDate = customerCreatedDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.divisionID = divisionID;
    }

    /** Customer Getters and Setters
     * @return*/

           //CUSTOMER ID
    public int getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(int value) {
        customerID.set(value);
    }

    public IntegerProperty idProperty() {
        return customerID;
    }



           //CUSTOMER NAME
    public String getCustomersName() {
        return customersName.get();
    }

    public void setCustomersName(String value) {
        customersName.set(value);
    }

    public StringProperty namesProperty(){
        return customersName;
    }



            //CUSTOMER ADDRESS
    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public void setCustomerAddress(String value) {
        customerAddress.set(value);
    }

    public StringProperty addressProperty(){
        return customerAddress;
    }


            //CUSTOMER ZIP
    public String getCustomerZip() {
        return customerZip.get();
    }

    public void setCustomerZip(String value) {
        customerZip.set(value);
    }

    public StringProperty zipProperty(){
        return customerZip;
    }



            //CUSTOMER PHONE
    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public void setCustomerPhone(String value) {
        customerPhone.set(value);
    }

    public StringProperty phoneProperty(){
        return customerPhone;
    }


             //CUSTOMER CREATED DATE
    public Timestamp getCustomerCreatedDate() {
        return customerCreatedDate.get();
    }

    public void setCustomerCreatedDate(Timestamp value) {
        customerCreatedDate.set(value);
    }

    public ObjectProperty<Timestamp> customerCreatedDate(){
        return customerCreatedDate;
    }


            //CREATED BY
    public String getCreatedBy() {
        return createdBy.get();
    }

    public void setCreatedBy(String value) {
        createdBy.set(value);
    }

    public StringProperty createdByProperty(){
        return createdBy;
    }


            //LAST UPDATE
    public Timestamp getLastUpdate() {
        return lastUpdate.get();
    }

    public void setLastUpdate(Timestamp value) {
        lastUpdate.set(value);
    }

    public ObjectProperty<Timestamp> lastUpdateProperty(){
        return lastUpdate;
    }


            //LAST UPDATED BY
    public String getUpdatedBy() {
        return updatedBy.get();
    }

    public void setUpdatedBy(String value) {
        updatedBy.set(value);
    }

    public StringProperty updatedByProperty(){
        return updatedBy;
    }


            //CUSTOMER ID
    public String getDivisionID() {
        return String.valueOf(divisionID.get());
    }

    public void setDivisionID(int value) {
        divisionID.set(value);
    }

    public IntegerProperty divisionIDProperty() {
        return divisionID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
