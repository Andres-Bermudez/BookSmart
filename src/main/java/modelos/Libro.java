package modelos;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public record Libro(
        @SerializedName("title")
        String titulo,

        @SerializedName("authors")
        List<Autor> autores,

        @SerializedName("formats")
        Map<String, String> formatos,

        @SerializedName("download_count")
        Integer totalDescargas
) {
}
