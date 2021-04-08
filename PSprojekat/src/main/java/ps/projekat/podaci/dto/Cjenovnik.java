package ps.projekat.podaci.dto;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Table(name = "cjenovnik")
public class Cjenovnik {

    @Id
    @Column(name = "IdCjenovnika")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCjenovnika;

    @Column(name = "IdAdministratora")
    private int idAdministratora;

    @Column(name = "CijenaPoSatu")
    private float cijenaPoSatu;

    @Column(name = "CijenaPoDanu")
    private float cijenaPoDanu;

    @Column(name = "OznaciObrisan")
    public boolean oznaciObrisan;

    public Cjenovnik(int idAdministratora, float cijenaPoSatu, float cijenaPoDanu) {
        this.idAdministratora = idAdministratora;
        this.cijenaPoSatu = cijenaPoSatu;
        this.cijenaPoDanu = cijenaPoDanu;
        oznaciObrisan = false;
    }

    public Cjenovnik(){
    }

    @Override
    public String toString() {
        return "Cjenovnik{" +
                "idCjenovnika='" + idCjenovnika + '\'' +
                ", idAdministratora=" + idAdministratora +
                ", cijenaPoSatu=" + cijenaPoSatu +
                ", cijenaPoDanu=" + cijenaPoDanu +
                ", oznaciObrisan=" + oznaciObrisan +
                '}';
    }

    public int getIdCjenovnika() {
        return idCjenovnika;
    }

    public void setIdentifikatorCjenovnika(int idCjenovnika) {
        this.idCjenovnika = idCjenovnika;
    }

    public float getCijenaPoSatu() {
        return cijenaPoSatu;
    }

    public void setCijenaPoSatu(float cijenaPoSatu) {
        this.cijenaPoSatu = cijenaPoSatu;
    }

    public float getCijenaPoDanu() {
        return cijenaPoDanu;
    }

    public void setCijenaPoDanu(float cijenaPoDanu) {
        this.cijenaPoDanu = cijenaPoDanu;
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
        Cjenovnik other = (Cjenovnik) o;
        if (idCjenovnika != other.idCjenovnika)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (idCjenovnika ^ (idCjenovnika >>> 32));
        return result;
    }

}
