package ps.projekat.podaci.dto;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rezervacija")
public class Rezervacija {

    @Id
    @Column(name = "IdRezervacije")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRezervacije;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdKlijenta")
    private Klijent klijent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCjenovnika")
    private Cjenovnik cjenovnik;

    @OneToOne(mappedBy = "rezervacija",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private Racun racun;

    @OneToMany(
            mappedBy = "rezervacija",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Termin> termini =  new ArrayList<>();

    @Column(name="IdAdministratora")
    private long idAdministratora;

    @Column(name = "OznaciOtkazano")
    private boolean oznaciOtkazano;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public Rezervacija() {
    }

    public Rezervacija(ArrayList<Termin> termini, Klijent klijent, Cjenovnik cjenovnik, long idAdministratora) {
        this.termini = termini;
        this.termini.forEach(t -> t.setRezervacija(this));
        this.klijent = klijent;
        this.cjenovnik = cjenovnik;
        this.idAdministratora = idAdministratora;
        oznaciOtkazano = false;
        oznaciObrisan = false;
        racun = new Racun(this);
    }


    public long getIdRezervacije() {
        return idRezervacije;
    }

    public void setIdRezervacije(long idRezervacije) {
        this.idRezervacije = idRezervacije;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public Cjenovnik getCjenovnik() {
        return cjenovnik;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public void setCjenovnik(Cjenovnik cjenovnik) {
        this.cjenovnik = cjenovnik;
    }

    public long getIdAdministratora() {
        return idAdministratora;
    }

    public void setIdAdministratora(long idAdministratora) {
        this.idAdministratora = idAdministratora;
    }

    public boolean isOznaciOtkazano() {
        return oznaciOtkazano;
    }

    public void setOznaciOtkazano(boolean oznaciOtkazano) {
        this.oznaciOtkazano = oznaciOtkazano;
    }

    public boolean isOznaciObrisan() {
        return oznaciObrisan;
    }

    public void setOznaciObrisana(boolean oznaciObrisan) {
        this.oznaciObrisan = oznaciObrisan;
    }

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
    }

    @Override
    public String toString() {
        StringBuilder termin = new StringBuilder();
        termini.forEach(t -> termin.append(t + " "));
        return "Rezervacija{" +
                "idRezervacije='" + idRezervacije +
                ", termini=" + termin.toString() +
                ", klijent=" + klijent +
                ", cjenovnik=" + cjenovnik +
                ", racun='" + racun +
                ", idAdministratora=" + idAdministratora +
                ", oznaciOtkazano=" + oznaciOtkazano +
                ", oznaciObrisana=" + oznaciObrisan +
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
        Rezervacija other = (Rezervacija) o;
        if (idRezervacije != other.idRezervacije)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idRezervacije ^ (idRezervacije >>> 32));
        return result;
    }

}
