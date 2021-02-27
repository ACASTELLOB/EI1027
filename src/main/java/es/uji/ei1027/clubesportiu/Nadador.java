package es.uji.ei1027.clubesportiu;

public class Nadador {
    private String nom;
    private String numFederat;
    private String pais;
    private int edat;
    private String genere;

    public Nadador() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumFederat() {
        return numFederat;
    }

    public void setNumFederat(String numFederat) {
        this.numFederat = numFederat;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Nadador{" +
                "nom='" + nom + "\'" +
                ", numFederat='" + numFederat + "\'" +
                ", pais='" + pais + "\'" +
                ", edat=" + edat +
                ", genere='" + genere + "\'" +
                "}";
    }
}
