package ps.projekat.podaci.dto;

import javax.persistence.*;

@Entity
@Table(name = "edukacija")
public class Edukacija {

    @Id
    @Column(name = "IdEdukacije")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEdukacije;

    @Column(name = "NazivEdukacije")
    private String nazivEdukacije;

    @Column(name = "OznaciObrisan")
    private boolean oznaciObrisan;

    public Edukacija() {
    }

    public Edukacija(String nazivEdukacije) {
        this.nazivEdukacije = nazivEdukacije;
        oznaciObrisan = false;
    }

    public long getIdEdukacije() {
        return idEdukacije;
    }

    public void setIdEdukacije(long idEdukacije) {
        this.idEdukacije = idEdukacije;
    }

    public String getNazivEdukacije() {
        return nazivEdukacije;
    }

    public void setNazivEdukacije(String nazivEdukacije) {
        this.nazivEdukacije = nazivEdukacije;
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
        result = prime * result + (int) (idEdukacije ^ (idEdukacije >>> 32));
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
        Edukacija other = (Edukacija) o;
        if (idEdukacije != other.idEdukacije)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edukacija{" +
                "idEdukacije='" + idEdukacije + '\'' +
                "nazivEdukacije='" + nazivEdukacije + '\'' +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }
}
