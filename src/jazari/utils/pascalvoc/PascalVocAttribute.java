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
public class PascalVocAttribute {
    public String name;
    public String value;

    public PascalVocAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.value);
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
        final PascalVocAttribute other = (PascalVocAttribute) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return    "\t\t\t<attribute>\n"
                + "\t\t\t\t<name>" + name + "</name>\n"
                + "\t\t\t\t<value>" + value + "</value>\n"
                + "\t\t\t</attribute>\n";

    }
    
    
}
