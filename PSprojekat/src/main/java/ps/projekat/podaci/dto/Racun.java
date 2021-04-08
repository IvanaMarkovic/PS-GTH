package ps.projekat.podaci.dto;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Transactional
@Table(name = "racun")
public class Racun {

    @Id
    @Column(name = "IdRacuna")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRacuna;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRezervacije")
    private Rezervacija rezervacija;

    @Column(name = "UkupanIznos")
    private float ukupanIznos;

    @Column(name = "IzdatRacun")
    private boolean izdatRacun;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public Racun() {
        ukupanIznos = 0;
        izdatRacun = false;
        oznaciObrisan = false;
    }

/*
    public Racun(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;

    }
*/

    public int getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(int idRacuna) {
        this.idRacuna = idRacuna;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public float getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(float ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public boolean isIzdatRacun() {
        return izdatRacun;
    }

    public void setIzdatRacun(boolean izdatRacun) {
        this.izdatRacun = izdatRacun;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisan(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }

    @Override
    public String toString() {
        return "Racun{" +
                "idRacuna=" + idRacuna +
                ", idRezervacije='" + (rezervacija != null ? rezervacija.getIdRezervacije() : "") + '\'' +
                ", ukupanIznos=" + ukupanIznos +
                ", izdatRacun=" + izdatRacun +
                ", oznaciObrisan='" + oznaciObrisan +
                '}';
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
        Racun other = (Racun) o;
        if (idRacuna != other.idRacuna)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idRacuna ^ (idRacuna >>> 32));
        return result;
    }
}
