package ps.projekat.podaci.dto;

import ps.projekat.podaci.dao.KlijentDAO;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = "telefon")
public class BrojTelefona {

    @Id
    @Column(name = "BrojTelefona")
    private String brojTelefona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdKlijenta")
    private Klijent klijent;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public BrojTelefona() {
    }

    public BrojTelefona(String brojTelefona, Klijent klijent) {
        this.brojTelefona = brojTelefona;
        this.klijent = klijent;
        oznaciObrisan = false;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
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
        BrojTelefona other = (BrojTelefona) o;
        if (!brojTelefona.equals(other.brojTelefona) && klijent.getIdKlijenta() != other.klijent.getIdKlijenta())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (klijent.getIdKlijenta() ^ (klijent.getIdKlijenta() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Telefon{" + "brojTelefona='" + brojTelefona + '\'' + '}';
    }
}
