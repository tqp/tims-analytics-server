package com.timsanalytics.auth.authGoogle.beans;

public class GoogleUser {
    private String iss; // The Issuer Identifier for the Issuer of the response
    private String azp; // The client_id of the authorized presenter.
    private String aud; // Identifies the audience that this ID token is intended for
    private String sub; // An identifier for the user, unique among all Google accounts and never reused
    private String email; // The user's email address
    private Boolean email_verified; // True if the user's e-mail address has been verified; otherwise false
    private String at_hash; // Access token hash
    private String name; // The user's full name, in a displayable form
    private String picture; // The URL of the user's profile picture
    private String given_name;
    private String family_name;
    private String locale;
    private Long iat; // The time the ID token was issued
    private Long exp; // The time the ID token expires
    private String alg;
    private String kid;
    private String typ;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getAzp() {
        return azp;
    }

    public void setAzp(String azp) {
        this.azp = azp;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(Boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getAt_hash() {
        return at_hash;
    }

    public void setAt_hash(String at_hash) {
        this.at_hash = at_hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
