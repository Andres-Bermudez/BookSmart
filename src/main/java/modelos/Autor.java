package modelos;

import com.google.gson.annotations.SerializedName;

public record Autor(
        @SerializedName("name")
        String nombreAutor,

        @SerializedName("birth_year")
        Integer fechaNacimientoAutor,

        @SerializedName("death_year")
        Integer fechaFallecimientoAutor
) {
    @Override
    public String toString() {
        return "    Nombre: " + nombreAutor() +
               "\n    Fecha de nacimiento: " + fechaNacimientoAutor() +
               "\n    Fecha de fallecimiento: " + fechaFallecimientoAutor();
    }
}
