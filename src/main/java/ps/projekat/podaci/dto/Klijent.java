package ps.projekat.podaci.dto;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Table(name = "klijent")
public class Klijent {

    @Id
    @Column(name = "IdKlijenta")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idKlijenta;

    @Column(name = "IdAdministratora")
    private long idAdminstratora;

    @Column(name = "Ime")
    private String ime;

    @Column(name = "Prezime")
    private String prezime;

    @Column(name = "NazivFirme")
    private String nazivFirme;

    @Column(name = "EmailAdresa")
    private String emailAdresa;

    @OneToMany(
            mappedBy = "klijent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BrojTelefona> brojeviTelefona =  new ArrayList<>();

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    @Column(name  = "Edukacije")
    private String edukacije;


    public Klijent() {
    }

    public Klijent(long idAdminstratora, String ime, String prezime, String nazivFirme, String emailAdresa,
                   ArrayList<String> brojeviTelefona, String edukacije) {
        this.idAdminstratora = idAdminstratora;
        this.ime = ime;
        this.prezime = prezime;
        this.nazivFirme = nazivFirme;
        this.emailAdresa = emailAdresa;
        this.edukacije = edukacije;
        brojeviTelefona.forEach(broj -> this.brojeviTelefona.add(new BrojTelefona(broj, this)));
        oznaciObrisan = false;
    }

    public Klijent(long idAdminstratora, String ime, String prezime, String nazivFirme) {
        this.idAdminstratora = idAdminstratora;
        this.ime = ime;
        this.prezime = prezime;
        this.nazivFirme = nazivFirme;
        oznaciObrisan = false;
    }

    public long getIdKlijenta() {
        return idKlijenta;
    }

    public void setIdKlijenta(long idKlijenta) {
        this.idKlijenta = idKlijenta;
    }

    public long getIdAdminstratora() {
        return idAdminstratora;
    }

    public void setIdAdminstratora(long idAdminstratora) {
        this.idAdminstratora = idAdminstratora;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public List<BrojTelefona> getBrojeviTelefona() {
        return brojeviTelefona;
    }

    public void setBrojeviTelefona(List<BrojTelefona> brojeviTelefona) {
        this.brojeviTelefona = brojeviTelefona;
    }

    public String getEdukacije() {
        return edukacije;
    }

    public void setEdukacije(String edukacije) {
        this.edukacije = edukacije;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }

    @Override
    public String toString() {
        StringBuilder telefoni = new StringBuilder("");
        if(brojeviTelefona.size() > 0) {
            for (BrojTelefona b : brojeviTelefona) {
                telefoni.append(b + " ");
            }
        }
        return "Klijent{" +
                "idKlijenta='" + idKlijenta + '\'' +
                ", idAdminstratora=" + idAdminstratora +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", nazivFirme='" + nazivFirme + '\'' +
                ", adresa='" + emailAdresa + '\'' +
                ", brojeviTelefona=' " + telefoni.toString() +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }


}