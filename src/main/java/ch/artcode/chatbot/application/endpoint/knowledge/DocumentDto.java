package ch.artcode.chatbot.application.endpoint.knowledge;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Data
@Schema(
        name = "Document",
        description = "Represents a document"
)
public class DocumentDto {

    @Schema(
            description = "File name",
            example = "document.pdf"
    )
    private String fileName;

    @Schema(
            description = "Content type",
            example = "application/pdf"
    )
    private String contentType;

    @Schema(
            description = "File data in base64",
            example = "base64"
    )
    private String fileData;

    @Schema(
            description = "Document addition date",
            example = "2021-01-01"
    )
    private LocalDate addedDate;

    public void updateAddedDate() {
        this.addedDate = LocalDate.now();
    }
}

