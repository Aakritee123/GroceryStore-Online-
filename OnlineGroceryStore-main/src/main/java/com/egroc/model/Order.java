//package com.egroc.model;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//
//
//    @Entity
//    public class Order {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private int id;
//        private String firstName;
//        private String lastName;
//        private String addressLine1;
//        private String addressLine2;
//        private String postcode;
//        private String city;
//        private String phone;
//        private String email;
//        private String additionalInfo;
//        private double totalAmount;
//
//        // Getters and Setters
//
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getAddressLine1() {
//            return addressLine1;
//        }
//
//        public void setAddressLine1(String addressLine1) {
//            this.addressLine1 = addressLine1;
//        }
//
//        public String getAddressLine2() {
//            return addressLine2;
//        }
//
//        public void setAddressLine2(String addressLine2) {
//            this.addressLine2 = addressLine2;
//        }
//
//        public String getPostcode() {
//            return postcode;
//        }
//
//        public void setPostcode(String postcode) {
//            this.postcode = postcode;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public String getAdditionalInfo() {
//            return additionalInfo;
//        }
//
//        public void setAdditionalInfo(String additionalInfo) {
//            this.additionalInfo = additionalInfo;
//        }
//
//        public double getTotalAmount() {
//            return totalAmount;
//        }
//
//        public void setTotalAmount(double totalAmount) {
//            this.totalAmount = totalAmount;
//        }
//    }
//
//
