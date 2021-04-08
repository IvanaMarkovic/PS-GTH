package ps.projekat.podaci.dto;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = "nalog_administratora")
public class NalogAdministratora {

    @Id
    @Column(name = "IdAdministratora")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdministratora;

    @Column(name = "Ime")
    private String ime;

    @Column(name = "Prezime")
    private String prezime;

    @Column(name = "KorisnickoIme")
    private String korisnickoIme;

    @Column(name = "Lozinka")
    private String lozinka;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public NalogAdministratora() {}

    public NalogAdministratora(String ime, String prezime, String korisnickoIme, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public long getIdAdministratora() {
        return idAdministratora;
    }

    public void setIdAdministratora(long idAdministratora) {
        this.idAdministratora = idAdministratora;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idAdministratora ^ (idAdministratora >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        NalogAdministratora other = (NalogAdministratora) o;
        if (idAdministratora != other.idAdministratora)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NalogAdministratora {" +
                ", Ime: " + ime +
                ", Prezime: " + prezime +
                ", Korisnicko ime: " +korisnickoIme +" }";
    }
}
