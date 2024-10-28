package modelos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record Resultado(
        @SerializedName("results")
        List<Libro> resultado
) {
}
