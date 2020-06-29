package entity;

import java.util.List;

public class Material {
    private final String fixedName;
    private final List<MaterialRecord> materialRecords;

    public Material(String fixedName, List<MaterialRecord> materialRecords) {
        this.fixedName = fixedName;
        this.materialRecords = materialRecords;
    }

    public String getFixedName() {
        return fixedName;
    }

    public List<MaterialRecord> getMaterialRecords() {
        return materialRecords;
    }
}
