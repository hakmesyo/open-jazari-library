/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils.pascalvoc;

import java.util.Objects;

/**
 *
 * @author cezerilab
 */
public class PascalVocSource {
    String database="Unknown";
    String annotation="Unknown";
    String image="Unknown";

    public PascalVocSource() {
    }
    
    public PascalVocSource(String database, String annotation, String image) {
        this.database=database;
        this.annotation=annotation;
        this.image=image;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.database);
        hash = 47 * hash + Objects.hashCode(this.annotation);
        hash = 47 * hash + Objects.hashCode(this.image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PascalVocSource other = (PascalVocSource) obj;
        if (!Objects.equals(this.database, other.database)) {
            return false;
        }
        if (!Objects.equals(this.annotation, other.annotation)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "\t<source>\n"
              + "\t\t<database>"+database+"</database>\n" 
              + "\t\t<annotation>"+annotation+"</annotation>\n" 
              + "\t\t<image>"+image+"</image>\n" 
              + "\t</source>\n"
                ;
    }
    
    
    
    
}
