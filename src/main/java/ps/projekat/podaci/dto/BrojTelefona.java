package ps.projekat.podaci.dto;

import ps.projekat.podaci.dao.KlijentDAO;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Table(name = "telefon")
public class BrojTelefona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTelefona")
    private long idTelefona;

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

    public long getIdTelefona() {
        return idTelefona;
    }

    public void setIdTelefona(long idTelefona) {
        this.idTelefona = idTelefona;
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

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
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
