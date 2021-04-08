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
    private int idKlijenta;

    @Column(name = "IdAdministratora")
    private int idAdminstratora;

    @Column(name = "Ime")
    private String ime;

    @Column(name = "Prezime")
    private String prezime;

    @Column(name = "NazivFirme")
    private String nazivFirme;

    @Column(name = "Adresa")
    private String adresa;

    @OneToMany(
            mappedBy = "klijent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BrojTelefona> brojeviTelefona =  new ArrayList<>();

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;


    public Klijent() {
    }

    public Klijent(int idAdminstratora, String ime, String prezime, String nazivFirme, String adresa, ArrayList<String> brojeviTelefona) {
        this.idAdminstratora = idAdminstratora;
        this.ime = ime;
        this.prezime = prezime;
        this.nazivFirme = nazivFirme;
        this.adresa = adresa;
        for(String broj : brojeviTelefona) {
            this.brojeviTelefona.add(new BrojTelefona(broj, this));
        }
        oznaciObrisan = false;
    }

    public int getIdKlijenta() {
        return idKlijenta;
    }

    public void setIdKlijenta(int idKlijenta) {
        this.idKlijenta = idKlijenta;
    }

    public int getIdAdminstratora() {
        return idAdminstratora;
    }

    public void setIdAdminstratora(int idAdminstratora) {
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public List<BrojTelefona> getBrojeviTelefona() {
        return brojeviTelefona;
    }

    public void setBrojeviTelefona(List<BrojTelefona> brojeviTelefona) {
        this.brojeviTelefona = brojeviTelefona;
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
                ", adresa='" + adresa + '\'' +
                ", brojeviTelefona=' " + telefoni.toString() +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }


}