package tech.buildrun.agregadordeinvestimentos.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billingaddres")
public class BillingAddress {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = " bairro")
    private String bairro;

    public BillingAddress(UUID id, Account account, String street, String number,  String bairro) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
        this.bairro = bairro;
    }

    public BillingAddress() {}


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
