package ps.projekat.podaci.dto;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Date;
import java.util.Objects;

@Entity
//@Transactional
@Table(name = "rezervacija")
public class Rezervacija {

    @Id
    @Column(name = "IdRezervacije")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRezervacije;

    @Column(name = "DatumIznajmljivanjaPocetak")
    private Date datumIznajmljivanjaPocetak;

    @Column(name = "VrijemeIznajmljivanjaPocetak")
    private Time vrijemeIznajmljivanjaPocetak;

    @Column(name = "DatumIznajmljivanjaKraj")
    private Date datumIznajmljivanjaKraj;

    @Column(name = "VrijemeIznajmljivanjaKraj")
    private Time vrijemeIznajmljivanjaKraj;

    @Column(name = "Sala")
    private byte sala;

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

    @Column(name="IdAdministratora")
    private int idAdministratora;

    @Column(name = "OznaciOtkazano")
    private boolean oznaciOtkazano;

    public Rezervacija() {
    }

    public Rezervacija(Date datumIznajmljivanjaPocetak, Time vrijemeIznajmljivanjaPocetak, Date datumIznajmljivanjaKraj,
                       Time vrijemeIznajmljivanjaKraj, byte sala, Klijent klijent, Cjenovnik cjenovnik, int idAdministratora) {
        this.datumIznajmljivanjaPocetak = datumIznajmljivanjaPocetak;
        this.vrijemeIznajmljivanjaPocetak = vrijemeIznajmljivanjaPocetak;
        this.datumIznajmljivanjaKraj = datumIznajmljivanjaKraj;
        this.vrijemeIznajmljivanjaKraj = vrijemeIznajmljivanjaKraj;
        this.sala = sala;
        this.klijent = klijent;
        this.cjenovnik = cjenovnik;
        this.idAdministratora = idAdministratora;
        oznaciOtkazano = false;
        racun = new Racun();
    }


    public int getIdRezervacije() {
        return idRezervacije;
    }

    public void setIdRezervacije(int idRezervacije) {
        this.idRezervacije = idRezervacije;
    }

    public Date getDatumIznajmljivanjaPocetak() {
        return datumIznajmljivanjaPocetak;
    }

    public void setDatumIznajmljivanjaPocetak(Date datumIznajmljivanjaPocetak) {
        this.datumIznajmljivanjaPocetak = datumIznajmljivanjaPocetak;
    }

    public Time getVrijemeIznajmljivanjaPocetak() {
        return vrijemeIznajmljivanjaPocetak;
    }

    public void setVrijemeIznajmljivanjaPocetak(Time vrijemeIznajmljivanjaPocetak) {
        this.vrijemeIznajmljivanjaPocetak = vrijemeIznajmljivanjaPocetak;
    }

    public Date getDatumIznajmljivanjaKraj() {
        return datumIznajmljivanjaKraj;
    }

    public void setDatumIznajmljivanjaKraj(Date datumIznajmljivanjaKraj) {
        this.datumIznajmljivanjaKraj = datumIznajmljivanjaKraj;
    }

    public Time getVrijemeIznajmljivanjaKraj() {
        return vrijemeIznajmljivanjaKraj;
    }

    public void setVrijemeIznajmljivanjaKraj(Time vrijemeIznajmljivanjaKraj) {
        this.vrijemeIznajmljivanjaKraj = vrijemeIznajmljivanjaKraj;
    }

    public byte getSala() {
        return sala;
    }

    public void setSala(byte sala) {
        this.sala = sala;
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

    public int getIdAdministratora() {
        return idAdministratora;
    }

    public void setIdAdministratora(int idAdministratora) {
        this.idAdministratora = idAdministratora;
    }

    public boolean isOznaciOtkazano() {
        return oznaciOtkazano;
    }

    public void setOznaciOtkazano(boolean oznaciOtkazano) {
        this.oznaciOtkazano = oznaciOtkazano;
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
                "idRezervacije='" + idRezervacije + '\'' +
                ", datumIznajmljivanjaPocetak=" + datumIznajmljivanjaPocetak +
                ", vrijemeIznajmljivanjaPocetak=" + vrijemeIznajmljivanjaPocetak +
                ", datumIznajmljivanjaKraj=" + datumIznajmljivanjaKraj +
                ", vrijemeIznajmljivanjaKraj=" + vrijemeIznajmljivanjaKraj +
                ", klijent=" + klijent +
                ", cjenovnik=" + cjenovnik +
                ", racun='" + racun +
                ", sala=" + sala +
                ", idAdministratora=" + idAdministratora +
                ", oznaciOtkazano=" + oznaciOtkazano +
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
